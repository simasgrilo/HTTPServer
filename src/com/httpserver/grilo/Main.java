package com.httpserver.grilo;

import java.io.IOException;

/**
 * @author T-GAMER
 *
 */
public class Main {
	
	public static void main(String[] args) {
		try {
			//implementing runnable i need to instantiate an object of type Thread.
			//Extending thread, your server is already a Thread object.
			Server server = new HTTPServer("resources");	
			server.start();
		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}

}
