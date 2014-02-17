package fr.xebia.sophietk.memory;

import com.sun.jersey.api.client.Client;
import fr.xebia.sophietk.memory.resource.MemoryResponse;
import fr.xebia.sophietk.memory.service.Card;
import org.eclipse.jetty.server.Server;
import org.junit.After;
import org.junit.Test;

import javax.ws.rs.core.MediaType;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AppTest {

	private static final int TEST_PORT = 3001;
	private static final String TEST_APP_ROOT = "http://0.0.0.0:" + TEST_PORT;
	public static final String IP_PATTERN = "\\b(?:\\d{1,3}\\.){3}\\d{1,3}\\b";

	private Client client = Client.create();
	private Server server;

	@After
	public void tearDown() throws Exception {
		if (server != null) server.stop();
	}

	@Test
	public void should_start_server() throws Exception {
		server = App.startServer(TEST_PORT);

		String response = client.resource(TEST_APP_ROOT)
				.path("play/ping")
				.get(String.class);

		assertTrue(response.matches("pong " + IP_PATTERN));
	}

	@Test
	public void should_play_in_same_game() throws Exception {
		server = App.startServer(TEST_PORT);

		MemoryResponse response = client.resource(TEST_APP_ROOT)
				.path("play")
				.type(MediaType.APPLICATION_JSON)
				.post(MemoryResponse.class, "[{\"x\": 0, \"y\": 1}, {\"x\": 0, \"y\": 1}]");

		assertTrue(1 == response.getGameId());
		assertTrue(0 == response.getProgress());
		assertTrue(0 == response.getGameScore());
		assertTrue(0 == response.getTurn().getTurnScore());

		Card card1 = response.getTurn().getCards().get(0);

		MemoryResponse response2 = client.resource(TEST_APP_ROOT)
				.path("play")
				.type(MediaType.APPLICATION_JSON)
				.post(MemoryResponse.class, "[{\"x\": 0, \"y\": 1}, {\"x\": 0, \"y\": 1}]");

		assertEquals(card1, response2.getTurn().getCards().get(0));
	}
}
