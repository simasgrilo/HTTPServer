package com.httpserver.grilo;

import java.net.*;
import java.io.*;
import java.io.IOException;

public class HTTPServer {
	private ServerSocket serverSocket;
	private final int port = 80;
	private String headers = "HTTP/1.1 200 OK \r\n\r\n";
	private String indexPath = "resources/html/index.html";

	public void start() throws IOException {
		this.serverSocket = new ServerSocket(this.port); //no sense in initializing the socket outside the start
		System.out.println("Server running at port " + this.port);
		while (true) {
			Socket clientSocket = this.serverSocket.accept(); 
			BufferedReader request = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			PrintWriter response = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
			if (request != null) {
				String[] uriRequest = request.readLine().split(" ");
				try {
					String requestedRes = uriRequest[uriRequest.length - 2]; // uriRequest[uriRequest.length - 2];
					if (requestedRes.equals("/") ||
						requestedRes.equals("/index.html")) {
						this.serve(requestedRes, response);		
					}
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}

			/*
			String message =  this.headers + "Requested path: " +  requestedPath;
			System.out.println("message: " + message);
			response.write(message);
			response.flush(); 
			*/


		}

	}

	public void serve(String path, PrintWriter response) throws IOException {
		//TODO serve the requested file, as in a router method.
		response.write(this.headers);
		BufferedReader page = new BufferedReader(new FileReader(new File(this.indexPath)));
		String line = page.readLine();
		while (line != null) {
			response.write(line);
			line = page.readLine();
		}
		response.flush();
	}
	
	public void close() throws IOException {
		this.serverSocket.close();
		/*this.request.close();
		this.response.close();*/
	}

}
