package fr.xebia.sophietk.memory;

import com.sun.jersey.api.core.DefaultResourceConfig;
import com.sun.jersey.simple.container.SimpleServerFactory;

import java.io.Closeable;
import java.io.IOException;

public class App {

	public static final int PORT = 3000;

	public static void main(String[] args) throws Exception {
		Closeable server = startServer(PORT);

		try {
			System.out.println("Press any key to stop the service...");
			System.in.read();
		} finally {
			server.close();
		}
	}

	protected static Closeable startServer(int port) throws IOException {
		DefaultResourceConfig resourceConfig = new DefaultResourceConfig(MemoryResource.class);
		return SimpleServerFactory.create("http://0.0.0.0:" + port, resourceConfig);
	}

}
