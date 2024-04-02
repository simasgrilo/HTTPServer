package com.httpserver.grilo;

import java.net.*;
import java.io.*;
import java.io.IOException;
import com.httpserver.grilo.ClientHandler;

public class HTTPServer {
	private ServerSocket serverSocket;
	private Socket clientSocket;
	private int port = 80;
	private final String headers = "HTTP/1.1 %s \r\n\r\n";
	private String resourcePath = null;
	private static final String OK = "200 OK ";
	private static final String INDEX_PATH = "index.html";
	private static final String INVALID_RES_PATH = "resources/html/notfound.html";
	private static final String NOT_FOUND = "404 NOT FOUND";

	public HTTPServer(String resourcePath) {
		this.resourcePath = resourcePath;
	}
	
	public void start() throws IOException {
		this.serverSocket = new ServerSocket(this.port); //no sense in initializing the socket outside the start
		System.out.println("Server running at port " + this.port);
		while (true) {
			new ClientHandler(this.serverSocket.accept(), this).start();
			//TODO -- end thread execution after the page has been served?
		}
	}
	
	public String getResourcePath() {
		return this.resourcePath;
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
