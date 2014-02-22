package fr.xebia.sophietk.memory.resource;

import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ScoreResourceTest extends ServerResourceTest {

	@Test
	public void should_get_updated_scores() {
		Map<String, Integer> gameScore = gameScore(1);

		assertEquals(0, gameScore.size());

		play("[ [-1, 1], [0, 1] ]");

		gameScore = gameScore(1);

		assertEquals(1, gameScore.size());
		assertTrue(gameScore.containsValue(-1)); // score -1

		play("[ [0, 1] ]");

		gameScore = gameScore(1);

		assertEquals(1, gameScore.size());
		assertTrue(gameScore.containsValue(-2)); // score -2
	}

	@Test
	public void should_get_updated_total_scores() {
		Map<String, Integer> totalScores = totalScores();

		assertEquals(0, totalScores.size());

		play("[ [-1, 1], [0, 1] ]");

		totalScores = totalScores();

		assertEquals(1, totalScores.size());
		assertTrue(totalScores.containsValue(-1));

		newGameForAdmin(2);

		totalScores = totalScores();

		assertEquals(1, totalScores.size());
		assertTrue(totalScores.containsValue(-1));

		play("[ [-1, 1], [0, 1] ]");

		totalScores = totalScores();

		assertEquals(1, totalScores.size());
		assertTrue(totalScores.containsValue(-2));
	}

	@Test
	public void should_get_updated_games_list() {
		List<Integer> gamesList = gamesList();

		assertEquals(0, gamesList.size());

		play("[ [-1, 1], [0, 1] ]");

		gamesList = gamesList();

		assertEquals(1, gamesList.size());
		assertTrue(gamesList.contains(1)); // game 1
	}

	@Test
	public void should_get_updated_players_list() {
		List<String> playersList = playersList();

		assertEquals(0, playersList.size());

		play("[ [-1, 1], [0, 1] ]");

		playersList = playersList();

		assertEquals(1, playersList.size());
		assertTrue(playersList.get(0).matches(IP_PATTERN)); // my ip
	}

}
