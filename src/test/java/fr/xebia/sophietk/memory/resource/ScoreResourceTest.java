package fr.xebia.sophietk.memory.resource;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.GenericType;
import fr.xebia.sophietk.memory.App;
import org.eclipse.jetty.server.Server;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ScoreResourceTest {

	private static final int TEST_PORT = 3001;
	private static final String TEST_APP_ROOT = "http://0.0.0.0:" + TEST_PORT;
	public static final String IP_PATTERN = "\\b(?:\\d{1,3}\\.){3}\\d{1,3}\\b";

	private Client client = Client.create();
	private Server server;

	@Before
	public void setUp() throws Exception {
		server = App.startServer(TEST_PORT);
	}

	@After
	public void tearDown() throws Exception {
		if (server != null) server.stop();
	}

	@Test
	public void should_get_updated_scores() {
		final String gameScoreUri = "scores/game/1";

		Map<String, Integer> response = client.resource(TEST_APP_ROOT)
				.path(gameScoreUri)
				.get(new GenericType<HashMap<String, Integer>>() {
				});

		assertEquals(0, response.size());

		client.resource(TEST_APP_ROOT)
				.path("play")
				.type(MediaType.APPLICATION_JSON)
				.post(MemoryResponse.class, "[ [-1, 1], [0, 1] ]");

		Map<String, Integer> response2 = client.resource(TEST_APP_ROOT)
				.path(gameScoreUri)
				.get(new GenericType<HashMap<String, Integer>>() {
				});

		assertEquals(1, response2.size());
		assertTrue(response2.containsValue(-1)); // score -1

		client.resource(TEST_APP_ROOT)
				.path("play")
				.type(MediaType.APPLICATION_JSON)
				.post(MemoryResponse.class, "[ [0, 1] ]");

		Map<String, Integer> response3 = client.resource(TEST_APP_ROOT)
				.path(gameScoreUri)
				.get(new GenericType<HashMap<String, Integer>>() {
				});

		assertEquals(1, response3.size());
		assertTrue(response3.containsValue(-2)); // score -2
	}

	@Test
	public void should_get_updated_games_list() {
		final String gamesUri = "scores/game";

		List<Integer> response = client.resource(TEST_APP_ROOT)
				.path(gamesUri)
				.get(new GenericType<List<Integer>>() {
				});

		assertEquals(0, response.size());

		client.resource(TEST_APP_ROOT)
				.path("play")
				.type(MediaType.APPLICATION_JSON)
				.post(MemoryResponse.class, "[ [-1, 1], [0, 1] ]");

		List<Integer> response2 = client.resource(TEST_APP_ROOT)
				.path(gamesUri)
				.get(new GenericType<List<Integer>>() {
				});

		assertEquals(1, response2.size());
		assertTrue(response2.contains(1)); // game 1
	}

	@Test
	public void should_get_updated_players_list() {
		final String gamesUri = "scores/player";

		List<String> response = client.resource(TEST_APP_ROOT)
				.path(gamesUri)
				.get(new GenericType<List<String>>() {
				});

		assertEquals(0, response.size());

		client.resource(TEST_APP_ROOT)
				.path("play")
				.type(MediaType.APPLICATION_JSON)
				.post(MemoryResponse.class, "[ [-1, 1], [0, 1] ]");

		List<String> response2 = client.resource(TEST_APP_ROOT)
				.path(gamesUri)
				.get(new GenericType<List<String>>() {
				});

		assertEquals(1, response2.size());
		assertTrue(response2.get(0).matches(IP_PATTERN)); // my ip
	}
}
