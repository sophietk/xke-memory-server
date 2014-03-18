package fr.xebia.sophietk.memory.service;

import com.google.common.collect.Maps;
import com.google.inject.Singleton;

import java.util.Date;
import java.util.Map;

@Singleton
public class GameService {

	private Game game;
	private int nbGames;
	private int tempo;
	private Map<String, Long> lastTurnTimes;

	public GameService() {
		reset();
	}

	public Game getCurrentGame() {
		return game;
	}

	public void launchNewGame(int gridSize) {
		game = new Game(gridSize);
		game.setGameId(++nbGames);
	}

	public void reset() {
		nbGames = 0;
		game = new Game();
		game.setGameId(++nbGames);
		tempo = 0;
		lastTurnTimes = Maps.newHashMap();
	}

	public void temporize(int duration) { // ms
		tempo = duration;
	}

	public boolean canPlay(String ip) {
		long now = new Date().getTime();
		if (!lastTurnTimes.containsKey(ip) || (now - lastTurnTimes.get(ip)) > tempo) {
			lastTurnTimes.put(ip, now);
			return true;
		}

		return false;
	}
}
