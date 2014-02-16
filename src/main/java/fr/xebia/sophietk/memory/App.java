package fr.xebia.sophietk.memory;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.sun.jersey.api.core.DefaultResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.simple.container.SimpleServerFactory;
import fr.xebia.sophietk.memory.resource.MemoryResource;

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
		ResourceConfig resourceConfig = new DefaultResourceConfig(MemoryResource.class, JacksonJsonProvider.class);
		return SimpleServerFactory.create("http://0.0.0.0:" + port, resourceConfig);
	}

}
