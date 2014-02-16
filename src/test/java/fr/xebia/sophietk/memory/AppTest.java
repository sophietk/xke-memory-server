package fr.xebia.sophietk.memory;

import com.sun.jersey.api.client.Client;
import fr.xebia.sophietk.memory.resource.MemoryResponse;
import fr.xebia.sophietk.memory.service.Card;
import org.junit.After;
import org.junit.Test;

import javax.ws.rs.core.MediaType;
import java.io.Closeable;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AppTest {

	private static final int TEST_PORT = 3001;
	private static final String TEST_APP_ROOT = "http://0.0.0.0:" + TEST_PORT;

	private Client client = Client.create();
	private Closeable server;

	@After
	public void tearDown() throws IOException {
		if (server != null) server.close();
	}

	@Test
	public void should_start_server() throws Exception {
		server = App.startServer(TEST_PORT);

		String response = client.resource(TEST_APP_ROOT)
				.path("play/ping")
				.get(String.class);

		assertEquals("pong", response);
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
