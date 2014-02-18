package fr.xebia.sophietk.memory;

import com.sun.jersey.api.client.Client;
import org.eclipse.jetty.server.Server;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class AppTest {

	private static final int TEST_PORT = 3001;
	private static final String TEST_APP_ROOT = "http://0.0.0.0:" + TEST_PORT;

	@Test
	public void should_start_server() throws Exception {
		Server server = App.startServer(TEST_PORT);

		String response = Client.create().resource(TEST_APP_ROOT)
				.path("play/ping")
				.get(String.class);

		assertTrue(response.contains("pong"));

		server.stop();
	}

}
