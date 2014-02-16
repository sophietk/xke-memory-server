package fr.xebia.sophietk.memory.util;

import fr.xebia.sophietk.memory.service.Card;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GridGeneratorTest {

	@Test
	public void should_generate_random_grid() {
		Card[][] grid = GridGenerator.generate();
		Card[][] grid2 = GridGenerator.generate();

		assertFalse(Arrays.deepEquals(grid, grid2));
	}

	@Test
	public void should_generate_grid_with_couple_of_cards() {
		Card[][] grid = GridGenerator.generate();

		Map<String, Integer> foundCards = new HashMap<String, Integer>();
		for (Card[] cardLines : grid) {
			for (Card card : cardLines) {
				String cardDescription = card.toString();
				if (foundCards.containsKey(cardDescription)) {
					int sameCardsNumber = foundCards.get(cardDescription) + 1;
					foundCards.put(cardDescription, sameCardsNumber);
				} else {
					foundCards.put(cardDescription, 1);
				}
			}
		}

		assertEquals(GridGenerator.GRID_SIZE * GridGenerator.GRID_SIZE / 2, foundCards.size());
		for (Integer numberDifferentCards : foundCards.values()) {
			assertTrue(numberDifferentCards == 2);
		}
	}
}
