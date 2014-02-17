package fr.xebia.sophietk.memory.service;

import fr.xebia.sophietk.memory.util.GridGenerator;
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

		Turn result = game.play(positions);

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

		Turn result = game.play(positions);

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

		Turn result = game.play(positions);

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

		List<CardPosition> positions = findSomeUndiscoveredCardsCouple(game);

		Turn result = game.play(positions);

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

		List<CardPosition> firstTurnPositions = findSomeUndiscoveredCardsCouple(game);
		game.play(firstTurnPositions);

		List<CardPosition> secondTurnPositions = new ArrayList<CardPosition>();
		secondTurnPositions.add(firstTurnPositions.get(0));
		secondTurnPositions.add(findSomeNotFoundCard(game));
		Turn result = game.play(secondTurnPositions);

		assertTrue(result.getTurnScore() == -3);
		assertNull(result.getMessage());
		assertNotNull(result.getCards());
		assertEquals(2, result.getCards().size());
		assertNotEquals(result.getCards().get(0), result.getCards().get(1));
		assertTrue(result.getCards().get(0).isFound());
		assertFalse(result.getCards().get(1).isFound());
	}

	@Test
	public void should_lose_1_point_when_outside_the_grid() {
		Game game = new Game();

		assertTrue(game.getProgress() == 0);

		List<CardPosition> positions = new ArrayList<CardPosition>();
		positions.add(new CardPosition(30, 0));
		positions.add(new CardPosition(0, 5));
		Turn turn = game.play(positions);

		assertTrue(-1 == turn.getTurnScore());
		assertEquals("You cannot play outside the 6x6 grid", turn.getMessage());
	}

	@Test
	public void should_lose_1_point_when_negative_coordinates() {
		Game game = new Game();

		assertTrue(game.getProgress() == 0);

		List<CardPosition> positions = new ArrayList<CardPosition>();
		positions.add(new CardPosition(-5, 0));
		positions.add(new CardPosition(0, -5));
		Turn turn = game.play(positions);

		assertTrue(-1 * 2 == turn.getTurnScore());
		assertEquals("You cannot play outside the 6x6 grid", turn.getMessage());
	}

	@Test
	public void should_return_updated_game_progress() {
		Game game = new Game();

		assertTrue(game.getProgress() == 0);

		game.play(findSomeUndiscoveredCardsCouple(game));
		double firstTurnGameProgress = game.getProgress();
		assertTrue(firstTurnGameProgress > 0);

		game.play(findSomeUndiscoveredCardsCouple(game));
		double secondTurnGameProgress = game.getProgress();
		assertTrue(secondTurnGameProgress > 0);
		assertTrue(secondTurnGameProgress > firstTurnGameProgress);
	}

	private List<CardPosition> findSomeUndiscoveredCardsCouple(Game game) {
		Card[][] grid = game.getGrid();
		Card firstCard = null;
		CardPosition firstCardPosition = null;
		CardPosition secondCardPosition = null;
		outerloop:
		for (int i = 0; i < GridGenerator.GRID_SIZE; i++) {
			for (int j = 0; j < GridGenerator.GRID_SIZE; j++) {
				if (firstCardPosition == null && !grid[i][j].isFound()) {
					firstCard = grid[i][j];
					firstCardPosition = new CardPosition(i, j);
				}
				if (firstCardPosition != null && !(i == firstCardPosition.getX() && j == firstCardPosition.getY()) && grid[i][j].equals(firstCard)) {
					secondCardPosition = new CardPosition(i, j);
					break outerloop;
				}
			}
		}

		List<CardPosition> positions = new ArrayList<CardPosition>();
		positions.add(firstCardPosition);
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
