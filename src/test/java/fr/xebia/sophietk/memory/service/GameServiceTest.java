package fr.xebia.sophietk.memory.service;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class GameServiceTest {

	@Test
	public void should_launch_new_game_and_increment_game_id() {
		GameService gameService = new GameService();
		Game game1 = gameService.getCurrentGame();

		assertEquals(1, game1.getGameId());

		gameService.launchNewGame(6);
		Game game2 = gameService.getCurrentGame();

		assertEquals(2, game2.getGameId());
		assertFalse(Arrays.equals(game1.getGrid(), game2.getGrid()));
	}

}
