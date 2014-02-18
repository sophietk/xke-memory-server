package fr.xebia.sophietk.memory.resource;

import fr.xebia.sophietk.memory.service.Turn;

public class MemoryResponse {

	private int gameId = 0; // numéro de la partie
	private double progress = 0; // avancement (0 à 100 en pourcents)
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
