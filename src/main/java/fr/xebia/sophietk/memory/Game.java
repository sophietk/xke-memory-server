package fr.xebia.sophietk.memory;

import com.google.common.annotations.VisibleForTesting;

import java.util.ArrayList;
import java.util.List;

public class Game {

	private Card[][] grid;

	public Game() {
		grid = GridGenerator.generate();
	}

	@VisibleForTesting
	protected Card[][] getGrid() {
		return grid;
	}

	public TurnResult play(List<CardPosition> positions) {
		TurnResult turnResult = new TurnResult();

		if (positions.size() != 2) {
			turnResult.incrementScore(-1);
			turnResult.setMessage("You cannot turn less or more than 2 cards");
			return turnResult;
		}

		List<Card> cards = new ArrayList<Card>();
		for (CardPosition position : positions) {
			Card card = grid[position.getX()][position.getY()];
			if (card.isFound()) {
				turnResult.incrementScore(-3);
			}
			cards.add(card);
		}
		turnResult.setCards(cards);

		if (!positions.get(0).equals(positions.get(1)) && !cards.get(0).isFound() && !cards.get(1).isFound() && cards.get(0).equals(cards.get(1))) {
			turnResult.incrementScore(10);
			cards.get(0).switchFound();
			cards.get(1).switchFound();
		}

		return turnResult;
	}
}
