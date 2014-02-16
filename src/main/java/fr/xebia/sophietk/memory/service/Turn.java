package fr.xebia.sophietk.memory.service;

import java.util.List;

public class Turn {

	private int turnScore = 0;
	private List<Card> cards;
	private String message;

	public int getTurnScore() {
		return turnScore;
	}

	public void incrementScore(int turnScore) {
		this.turnScore += turnScore;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<Card> getCards() {
		return cards;
	}

	public void setCards(List<Card> cards) {
		this.cards = cards;
	}

}
