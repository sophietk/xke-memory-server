package fr.xebia.sophietk.memory.resource;

import fr.xebia.sophietk.memory.service.Turn;

public class MemoryResponse {

	private int gameId = 0; // current game id
	private double progress = 0; // from 0 to 100, in percents
	private Turn turn;
	private int gameScore;

	public MemoryResponse() {
	}

	public MemoryResponse(int gameId, double progress, Turn turn, int gameScore) {
		this.gameId = gameId;
		this.progress = progress;
		this.turn = turn;
		this.gameScore = gameScore;
	}

	public int getGameId() {
		return gameId;
	}

	public double getProgress() {
		return progress;
	}

	public Turn getTurn() {
		return turn;
	}

	public int getGameScore() {
		return gameScore;
	}
}
