package fr.xebia.sophietk.memory;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.junit.Test;

import java.io.Closeable;

import static org.junit.Assert.assertEquals;

public class AppTest {

	public static final int TEST_PORT = 3001;
	public static final String TEST_APP_ROOT = "http://0.0.0.0:" + TEST_PORT;

	@Test
	public void should_start_server() throws Exception {
		Closeable server = App.startServer(TEST_PORT);

		ClientResponse<String> response = new ClientRequest(TEST_APP_ROOT + "/play/ping").get(String.class);
		assertEquals(200, response.getStatus());
		assertEquals("pong", response.getEntity());

		server.close();
	}
}
