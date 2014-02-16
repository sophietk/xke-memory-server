package fr.xebia.sophietk.memory.service;

import fr.xebia.sophietk.memory.util.GridGenerator;

import java.util.ArrayList;
import java.util.List;

public class Game {

	private Card[][] grid;
	private int discoveredCards;

	public Game() {
		grid = GridGenerator.generate();
		discoveredCards = 0;
	}

	protected Card[][] getGrid() {
		return grid;
	}

	public Turn play(List<CardPosition> positions) {
		Turn turn = new Turn();

		if (positions.size() != 2) {
			turn.incrementScore(-1);
			turn.setMessage("You cannot turn less or more than 2 cards");
			return turn;
		}

		List<Card> cards = new ArrayList<Card>();
		for (CardPosition position : positions) {
			Card card = grid[position.getX()][position.getY()];
			if (card.isFound()) {
				turn.incrementScore(-3);
			}
			cards.add(card);
		}
		turn.setCards(cards);

		if (!positions.get(0).equals(positions.get(1)) && !cards.get(0).isFound() && !cards.get(1).isFound() && cards.get(0).equals(cards.get(1))) {
			turn.incrementScore(10);
			cards.get(0).switchFound();
			cards.get(1).switchFound();
			discoveredCards += 2;
		}

		return turn;
	}

	public double getProgress() {
		return 100 * discoveredCards / (GridGenerator.GRID_SIZE * GridGenerator.GRID_SIZE);
	}
}