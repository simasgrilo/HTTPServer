package com.httpserver.grilo;

import java.io.IOException;

/**
 * @author T-GAMER
 *
 */
public class Main {
	
	public static void main(String[] args) {
		HTTPServer server = new HTTPServer();
		try {
			server.start();	
		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}

}
