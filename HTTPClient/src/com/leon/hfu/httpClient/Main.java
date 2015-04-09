package com.leon.hfu.httpClient;

import com.leon.hfu.util.commandLine.CommandLineExecutor;

import java.io.IOException;
import java.net.MalformedURLException;

/**
 *
 *
 * @author		Stefan Hahn
 */
public class Main {
	/**
	 *
	 */
	public static HTTPClientProgram program;

	/**
	 *
	 *
	 * @param	args		Command line arguments
	 */
	public static void main(String[] args) {
		program = new HTTPClientProgram();

		// CommandLineExecutor.run(program, args);

		try {
			HTTPRequest req = (new HTTPRequest("http://pr0gramm.com/")).execute().parse();

			System.out.println(req.getRawResponse());
		}
		catch (MalformedURLException e) {
			System.err.println("Invalid URL!");
		}
		catch (IOException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}
}
