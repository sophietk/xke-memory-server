package fr.xebia.sophietk.memory.service;

import com.google.inject.Singleton;

@Singleton
public class GameService {

	private Game game;
	private int nbGames;

	public GameService() {
		nbGames = 0;
		game = new Game();
		game.setGameId(++nbGames);
	}

	public Game getCurrentGame() {
		return game;
	}

	public void launchNewGame(int gridSize) {
		game = new Game(gridSize);
		game.setGameId(++nbGames);
	}
}
