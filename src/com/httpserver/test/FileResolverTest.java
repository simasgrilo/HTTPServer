package com.httpserver.test;

import static org.junit.Assert.*;
import org.junit.Test;
import com.httpserver.grilo.WinFileResolver;
import com.httpserver.grilo.HTTPServer;
import com.httpserver.grilo.Server;

import java.io.File;
import java.io.IOException;


public class FileResolverTest {
	
	private Server httpServer = new HTTPServer("resources");
	private WinFileResolver winFileResolver = new WinFileResolver();
	
	@Test
	public void testResolveIndex() throws IOException {
		assertEquals(new File("resources\\html\\index.html"), winFileResolver.resolve(this.httpServer,"index.html"));
	}
	
	@Test
	public void testResolveNotFould() throws IOException {
		assertNotEquals(new File("resources\\html\\index.html"), winFileResolver.resolve(this.httpServer, "/usr/bin/password/pasword.txt"));
	}

}
