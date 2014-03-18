package fr.xebia.sophietk.memory.resource;

import com.sun.jersey.api.client.UniformInterfaceException;
import fr.xebia.sophietk.memory.App;
import fr.xebia.sophietk.memory.service.Game;
import org.joda.time.DateTime;
import org.joda.time.Seconds;
import org.json.JSONObject;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class AdminResourceTest extends ServerResourceTest {

	@Test(expected = UniformInterfaceException.class)
	public void should_throw_401_when_accessing_admin_resource() {
		client.resource(TEST_APP_ROOT)
				.path("admin/game")
				.get(String.class);
	}

	@Test
	public void should_get_current_game() {
		JSONObject gameJson = currentGameForAdmin();

		assertEquals(1, gameJson.getInt("gameId"));
		assertNotNull(gameJson.getJSONArray("grid"));
		assertEquals(0, gameJson.getDouble("progress"), 0);
	}

	@Test
	public void should_launch_new_game_with_appropriate_size() {
		JSONObject game1Json = currentGameForAdmin();
		assertEquals(Game.DEFAULT_GRID_SIZE, game1Json.getInt("gridSize"));

		newGameForAdmin(4);
		JSONObject game2Json = currentGameForAdmin();

		assertNotEquals(game1Json.getInt("gameId"), game2Json.getInt("gameId"));
		assertEquals(4, game2Json.getInt("gridSize"));
	}

	@Test(expected = UniformInterfaceException.class)
	public void should_throw_exception_when_trying_to_create_impossible_grid() {
		newGameForAdmin(3);
	}

	@Test
	public void should_return_updated_logs() {
		List<Map<String, Object>> logs = logsForAdmin();
		assertEquals(0, logs.size());

		play("[ [0, 1], [0, 1] ]");
		logs = logsForAdmin();
		assertEquals(2, logs.size());

		Map<String, Object> log0 = logs.get(0);
		assertTrue(log0.containsKey("date"));
		assertTrue(log0.containsKey("player"));
		assertTrue(log0.containsKey("action"));
		assertTrue(Seconds.secondsBetween(DateTime.now(), new DateTime(log0.get("date"))).isLessThan(Seconds.seconds(1)));
		assertTrue(((String) log0.get("player")).matches(IP_PATTERN));
		assertTrue(((String) log0.get("action")).matches("^Plays .*"));

		Map<String, Object> log1 = logs.get(1);
		assertTrue(log1.containsKey("date"));
		assertTrue(log1.containsKey("player"));
		assertTrue(log1.containsKey("action"));
		assertTrue(Seconds.secondsBetween(DateTime.now(), new DateTime(log1.get("date"))).isLessThan(Seconds.seconds(1)));
		assertTrue(((String) log1.get("player")).matches(IP_PATTERN));
		assertTrue(((String) log1.get("action")).matches("^(Wins|Loses) .*"));
	}

	@Test
	public void should_reset_all() {
		play("[ [0, 1], [0, 1] ]");
		newGameForAdmin(2);
		play("[ [0, 1], [-1, 1] ]");

		assertFalse(logsForAdmin().isEmpty());
		assertFalse(totalScores().isEmpty());
		assertEquals(2, currentGameForAdmin().getInt("gameId"));

		client.resource(TEST_APP_ROOT)
				.path("admin/reset")
				.header("adminpass", App.HEADER_ADMIN_PASS)
				.post();

		assertTrue(logsForAdmin().isEmpty());
		assertTrue(totalScores().isEmpty());
		assertEquals(1, currentGameForAdmin().getInt("gameId"));
	}

	@Test
	public void should_temporize_game_turns() {
		changeTempoForAdmin(500);
	}
}
