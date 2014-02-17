package fr.xebia.sophietk.memory.service;

import com.google.inject.Singleton;

import java.util.HashMap;
import java.util.Map;

@Singleton
public class ScoreService {

	private Map<String, Integer> scores = new HashMap<String, Integer>();

	public int addTurnScoreAndReturnGameScore(String player, int gameId, int turnScore) {
		String key = player + " " + gameId;
		int newGameScore = scores.containsKey(key) ? scores.get(key) + turnScore : turnScore;
		scores.put(key, newGameScore);

		return newGameScore;
	}

	public Map<String, Integer> getGameScores(int gameId) {
		Map<String, Integer> gameScores = new HashMap<String, Integer>();
		for (String key : scores.keySet()) {
			int currentGameId = Integer.parseInt(key.split(" ")[1]);
			if (currentGameId == gameId) {
				String player = key.split(" ")[0];
				gameScores.put(player, scores.get(key));
			}
		}

		return gameScores;
	}

	public Map<Integer, Integer> getPlayerScores(String player) {
		Map<Integer, Integer> playerScores = new HashMap<Integer, Integer>();
		for (String key : scores.keySet()) {
			String currentPlayer = key.split(" ")[0];
			if (player.equals(currentPlayer)) {
				int gameId = Integer.parseInt(key.split(" ")[1]);
				playerScores.put(gameId, scores.get(key));
			}
		}

		return playerScores;
	}
}
