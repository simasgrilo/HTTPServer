package com.httpserver.grilo;

import java.net.*;
import java.io.*;
import java.io.IOException;
import com.httpserver.grilo.ClientHandler;

public class HTTPServer {
	private ServerSocket serverSocket;
	private Socket clientSocket;
	private final int PORT = 80;
	private final String headers = "HTTP/1.1 %s OK \r\n\r\n";
	private final String OK = "200";
	private final String INDEX_PATH = "resources/html/index.html";
	private final String INVALID_RES_PATH = "resources/html/notfound.html";
	private final String NOT_FOUND = "404";

	public void start() throws IOException {
		this.serverSocket = new ServerSocket(this.PORT); //no sense in initializing the socket outside the start
		System.out.println("Server running at port " + this.PORT);
		while (true) {
			new ClientHandler(this.serverSocket.accept(), this).start();
			//TODO -- end thread execution after the page has been served?
		}
	}
	
	public Socket getClientSocket() {
		return this.clientSocket;
	}

	public String returnOk() {
		return String.format(this.headers, this.OK);
	}
	
	public String returnNotFound() {
		return String.format(this.headers, this.NOT_FOUND);
	}
	
	public String getIndexPath() {
		return this.INDEX_PATH;
	}
	
	public String getInvalidPath() {
		return this.INVALID_RES_PATH;
	}
	
	public void close() throws IOException {
		this.serverSocket.close();
		/*this.request.close();
		this.response.close();*/
	}

}
