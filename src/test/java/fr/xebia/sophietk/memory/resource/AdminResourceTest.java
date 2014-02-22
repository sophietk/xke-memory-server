package fr.xebia.sophietk.memory.resource;

import com.sun.jersey.api.client.UniformInterfaceException;
import fr.xebia.sophietk.memory.App;
import fr.xebia.sophietk.memory.service.Game;
import org.json.JSONObject;
import org.junit.Test;

import javax.ws.rs.core.MediaType;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

public class AdminResourceTest extends ServerResourceTest {

	@Test(expected = UniformInterfaceException.class)
	public void should_throw_401_when_accessing_admin_resource() {
		client.resource(TEST_APP_ROOT)
				.path("admin/game")
				.get(String.class);
	}

	@Test
	public void should_get_current_game() {
		String response = client.resource(TEST_APP_ROOT)
				.path("admin/game")
				.header("adminpass", App.DEFAULT_ADMIN_PASS)
				.get(String.class);
		JSONObject jsonGame = new JSONObject(response);

		assertEquals(1, jsonGame.getInt("gameId"));
		assertNotNull(jsonGame.getJSONArray("grid"));
		assertEquals(0, jsonGame.getDouble("progress"), 0);
	}

	@Test
	public void should_launch_new_game_with_appropriate_size() {
		String response = client.resource(TEST_APP_ROOT)
				.path("admin/game")
				.header("adminpass", App.DEFAULT_ADMIN_PASS)
				.get(String.class);
		JSONObject jsonGame1 = new JSONObject(response);
		assertEquals(Game.DEFAULT_GRID_SIZE, jsonGame1.getInt("gridSize"));

		client.resource(TEST_APP_ROOT)
				.path("admin/new")
				.header("adminpass", App.DEFAULT_ADMIN_PASS)
				.type(MediaType.APPLICATION_JSON)
				.post(4);
		String response2 = client.resource(TEST_APP_ROOT)
				.path("admin/game")
				.header("adminpass", App.DEFAULT_ADMIN_PASS)
				.get(String.class);
		JSONObject jsonGame2 = new JSONObject(response2);

		assertNotEquals(jsonGame1.getInt("gameId"), jsonGame2.getInt("gameId"));
		assertEquals(4, jsonGame2.getInt("gridSize"));
	}

	@Test(expected = UniformInterfaceException.class)
	public void should_throw_exception_when_trying_to_create_impossible_grid() {
		client.resource(TEST_APP_ROOT)
				.path("admin/new")
				.header("adminpass", App.DEFAULT_ADMIN_PASS)
				.type(MediaType.APPLICATION_JSON)
				.post(3);
	}
}