package com.httpserver.grilo;

import java.io.IOException;
import java.net.Socket;

public interface Server {

	void start() throws IOException;

	String getResourcePath();

	Socket getClientSocket();

	String returnOk();

	String returnNotFound();

	String getIndexPath();

	String getInvalidPath();

	void close() throws IOException;

}