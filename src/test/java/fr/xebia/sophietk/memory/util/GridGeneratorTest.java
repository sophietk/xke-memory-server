package fr.xebia.sophietk.memory.util;

import fr.xebia.sophietk.memory.service.Card;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GridGeneratorTest {

	private static final int TEST_CARDS_COUPLE = 10;

	@Test
	public void should_generate_random_grid() {
		List<Card> grid = GridGenerator.generate(TEST_CARDS_COUPLE);
		List<Card> grid2 = GridGenerator.generate(TEST_CARDS_COUPLE);

		assertFalse(grid.containsAll(grid2));
	}

	@Test
	public void should_generate_grid_with_couple_of_cards() {
		List<Card> grid = GridGenerator.generate(10);

		Map<String, Integer> foundCards = new HashMap<String, Integer>();
		for (Card card : grid) {
			String cardDescription = card.toString();
			if (foundCards.containsKey(cardDescription)) {
				int sameCardsNumber = foundCards.get(cardDescription) + 1;
				foundCards.put(cardDescription, sameCardsNumber);
			} else {
				foundCards.put(cardDescription, 1);
			}
		}

		assertEquals(TEST_CARDS_COUPLE, foundCards.size());
		for (Integer numberDifferentCards : foundCards.values()) {
			assertTrue(numberDifferentCards == 2);
		}
	}

	@Test
	public void should_generate_grid_with_appropriate_size() {
		List<Card> grid = GridGenerator.generate(5);
		assertEquals(5 * 2, grid.size());

		List<Card> grid2 = GridGenerator.generate(12);
		assertEquals(12 * 2, grid2.size());
	}
}
