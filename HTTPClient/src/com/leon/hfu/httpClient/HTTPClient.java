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
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;

/**
 *
 *
 * @author		Stefan Hahn
 */
public class HTTPClient {
	/**
	 *
	 *
	 * @param	targetURL
	 * @throws	MalformedURLException
	 * @throws	IOException
	 */
	public static void get(String targetURL) throws MalformedURLException, IOException {
		URL url = new URL(targetURL);
		String httpRequest = "";

		httpRequest += "GET " + url.getPath() + ((url.getQuery() == null) ? "" : ("?" + url.getQuery())) + ((url.getRef() == null) ? "" : ("#" + url.getRef())) + " HTTP/1.1\r\n";
		httpRequest += "Host: " + url.getHost() + "\r\n";
		httpRequest += "Content-Length: 0\r\n";
		httpRequest += "Accept-Charset: utf-8\r\n";
		httpRequest += "Connection: close\r\n";
		httpRequest += "\r\n";

		System.out.println(getMessageFromServer(url.getHost(), ((url.getPort() == -1) ? 80 : url.getPort()), httpRequest));
	}

	/**
	 *
	 *
	 * @param	host
	 * @param	port
	 * @param	message
	 * @return
	 * @throws	IOException
	 */
	private static String getMessageFromServer(String host, int port, String message) throws IOException {
		Socket client = new Socket(host, port);
		BufferedReader connectionIn = new BufferedReader(new InputStreamReader(client.getInputStream(), "UTF-8"));
		BufferedWriter connectionOut = new BufferedWriter(new OutputStreamWriter(client.getOutputStream(), "UTF-8"));

		char[] charBuffer = new char[512];
		StringBuffer stringBuffer = new StringBuffer(512);
		int charsRead;

		connectionOut.write(message);
		connectionOut.flush();

		while ((charsRead = connectionIn.read(charBuffer, 0, 512)) > -1) {
			stringBuffer.append(charBuffer, 0, charsRead);
		}

		connectionIn.close();
		connectionOut.close();
		client.close();

		return stringBuffer.toString();
	}
}
