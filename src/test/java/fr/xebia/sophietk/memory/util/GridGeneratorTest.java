package fr.xebia.sophietk.memory.util;

import com.google.common.collect.Maps;
import fr.xebia.sophietk.memory.service.Card;
import org.junit.Test;

import java.util.Arrays;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class GridGeneratorTest {

	private static final int TEST_GRID_SIZE = 6;

	@Test
	public void should_generate_random_grid() {
		Card[][] grid = GridGenerator.generate(TEST_GRID_SIZE);
		Card[][] grid2 = GridGenerator.generate(TEST_GRID_SIZE);

		assertFalse(Arrays.deepEquals(grid, grid2));
	}

	@Test
	public void should_generate_grid_with_cards_pairs() {
		Card[][] grid = GridGenerator.generate(TEST_GRID_SIZE);

		Map<String, Integer> foundCards = Maps.newHashMap();
		for (Card[] cardLines : grid) {
			for (Card card : cardLines) {
				String cardDescription = card.toString();
				int sameCardsNumber = foundCards.containsKey(cardDescription) ? foundCards.get(cardDescription) + 1 : 1;
				foundCards.put(cardDescription, sameCardsNumber);
			}
		}

		assertEquals(TEST_GRID_SIZE * TEST_GRID_SIZE / 2, foundCards.size());
		for (Integer numberDifferentCards : foundCards.values()) {
			assertEquals(2, (int) numberDifferentCards);
		}
	}

	@Test
	public void should_generate_grid_with_appropriate_size() {
		Card[][] grid = GridGenerator.generate(2);

		assertEquals(2, grid.length);
		for (Card[] line : grid) {
			assertEquals(2, line.length);
		}
	}

	@Test(expected = RuntimeException.class)
	public void should_throw_error_when_grid_cannot_be_built_with_odd_cards_number() {
		GridGenerator.generate(5);
	}

	@Test(expected = RuntimeException.class)
	public void should_throw_error_when_grid_cannot_be_built_with_too_high_cards_number() {
		GridGenerator.generate(8);
	}
}
