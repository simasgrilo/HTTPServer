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
	private Server server;
	private BufferedReader request;
	private PrintWriter response;
	private FileResolver fileResolver;

	public ClientHandler(Socket socket, Server server) {
		this.clientSocket = socket;
		this.server = server;
		this.fileResolver = new WinFileResolver();
	}


	@Override
	public void run() {
		try {
			BufferedReader request = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
			PrintWriter response = new PrintWriter(new OutputStreamWriter(this.clientSocket.getOutputStream()));
			if (request != null) {
				String[] uriRequest = request.readLine().split(" ");
				String requestedRes = uriRequest[uriRequest.length - 2]; // uriRequest[uriRequest.length - 2];
				if (requestedRes.equals("/")) {
					this.serve(this.server.getIndexPath(), response);
				}
				else {
					this.serve(requestedRes.replace("/",""), response);
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

	public void serve(String path, PrintWriter response) throws IOException {
		File file = this.fileResolver.resolve(server, path);
		BufferedReader page = null;		
		String status = null;
		if (file != null) { 
			status = server.returnOk();
			page = new BufferedReader(new FileReader(file));
		}
		else {
			status = server.returnNotFound();
			page = new BufferedReader(new FileReader(new File(server.getInvalidPath())));
		}
		response.write(status);
		String line = page.readLine();
		while (line != null) {
			response.write(line);
			line = page.readLine();
		}
		response.write("<h1> This page was served by Thread " + this.getId() + "</h1>");
		response.flush();
		page.close();
	}

	public void close() throws IOException {
		this.request.close();
		this.response.close();
		this.clientSocket.close();
	}
}
