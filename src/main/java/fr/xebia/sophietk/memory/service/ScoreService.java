package fr.xebia.sophietk.memory.service;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.inject.Singleton;

import java.util.Map;
import java.util.Set;

@Singleton
public class ScoreService {

	private static final String KEY_SEPARATOR = " ";

	// TODO : replace with MultiKeyMap
	private Map<String, Integer> scores = Maps.newHashMap();

	public int addTurnScore(String player, int gameId, int turnScore) {
		String key = player + KEY_SEPARATOR + gameId;
		int newGameScore = scores.containsKey(key) ? scores.get(key) + turnScore : turnScore;
		scores.put(key, newGameScore);

		return newGameScore;
	}

	public Map<String, Integer> getGameScores(int gameId) {
		Map<String, Integer> gameScores = Maps.newHashMap();
		for (String key : scores.keySet()) {
			int currentGameId = Integer.parseInt(key.split(KEY_SEPARATOR)[1]);
			if (currentGameId == gameId) {
				String player = key.split(KEY_SEPARATOR)[0];
				gameScores.put(player, scores.get(key));
			}
		}

		return gameScores;
	}

	public Map<Integer, Integer> getPlayerScores(String player) {
		Map<Integer, Integer> playerScores = Maps.newHashMap();
		for (String key : scores.keySet()) {
			String currentPlayer = key.split(KEY_SEPARATOR)[0];
			if (player.equals(currentPlayer)) {
				int gameId = Integer.parseInt(key.split(KEY_SEPARATOR)[1]);
				playerScores.put(gameId, scores.get(key));
			}
		}

		return playerScores;
	}

	public Set<Integer> getAllGamesId() {
		Set<Integer> allGamesId = Sets.newHashSet();
		for (String key : scores.keySet()) {
			int currentGameId = Integer.parseInt(key.split(KEY_SEPARATOR)[1]);
			allGamesId.add(currentGameId);
		}
		return allGamesId;
	}

	public Set<String> getAllPlayers() {
		Set<String> allPlayers = Sets.newHashSet();
		for (String key : scores.keySet()) {
			String currentPlayer = key.split(KEY_SEPARATOR)[0];
			allPlayers.add(currentPlayer);
		}
		return allPlayers;
	}
}
