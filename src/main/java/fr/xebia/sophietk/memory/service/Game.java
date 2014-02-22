package fr.xebia.sophietk.memory.service;

import com.google.common.collect.Lists;
import fr.xebia.sophietk.memory.util.GridGenerator;

import java.util.List;

public class Game {

	public static final int DEFAULT_GRID_SIZE = 6;

	private int gameId;
	private Card[][] grid;
	private int gridSize;
	private int discoveredCards;

	public Game() {
		this(DEFAULT_GRID_SIZE);
	}

	public Game(int gridSize) {
		grid = GridGenerator.generate(gridSize);
		this.gridSize = gridSize;
		discoveredCards = 0;
	}

	public int getGameId() {
		return gameId;
	}

	public void setGameId(int gameId) {
		this.gameId = gameId;
	}

	public Card[][] getGrid() {
		return grid;
	}

	public int getGridSize() {
		return gridSize;
	}

	public double getProgress() {
		return 100 * discoveredCards / (gridSize * gridSize);
	}

	public Turn play(List<CardPosition> positions) {
		Turn turn = new Turn();

		if (positions.size() != 2) {
			turn.incrementScore(-1);
			turn.setMessage("You cannot turn less or more than 2 cards");
			return turn;
		}

		List<Card> cards = Lists.newArrayList();
		for (CardPosition position : positions) {
			try {
				Card card = grid[position.getX()][position.getY()];
				if (card.isFound()) {
					turn.incrementScore(-3);
				}
				cards.add(card);
			} catch (ArrayIndexOutOfBoundsException e) {
				turn.incrementScore(-1);
				turn.setMessage(String.format("You cannot play outside the %dx%d grid", gridSize, gridSize));
			}
		}
		turn.setCards(cards);

		if (turn.getCards().size() == 2 && !positions.get(0).equals(positions.get(1)) && !cards.get(0).isFound() && !cards.get(1).isFound() && cards.get(0).equals(cards.get(1))) {
			turn.incrementScore(10);
			cards.get(0).switchFound();
			cards.get(1).switchFound();
			discoveredCards += 2;
		}

		return turn;
	}
}
