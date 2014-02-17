package fr.xebia.sophietk.memory.service;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ScoreServiceTest {

	public static final String AN_IP = "127.0.0.1";
	public static final String ANOTHER_IP = "127.0.0.2";

	@Test
	public void should_increment_score_for_a_player() {
		ScoreService scoreService = new ScoreService();

		int currentScore = scoreService.addTurnScore(AN_IP, 1, 10);
		assertTrue(currentScore == 10);

		currentScore = scoreService.addTurnScore(AN_IP, 1, 5);
		assertTrue(currentScore == 15);
	}

	@Test
	public void should_decrement_score_several_time_for_a_player() {
		ScoreService scoreService = new ScoreService();

		scoreService.addTurnScore(AN_IP, 1, 10);
		scoreService.addTurnScore(AN_IP, 1, -3);
		int currentScore = scoreService.addTurnScore(AN_IP, 1, -3);
		assertTrue(currentScore == 4);
	}

	@Test
	public void should_not_mix_scores_from_different_players() {
		ScoreService scoreService = new ScoreService();

		scoreService.addTurnScore(AN_IP, 1, 10);
		int currentScoreOtherPlayer = scoreService.addTurnScore(ANOTHER_IP, 1, -3);
		assertTrue(currentScoreOtherPlayer == -3);
	}

	@Test
	public void should_not_mix_scores_from_different_games() {
		ScoreService scoreService = new ScoreService();

		scoreService.addTurnScore(AN_IP, 1, 10);
		int currentScoreOtherGame = scoreService.addTurnScore(AN_IP, 2, -3);
		assertTrue(currentScoreOtherGame == -3);
	}
}
