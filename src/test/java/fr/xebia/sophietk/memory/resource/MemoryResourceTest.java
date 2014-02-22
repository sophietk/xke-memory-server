package fr.xebia.sophietk.memory.resource;

import com.sun.jersey.api.client.UniformInterfaceException;
import fr.xebia.sophietk.memory.service.Card;
import org.junit.Test;

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
		MemoryResponse play = play("[ [0, 1], [0, 1] ]");

		assertEquals(1, play.getGameId());
		assertEquals(0, play.getProgress(), 0);
		assertEquals(0, play.getGameScore());
		assertEquals(0, play.getTurn().getTurnScore());

		Card card1 = play.getTurn().getCards().get(0);

		MemoryResponse response2 = play("[ [0, 1], [0, 1] ]");

		assertEquals(card1, response2.getTurn().getCards().get(0));
	}

	@Test(expected = UniformInterfaceException.class)
	public void should_throw_http_error() {
		play("[ [0, 1], [2] ]");
	}
}
