package fr.xebia.sophietk.memory.resource;

import com.sun.jersey.api.client.UniformInterfaceException;
import fr.xebia.sophietk.memory.service.Card;
import org.junit.Test;

import javax.ws.rs.core.MediaType;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MemoryResourceTest extends ServerResourceTest {

	@Test
	public void should_return_my_ip() {
		String response = client.resource(TEST_APP_ROOT)
				.path("play/ping")
				.get(String.class);

		assertTrue(response.matches("pong " + IP_PATTERN));
	}

	@Test
	public void should_play_in_same_game() {
		MemoryResponse response = client.resource(TEST_APP_ROOT)
				.path("play")
				.type(MediaType.APPLICATION_JSON)
				.post(MemoryResponse.class, "[ [0, 1], [0, 1] ]");

		assertEquals(1, response.getGameId());
		assertEquals(0, response.getProgress(), 0);
		assertEquals(0, response.getGameScore());
		assertEquals(0, response.getTurn().getTurnScore());

		Card card1 = response.getTurn().getCards().get(0);

		MemoryResponse response2 = client.resource(TEST_APP_ROOT)
				.path("play")
				.type(MediaType.APPLICATION_JSON)
				.post(MemoryResponse.class, "[ [0, 1], [0, 1] ]");

		assertEquals(card1, response2.getTurn().getCards().get(0));
	}

	@Test(expected = UniformInterfaceException.class)
	public void should_throw_http_error() {
		client.resource(TEST_APP_ROOT)
				.path("play")
				.type(MediaType.APPLICATION_JSON)
				.post(MemoryResponse.class, "[ [0, 1], [2] ]");
	}
}
