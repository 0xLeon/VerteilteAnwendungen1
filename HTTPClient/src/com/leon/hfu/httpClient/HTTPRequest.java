/*
 * Copyright (C) 2015 Stefan Hahn
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */
package com.leon.hfu.httpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author		Stefan Hahn
 */
public class HTTPRequest {
	public static final Pattern STATUS_LINE_PATTERN = Pattern.compile("HTTP/\\d\\.\\d (?<statusCode>\\d{3}) (?<statusText>.*?)\r\n");
	public static final Pattern HEADER_FIELD_SPLITTER = Pattern.compile("(?<headerName>.*?):(?:\\s*)(?<headerValue>.*?)\r\n");
	public static final SimpleDateFormat HEADER_DATE_FORMAT = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);

	private URL requestURL;

	private StringBuffer rawRequest = new StringBuffer(1024);
	private StringBuffer rawResponse = new StringBuffer(2048);

	private int statusCode = 0;
	private String statusText = "";
	private String headerString = "";
	private String bodyString = "";

	private HashMap<String, String> headerFields = new HashMap<>();


	private boolean needPrepare = true;
	private boolean executed = false;
	private boolean parsed = false;

	public HTTPRequest(URL requestURL) {
		this.requestURL = requestURL;
	}

	public HTTPRequest(String requestURL) throws MalformedURLException {
		this(new URL(requestURL));
	}

	public HTTPRequest prepare() throws IOException {
		if (this.executed) {
			throw new IOException("Request already executed");
		}

		// line one
		this.rawRequest.append("GET ");
		this.rawRequest.append(this.requestURL.getPath());

		if (this.requestURL.getQuery() != null) {
			this.rawRequest.append("?");
			this.rawRequest.append(this.requestURL.getQuery());
		}

		if (this.requestURL.getRef() != null) {
			this.rawRequest.append("#");
			this.rawRequest.append(this.requestURL.getRef());
		}

		this.rawRequest.append(" HTTP/1.1\r\n");

		// host header field
		this.rawRequest.append("Host: ");
		this.rawRequest.append(this.requestURL.getHost());
		this.rawRequest.append("\r\n");

		// content-length header field
		this.rawRequest.append("Content-Length: 0\r\n");

		// accept-charset header field
		this.rawRequest.append("Accept-Charset: utf-8\r\n");

		// connection header field
		this.rawRequest.append("Connection: close\r\n");

		// empty line
		this.rawRequest.append("\r\n");

		this.needPrepare = false;

		return this;
	}

	public HTTPRequest execute() throws IOException {
		if (this.executed) {
			throw new IOException("Request already executed");
		}

		if (this.needPrepare) {
			this.prepare();
		}

		Socket client = new Socket(this.requestURL.getHost(), (this.requestURL.getPort() == -1) ? 80 : this.requestURL.getPort());
		BufferedReader connectionIn = new BufferedReader(new InputStreamReader(client.getInputStream(), "UTF-8"));
		OutputStreamWriter connectionOut = new OutputStreamWriter(client.getOutputStream(), "UTF-8");

		char[] charBuffer = new char[2048];
		int charsRead;

		connectionOut.write(this.rawRequest.toString());
		connectionOut.flush();

		while ((charsRead = connectionIn.read(charBuffer, 0, 512)) > -1) {
			this.rawResponse.append(charBuffer, 0, charsRead);
		}

		connectionIn.close();
		connectionOut.close();
		client.close();

		this.executed = true;

		return this;
	}

	public HTTPRequest parse() {
		if (this.parsed) {
			return this;
		}

		// split header from body
		String[] tmp = this.rawResponse.toString().split("\r\n\r\n", 2);
		this.headerString = tmp[0];
		this.bodyString = tmp[1];
		tmp = null;

		// status line
		Matcher statusLineMatcher = HTTPRequest.STATUS_LINE_PATTERN.matcher(this.headerString);
		statusLineMatcher.find();
		this.statusCode = Integer.parseInt(statusLineMatcher.group("statusCode"), 10);
		this.statusText = statusLineMatcher.group("statusText");

		// header fields
		Matcher headerFieldMatcher = HTTPRequest.HEADER_FIELD_SPLITTER.matcher(this.headerString + "\r\n");

		while (headerFieldMatcher.find()) {
			this.headerFields.put(headerFieldMatcher.group("headerName"), headerFieldMatcher.group("headerValue"));
		}

		this.parsed = true;

		return this;
	}

	public String getRawResponse() throws IOException {
		if (!this.executed) {
			throw new IOException("Request has to be executed");
		}

		return this.rawResponse.toString();
	}

	public String getRawHeader() throws IOException {
		if (!this.executed) {
			throw new IOException("Request has to be executed");
		}
		else if (!this.parsed) {
			throw new IOException("Request has to be parsed");
		}

		return this.headerString;
	}

	public String getRawBody() throws IOException {
		if (!this.executed) {
			throw new IOException("Request has to be executed");
		}
		else if (!this.parsed) {
			throw new IOException("Request has to be parsed");
		}

		return this.bodyString;
	}

	public int getStatusCode() throws IOException {
		if (!this.executed) {
			throw new IOException("Request has to be executed");
		}
		else if (!this.parsed) {
			throw new IOException("Request has to be parsed");
		}

		return this.statusCode;
	}

	public String getStatusText() throws IOException {
		if (!this.executed) {
			throw new IOException("Request has to be executed");
		}
		else if (!this.parsed) {
			throw new IOException("Request has to be parsed");
		}

		return this.statusText;
	}

	public boolean urlExists() throws IOException {
		if (!this.executed) {
			throw new IOException("Request has to be executed");
		}
		else if (!this.parsed) {
			throw new IOException("Request has to be parsed");
		}

		return (this.statusCode == 200);
	}

	public Date getLastModifiedDate() throws IOException {
		if (!this.executed) {
			throw new IOException("Request has to be executed");
		}
		else if (!this.parsed) {
			throw new IOException("Request has to be parsed");
		}

		// Last-Modified: Fri, 10 Apr 2015 19:08:52 GMT

		try {
			if (this.headerFields.containsKey("last-modified")) {
				return HTTPRequest.HEADER_DATE_FORMAT.parse(this.headerFields.get("last-modified"));
			}
		}
		catch (ParseException e) {
			System.err.println("Couldn't parse last-modified header date: " + this.headerFields.get("last-modified"));
		}

		return null;
	}
}
