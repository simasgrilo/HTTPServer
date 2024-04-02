package com.httpserver.grilo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;


public class FileResolver {
	
	
	
	public static File resolve(HTTPServer server, String resource) throws FileNotFoundException {
		//finds whether resource is into resources, html or any other folder that might exist
		File directory = new File(server.getResourcePath());
		File[] folders = directory.listFiles();
		while (folders != null) {
			ArrayList<File> nextFolders = new ArrayList<File>();
			for (File file : folders) {
				if (file != null) {
					//getName returns the name of the file (not the fqn)
					if (file.isFile() && file.getName().equals(resource)){
						return file;
					}
					else if (file.isDirectory()){
						for (File nextFile : file.listFiles()) {
							nextFolders.add(nextFile);
						}
					}
				}
			}
			//nextFolders.removeIf(file -> file == null);
			if (!nextFolders.isEmpty())
				folders = nextFolders.toArray(folders);
			else {
				folders = null;
			}
		}
		return null;		
	}

}
