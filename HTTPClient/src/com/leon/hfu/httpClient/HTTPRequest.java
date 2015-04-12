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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents an entire HTTP request and response.
 * Handles URL parsing and connection, parses the response
 * and provides methods to get the reponse.
 * This implementation only supports GET request.
 *
 * @author		Stefan Hahn
 */
public class HTTPRequest {
	/**
	 * Pattern used to extract status code and status text from
	 * the first line of an HTTP response header.
	 */
	public static final Pattern STATUS_LINE_PATTERN = Pattern.compile("HTTP/\\d\\.\\d (?<statusCode>\\d{3}) (?<statusText>.*?)\r\n");

	/**
	 * Pattern used to extract header key names and header values
	 * from an HTTP response header.
	 */
	public static final Pattern HEADER_FIELD_SPLITTER = Pattern.compile("(?<headerName>.*?):(?:\\s*)(?<headerValue>.*?)\r\n");

	/**
	 * Date format used to parse an HTTP header date to a Java Date object.
	 */
	public static final SimpleDateFormat HEADER_DATE_FORMAT = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);


	/**
	 * URL this request is sent to.
	 */
	private URL requestURL;


	/**
	 * Raw request header
	 */
	private StringBuffer rawRequest = new StringBuffer(1024);

	/**
	 * Raw response header and body
	 */
	private StringBuffer rawResponse = new StringBuffer(2048);


	/**
	 * Response status code
	 */
	private int statusCode = 0;

	/**
	 * Response status text
	 */
	private String statusText = "";

	/**
	 * Response header
	 */
	private String headerString = "";

	/**
	 * Response body
	 */
	private String bodyString = "";


	/**
	 * Response header as hash map. Every header key name is used
	 * as hash map key and holds the corresponding value as a string.
	 */
	private HashMap<String, String> headerFields = new HashMap<>();


	/**
	 * Flag which shows if this HTTPRequest needs to be prepared.
	 * This has to be done when the request URL is changed.
	 * Impossible when the request was executed.
	 */
	private boolean needPrepare = true;

	/**
	 *  Flag which shows if this HTTPRequest has been executed.
	 */
	private boolean executed = false;

	/**
	 * Flag which shows if the response has been parsed.
	 */
	private boolean parsed = false;

	/**
	 * Creates a new HTTPRequest objects with given URL object.
	 *
	 * @param	requestURL		URL this request is sent to.
	 */
	public HTTPRequest(URL requestURL) {
		this.requestURL = requestURL;
	}

	/**
	 * Creates a new HTTPRequest object with a given URL.
	 * The URL string is parsed as URL object.
	 *
	 * @param	requestURL		URL this request is sent to.
	 * @throws	MalformedURLException	Thrown if the given URL is invalid.
	 */
	public HTTPRequest(String requestURL) throws MalformedURLException {
		this(new URL(requestURL));
	}

	/**
	 * Prepares this requestby building the request header.
	 * This method has to be called before execute() but can't
	 * be called after execute() has been called.
	 *
	 * @return				This objects enabling method chaining.
	 * @throws	IOException		Thrown if this method was called after execute() has been called.
	 */
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

	/**
	 * Executes this request by creating a TCP connection and sending
	 * the request to the host. This method automatically calls prepare()
	 * if this request hasn't been prepared. The request is always sent
	 * with UTF-8 encoding.
	 * This method can only be called once per object.
	 *
	 * @return				This objects enabling method chaining.
	 * @throws	IOException		Thrown if execute() has been called before.
	 */
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

	/**
	 * Parses the response of this request. Stores information about
	 * this request in various private variables which can be accessed
	 * after this method finishes.
	 * This method can only get called after execute() has been called.
	 *
	 * @return				This objects enabling method chaining.
	 * @throws	IOException		Thrown if this request hasn't been executed before.
	 * @throws	ParseException		Thrown if the response couldn't be parsed because of invalid format.
	 */
	public HTTPRequest parse() throws IOException, ParseException {
		if (!this.executed) {
			throw new IOException("Request has to be executed");
		}

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
		if (statusLineMatcher.find()) {
			this.statusCode = Integer.parseInt(statusLineMatcher.group("statusCode"), 10);
			this.statusText = statusLineMatcher.group("statusText");
		}
		else {
			throw new ParseException("Couldn't parse response header, status not found.", 0);
		}

		// header fields
		Matcher headerFieldMatcher = HTTPRequest.HEADER_FIELD_SPLITTER.matcher(this.headerString + "\r\n");

		while (headerFieldMatcher.find()) {
			this.headerFields.put(headerFieldMatcher.group("headerName").toLowerCase(), headerFieldMatcher.group("headerValue"));
		}

		this.parsed = true;

		return this;
	}

	/**
	 * Returns the complete response of this request as string.
	 * This method can only get called after execute() has been called.
	 *
	 * @return				The complete response as string.
	 * @throws	IOException		Thrown if this request hasn't been executed before.
	 */
	public String getRawResponse() throws IOException {
		if (!this.executed) {
			throw new IOException("Request has to be executed");
		}

		return this.rawResponse.toString();
	}

	/**
	 * Returns the complete response header of this request as string.
	 * This method can only get called after execute() and parse()
	 * have been called.
	 *
	 * @return				The complete response header as string.
	 * @throws	IOException		Thrown if this request hasn't been executed or parsed before.
	 */
	public String getRawHeader() throws IOException {
		if (!this.executed) {
			throw new IOException("Request has to be executed");
		}
		else if (!this.parsed) {
			throw new IOException("Request has to be parsed");
		}

		return this.headerString;
	}

	/**
	 * Returns the complete response body of this request as string.
	 * This method can only get called after execute() and parse()
	 * have been called.
	 *
	 * @return				The complete response body as string.
	 * @throws	IOException		Thrown if this request hasn't been executed or parsed before.
	 */
	public String getRawBody() throws IOException {
		if (!this.executed) {
			throw new IOException("Request has to be executed");
		}
		else if (!this.parsed) {
			throw new IOException("Request has to be parsed");
		}

		return this.bodyString;
	}

	/**
	 * Returns the HTTP status code of this request.
	 * This method can only get called after execute() and parse()
	 * have been called.
	 *
	 * @return				The HTTP status code of this request.
	 * @throws	IOException		Thrown if this request hasn't been executed or parsed before.
	 */
	public int getStatusCode() throws IOException {
		if (!this.executed) {
			throw new IOException("Request has to be executed");
		}
		else if (!this.parsed) {
			throw new IOException("Request has to be parsed");
		}

		return this.statusCode;
	}

	/**
	 * Returns the HTTP status of this request.
	 * This method can only get called after execute() and parse()
	 * have been called.
	 *
	 * @return				The HTTP status of this request.
	 * @throws	IOException		Thrown if this request hasn't been executed or parsed before.
	 */
	public String getStatusText() throws IOException {
		if (!this.executed) {
			throw new IOException("Request has to be executed");
		}
		else if (!this.parsed) {
			throw new IOException("Request has to be parsed");
		}

		return this.statusText;
	}

	/**
	 * Returns true if the HTTP status code of this request is 200, false otherwise.
	 * This method can only get called after execute() and parse()
	 * have been called.
	 *
	 * @return				True if the HTTP status code of this request is 200, false otherwise.
	 * @throws	IOException		Thrown if this request hasn't been executed or parsed before.
	 */
	public boolean urlExists() throws IOException {
		if (!this.executed) {
			throw new IOException("Request has to be executed");
		}
		else if (!this.parsed) {
			throw new IOException("Request has to be parsed");
		}

		return (this.statusCode == 200);
	}

	/**
	 * Returns the last-modified header field as a Java Date object.
	 * If there is no last-modified header field or the date is invalid
	 * this method returns null.
	 * This method can only get called after execute() and parse()
	 * have been called.
	 *
	 * @return				Last-Modified date as Java Date object.
	 * @throws	IOException		Thrown if this request hasn't been executed or parsed before.
	 */
	public Date getLastModifiedDate() throws IOException {
		if (!this.executed) {
			throw new IOException("Request has to be executed");
		}
		else if (!this.parsed) {
			throw new IOException("Request has to be parsed");
		}

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
