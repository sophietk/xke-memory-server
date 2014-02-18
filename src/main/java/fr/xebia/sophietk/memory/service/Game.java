package fr.xebia.sophietk.memory.service;

import fr.xebia.sophietk.memory.util.GridGenerator;

import java.util.ArrayList;
import java.util.List;

public class Game {

	public static final int DEFAULT_NB_CARDS_COUPLE = 20;

	private List<Card> grid;
	private int nbCards;
	private int nbDiscoveredCards;

	public Game() {
		this(DEFAULT_NB_CARDS_COUPLE);
	}

	public Game(int cardsCouple) {
		grid = GridGenerator.generate(cardsCouple);
		nbCards = cardsCouple * 2;
		nbDiscoveredCards = 0;
	}

	protected List<Card> getGrid() {
		return grid;
	}

	public Turn play(List<Integer> positions) {
		Turn turn = new Turn();

		if (positions.size() != 2) {
			turn.incrementScore(-1);
			turn.setMessage("You cannot turn less or more than 2 cards");
			return turn;
		}

		List<Card> cards = new ArrayList<Card>();
		for (int position : positions) {
			try {
				Card card = grid.get(position);
				if (card.isFound()) {
					turn.incrementScore(-3);
				}
				cards.add(card);
			} catch (IndexOutOfBoundsException e) {
				turn.incrementScore(-1);
				turn.setMessage(String.format("You cannot play outside the grid (%d cards)", nbCards));
			}
		}
		turn.setCards(cards);

		if (turn.getCards().size() == 2
				&& !positions.get(0).equals(positions.get(1))
				&& !cards.get(0).isFound()
				&& !cards.get(1).isFound()
				&& cards.get(0).sameAs(cards.get(1))) {
			turn.incrementScore(10);
			cards.get(0).switchFound();
			cards.get(1).switchFound();
			nbDiscoveredCards += 2;
		}

		return turn;
	}

	public double getProgress() {
		return 100 * nbDiscoveredCards / nbCards;
	}
}
