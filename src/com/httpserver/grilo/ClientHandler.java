package com.httpserver.grilo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler extends Thread {
	//handler to handle multiple incoming requests
	private Socket clientSocket;
	private HTTPServer server;
	private BufferedReader request;
	private PrintWriter response;

	public ClientHandler(Socket socket, HTTPServer server) {
		this.clientSocket = socket;
		this.server = server;
	}


	@Override
	public void run() {
		try {
			BufferedReader request = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
			PrintWriter response = new PrintWriter(new OutputStreamWriter(this.clientSocket.getOutputStream()));
			if (request != null) {
				String[] uriRequest = request.readLine().split(" ");
				String requestedRes = uriRequest[uriRequest.length - 2]; // uriRequest[uriRequest.length - 2];
				//TODO - the request specifies the requested file. if it's in WWW and its subsidiaries, then we return it.
				if (requestedRes.equals("/") || requestedRes.equals("/index.html")) {
					//Thread.sleep(1000);
					this.serve(this.server.getIndexPath(), response, this.server.returnOk());
				}
				else {
					//invalid route:
					this.serve(this.server.getInvalidPath(), response, this.server.returnNotFound());
				}
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	public void serve(String path, PrintWriter response, String status) throws IOException {
		response.write(status);
		//File file = FileResolver.resolve(path);
		BufferedReader page = new BufferedReader(new FileReader(new File(path)));
		String line = page.readLine();
		while (line != null) {
			response.write(line);
			line = page.readLine();
		}
		response.write("<h1> This page was served by Thread " + this.getId() + "</h1>");
		response.flush();
	}

	public void close() throws IOException {
		this.request.close();
		this.response.close();
		this.clientSocket.close();
	}
}
