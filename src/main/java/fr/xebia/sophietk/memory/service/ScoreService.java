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
	private Map<String, Integer> scores;

	private Map<String, String> players;

	public ScoreService() {
		reset();
	}

	public void reset() {
		scores = Maps.newHashMap();
		players = Maps.newHashMap();
	}

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
				gameScores.put(getPlayerName(player), scores.get(key));
			}
		}

		return gameScores;
	}

	public Map<Integer, Integer> getPlayerScores(String player) {
		String playerIp = getPlayerIp(player);

		Map<Integer, Integer> playerScores = Maps.newHashMap();
		for (String key : scores.keySet()) {
			String currentPlayer = key.split(KEY_SEPARATOR)[0];
			if (playerIp.equals(currentPlayer)) {
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
			allPlayers.add(getPlayerName(currentPlayer));
		}
		return allPlayers;
	}

	public Map<String, Integer> getTotalScores() {
		Map<String, Integer> totalScores = Maps.newHashMap();
		for (String key : scores.keySet()) {
			String currentPlayer = key.split(KEY_SEPARATOR)[0];
			int addedScore = totalScores.containsKey(currentPlayer) ? totalScores.get(currentPlayer) + scores.get(key) : scores.get(key);
			totalScores.put(getPlayerName(currentPlayer), addedScore);
		}

		return totalScores;
	}

	public void registerPlayer(String ip, String player) {
		players.put(ip, player);
	}

	/**
	 * @param ip String player ip
	 * @return player name or ip
	 */
	private String getPlayerName(String ip) {
		return players.containsKey(ip) ? players.get(ip) : ip;
	}

	/**
	 * @param player String player name or ip
	 * @return player ip
	 */
	private String getPlayerIp(String player) {
		for (String playerIp : players.keySet()) {
			if (players.get(playerIp).equals(player)) return playerIp;
		}
		return player;
	}
}
