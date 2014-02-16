package fr.xebia.sophietk.memory.util;


import com.sun.tools.javac.util.Assert;
import fr.xebia.sophietk.memory.service.Card;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GridGenerator {

	public static final int GRID_SIZE = 6;

	private static final List<String> CARDS_SYMBOLS = Arrays.asList("balloon", "dog", "coat", "boat", "umbrella");
	private static final List<String> CARDS_COLORS = Arrays.asList("red", "blue", "yellow", "green");

	public static Card[][] generate() {
		Assert.check(GRID_SIZE * GRID_SIZE % 2 == 0);

		List<Card> randomCards = new ArrayList<Card>();
		Collections.shuffle(CARDS_SYMBOLS);
		Collections.shuffle(CARDS_COLORS);
		outerloop:
		for (String symbol : CARDS_SYMBOLS) {
			for (String color : CARDS_COLORS) {
				randomCards.add(new Card(symbol, color));
				if (randomCards.size() == GRID_SIZE * GRID_SIZE / 2) break outerloop;
			}
		}
		randomCards.addAll(randomCards); // Duplicate cards
		Collections.shuffle(randomCards);

		Card[][] grid = new Card[GRID_SIZE][GRID_SIZE];
		for (int i = 0; i < GRID_SIZE * GRID_SIZE; i++) {
			int x = i % GRID_SIZE;
			int y = i / GRID_SIZE;
			grid[x][y] = randomCards.get(i);
		}

		return grid;
	}
}
