package fr.xebia.sophietk.memory;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class GameTest {

	@Test
	public void should_lose_1_point_when_playing_only_one_card() {
		Game game = new Game();

		List<CardPosition> positions = new ArrayList<CardPosition>();
		positions.add(new CardPosition(0, 1));

		TurnResult result = game.play(positions);

		assertTrue(result.getTurnScore() == -1);
		assertEquals("You cannot turn less or more than 2 cards", result.getMessage());
		assertNull(result.getCards());
	}

	@Test
	public void should_lose_1_point_when_playing_more_than_two_cards() {
		Game game = new Game();

		List<CardPosition> positions = new ArrayList<CardPosition>();
		positions.add(new CardPosition(0, 1));
		positions.add(new CardPosition(0, 1));
		positions.add(new CardPosition(0, 1));

		TurnResult result = game.play(positions);

		assertTrue(result.getTurnScore() == -1);
		assertEquals("You cannot turn less or more than 2 cards", result.getMessage());
		assertNull(result.getCards());
	}

	@Test
	public void should_win_no_point_when_playing_same_position() {
		Game game = new Game();

		List<CardPosition> positions = new ArrayList<CardPosition>();
		positions.add(new CardPosition(0, 1));
		positions.add(new CardPosition(0, 1));

		TurnResult result = game.play(positions);

		assertTrue(result.getTurnScore() == 0);
		assertNull(result.getMessage());
		assertNotNull(result.getCards());
		assertEquals(2, result.getCards().size());
		assertEquals(result.getCards().get(0), result.getCards().get(1));
		assertFalse(result.getCards().get(0).isFound());
		assertFalse(result.getCards().get(1).isFound());
	}

	@Test
	public void should_win_10_points_when_finding_a_cards_couple() {
		Game game = new Game();

		List<CardPosition> positions = findSomeCardsCouple(game);

		TurnResult result = game.play(positions);

		assertTrue(result.getTurnScore() == 10);
		assertNull(result.getMessage());
		assertNotNull(result.getCards());
		assertEquals(2, result.getCards().size());
		assertEquals(result.getCards().get(0), result.getCards().get(1));
		assertTrue(result.getCards().get(0).isFound());
		assertTrue(result.getCards().get(1).isFound());
	}

	@Test
	public void should_lose_3_points_when_a_played_card_was_already_found() {
		Game game = new Game();

		List<CardPosition> firstTurnPositions = findSomeCardsCouple(game);
		game.play(firstTurnPositions);

		List<CardPosition> secondTurnPositions = new ArrayList<CardPosition>();
		secondTurnPositions.add(firstTurnPositions.get(0));
		secondTurnPositions.add(findSomeNotFoundCard(game));
		TurnResult result = game.play(secondTurnPositions);

		assertTrue(result.getTurnScore() == -3);
		assertNull(result.getMessage());
		assertNotNull(result.getCards());
		assertEquals(2, result.getCards().size());
		assertNotEquals(result.getCards().get(0), result.getCards().get(1));
		assertTrue(result.getCards().get(0).isFound());
		assertFalse(result.getCards().get(1).isFound());
	}

	private List<CardPosition> findSomeCardsCouple(Game game) {
		Card[][] grid = game.getGrid();
		Card firstCard = grid[0][0];
		CardPosition secondCardPosition = null;
		outerloop:
		for (int i = 0; i < GridGenerator.GRID_SIZE; i++) {
			for (int j = 0; j < GridGenerator.GRID_SIZE; j++) {
				if (!(i == 0 && j == 0) && grid[i][j].equals(firstCard)) {
					secondCardPosition = new CardPosition(i, j);
					break outerloop;
				}
			}
		}

		List<CardPosition> positions = new ArrayList<CardPosition>();
		positions.add(new CardPosition(0, 0));
		positions.add(secondCardPosition);
		return positions;
	}

	private CardPosition findSomeNotFoundCard(Game game) {
		Card[][] grid = game.getGrid();
		CardPosition notFoundCardPosition = null;
		outerloop:
		for (int i = 0; i < GridGenerator.GRID_SIZE; i++) {
			for (int j = 0; j < GridGenerator.GRID_SIZE; j++) {
				if (!grid[i][j].isFound()) {
					notFoundCardPosition = new CardPosition(i, j);
					break outerloop;
				}
			}
		}

		return notFoundCardPosition;
	}
}
