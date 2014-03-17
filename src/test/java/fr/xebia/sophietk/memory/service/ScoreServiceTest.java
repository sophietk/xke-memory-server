package fr.xebia.sophietk.memory.service;

import org.junit.Test;

import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ScoreServiceTest {

	public static final String AN_IP = "127.0.0.1";
	public static final String ANOTHER_IP = "127.0.0.2";

	@Test
	public void should_increment_score_for_a_player() {
		ScoreService scoreService = new ScoreService();

		int currentScore = scoreService.addTurnScore(AN_IP, 1, 10);
		assertEquals(10, currentScore);

		currentScore = scoreService.addTurnScore(AN_IP, 1, 5);
		assertEquals(15, currentScore);
	}

	@Test
	public void should_decrement_score_several_time_for_a_player() {
		ScoreService scoreService = new ScoreService();

		scoreService.addTurnScore(AN_IP, 1, 10);
		scoreService.addTurnScore(AN_IP, 1, -3);
		int currentScore = scoreService.addTurnScore(AN_IP, 1, -3);
		assertEquals(4, currentScore);
	}

	@Test
	public void should_not_mix_scores_from_different_players() {
		ScoreService scoreService = new ScoreService();

		scoreService.addTurnScore(AN_IP, 1, 10);
		int currentScoreOtherPlayer = scoreService.addTurnScore(ANOTHER_IP, 1, -3);
		assertEquals(-3, currentScoreOtherPlayer);
	}

	@Test
	public void should_not_mix_scores_from_different_games() {
		ScoreService scoreService = new ScoreService();

		scoreService.addTurnScore(AN_IP, 1, 10);
		int currentScoreOtherGame = scoreService.addTurnScore(AN_IP, 2, -3);
		assertEquals(-3, currentScoreOtherGame);
	}

	@Test
	public void should_return_all_played_games_id() {
		ScoreService scoreService = new ScoreService();

		scoreService.addTurnScore(AN_IP, 1, 10);
		scoreService.addTurnScore(AN_IP, 5, 10);
		scoreService.addTurnScore(ANOTHER_IP, 1, 10);

		Set<Integer> gamesId = scoreService.getAllGamesId();
		assertNotNull(gamesId);
		assertEquals(2, gamesId.size());
		assertTrue(gamesId.contains(1));
		assertTrue(gamesId.contains(5));
	}

	@Test
	public void should_return_all_players() {
		ScoreService scoreService = new ScoreService();

		scoreService.addTurnScore(AN_IP, 1, 10);
		scoreService.addTurnScore(AN_IP, 3, 10);
		scoreService.addTurnScore(ANOTHER_IP, 5, 10);

		Set<String> players = scoreService.getAllPlayers();
		assertNotNull(players);
		assertEquals(2, players.size());
		assertTrue(players.contains(AN_IP));
		assertTrue(players.contains(ANOTHER_IP));
	}

	@Test
	public void should_associate_ip_to_player_name() {
		String playerName = "Player1";
		ScoreService scoreService = new ScoreService();
		scoreService.addTurnScore(AN_IP, 1, 10);
		scoreService.registerPlayer(AN_IP, playerName);

		Set<String> players = scoreService.getAllPlayers();
		assertNotNull(players);
		assertEquals(1, players.size());
		assertTrue(players.contains(playerName));

		Map<String,Integer> gameScores = scoreService.getGameScores(1);
		assertEquals(1, gameScores.size());
		assertTrue(gameScores.containsKey(playerName));

		Map<String, Integer> totalScores = scoreService.getTotalScores();
		assertEquals(1, totalScores.size());
		assertTrue(totalScores.containsKey(playerName));

		Map<Integer, Integer> playerScores = scoreService.getPlayerScores(playerName);
		assertEquals(1, playerScores.size());
	}
}
