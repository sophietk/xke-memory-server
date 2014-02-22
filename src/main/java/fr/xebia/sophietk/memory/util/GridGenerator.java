package fr.xebia.sophietk.memory.util;


import com.google.common.collect.Lists;
import fr.xebia.sophietk.memory.service.Card;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GridGenerator {

	private static final List<String> CARDS_SYMBOLS = Arrays.asList("balloon", "dog", "coat", "boat", "umbrella");
	private static final List<String> CARDS_COLORS = Arrays.asList("red", "blue", "yellow", "green");

	private static List<Card> allCards() {
		List<Card> allCards = Lists.newArrayList();
		for (String symbol : CARDS_SYMBOLS) {
			for (String color : CARDS_COLORS) {
				allCards.add(new Card(symbol, color));
			}
		}
		Collections.shuffle(allCards);
		return allCards;
	}

	public static Card[][] generate(int gridSize) {
		if (gridSize * gridSize % 2 != 0)
			throw new RuntimeException("The grid size should enable the grid to contain an even number of cards");
		if (gridSize * gridSize % 2 > CARDS_SYMBOLS.size() * CARDS_COLORS.size())
			throw new RuntimeException("The grid cannot contain more than possible cards number");

		List<Card> randomCards = allCards().subList(0, gridSize * gridSize / 2);
		randomCards.addAll(randomCards); // Duplicate cards
		Collections.shuffle(randomCards);

		Card[][] grid = new Card[gridSize][gridSize];
		for (int i = 0; i < gridSize * gridSize; i++) {
			int x = i % gridSize;
			int y = i / gridSize;
			grid[x][y] = randomCards.get(i);
		}

		return grid;
	}
}
