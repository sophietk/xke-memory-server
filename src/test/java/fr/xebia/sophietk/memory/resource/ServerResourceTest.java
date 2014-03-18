package fr.xebia.sophietk.memory.resource;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.GenericType;
import fr.xebia.sophietk.memory.App;
import org.eclipse.jetty.server.Server;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;

import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ServerResourceTest {

	protected static final int TEST_PORT = 3001;
	protected static final String TEST_APP_ROOT = "http://0.0.0.0:" + TEST_PORT;
	protected static final String IP_PATTERN = "\\b(?:\\d{1,3}\\.){3}\\d{1,3}\\b";

	protected Client client = Client.create();
	protected Server server;

	@Before
	public void setUp() throws Exception {
		server = App.startServer(TEST_PORT);
	}

	@After
	public void tearDown() throws Exception {
		if (server != null) server.stop();
	}

	protected MemoryResponse play(String play) {
		return client.resource(TEST_APP_ROOT)
				.path("play")
				.type(MediaType.APPLICATION_JSON)
				.post(MemoryResponse.class, play);
	}

	protected void newGameForAdmin(int gridSize) {
		client.resource(TEST_APP_ROOT)
				.path("admin/new")
				.header("adminpass", App.HEADER_ADMIN_PASS)
				.type(MediaType.APPLICATION_JSON)
				.post(gridSize);
	}

	protected JSONObject currentGameForAdmin() {
		String gameString = client.resource(TEST_APP_ROOT)
				.path("admin/game")
				.header("adminpass", App.HEADER_ADMIN_PASS)
				.get(String.class);
		return new JSONObject(gameString);
	}

	protected List<Map<String, Object>> logsForAdmin() {
		return client.resource(TEST_APP_ROOT)
				.path("admin/logs")
				.header("adminpass", App.HEADER_ADMIN_PASS)
				.type(MediaType.APPLICATION_JSON)
				.get(new GenericType<List<Map<String, Object>>>() {
				});
	}

	protected List<Integer> gamesList() {
		return client.resource(TEST_APP_ROOT)
				.path("scores/game")
				.get(new GenericType<List<Integer>>() {
				});
	}

	protected List<String> playersList() {
		return client.resource(TEST_APP_ROOT)
				.path("scores/player")
				.get(new GenericType<List<String>>() {
				});
	}

	protected Map<String, Integer> totalScores() {
		return client.resource(TEST_APP_ROOT)
				.path("scores")
				.get(new GenericType<HashMap<String, Integer>>() {
				});
	}

	protected Map<String, Integer> gameScore(int gameId) {
		return client.resource(TEST_APP_ROOT)
				.path("scores/game/" + gameId)
				.get(new GenericType<HashMap<String, Integer>>() {
				});
	}

	protected void changeTempoForAdmin(int tempo) {
		client.resource(TEST_APP_ROOT)
				.path("admin/tempo")
				.header("adminpass", App.HEADER_ADMIN_PASS)
				.type(MediaType.APPLICATION_JSON)
				.post(tempo);
	}
}
