package fr.xebia.sophietk.memory.util;


import fr.xebia.sophietk.memory.service.Card;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GridGenerator {

	//public static final int GRID_SIZE = 2;

	private static final List<String> CARDS_SYMBOLS = Arrays.asList("balloon", "dog", "coat", "boat", "umbrella");
	private static final List<String> CARDS_COLORS = Arrays.asList("red", "blue", "yellow", "green");

	public static Card[][] generate(int gridSize) {
		if (gridSize * gridSize % 2 != 0)
			throw new RuntimeException("La grille doit permettre de placer un nombre pair de cartes");

		List<Card> randomCards = new ArrayList<Card>();
		Collections.shuffle(CARDS_SYMBOLS);
		Collections.shuffle(CARDS_COLORS);
		outerloop:
		for (String symbol : CARDS_SYMBOLS) {
			for (String color : CARDS_COLORS) {
				randomCards.add(new Card(symbol, color));
				if (randomCards.size() == gridSize * gridSize / 2) break outerloop;
			}
		}
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
