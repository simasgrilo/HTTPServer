package com.httpserver.grilo;

import java.io.File;
import java.io.FileNotFoundException;

public interface FileResolver {
	
	public File resolve(Server server, String resource) throws FileNotFoundException;

}
