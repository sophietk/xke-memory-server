package fr.xebia.sophietk.memory.service;

import java.util.HashMap;
import java.util.Map;

public class ScoreService {

	private Map<String, Integer> scores = new HashMap<String, Integer>();

	public int addTurnScoreAndReturnGameScore(String ip, int gameId, int turnScore) {
		String key = ip + " " + gameId;
		int newGameScore = scores.containsKey(key) ? scores.get(key) + turnScore : turnScore;
		scores.put(key, newGameScore);

		return newGameScore;
	}
}