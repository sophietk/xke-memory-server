package fr.xebia.sophietk.memory;

import com.sun.jersey.api.client.Client;
import org.junit.Test;

import java.io.Closeable;

import static org.junit.Assert.assertEquals;

public class AppTest {

	private static final int TEST_PORT = 3001;
	private static final String TEST_APP_ROOT = "http://0.0.0.0:" + TEST_PORT;

	private Client client = Client.create();

	@Test
	public void should_start_server() throws Exception {
		Closeable server = App.startServer(TEST_PORT);

		String response = client.resource(TEST_APP_ROOT)
				.path("play/ping")
				.get(String.class);

		assertEquals("pong", response);

		server.close();
	}
}
