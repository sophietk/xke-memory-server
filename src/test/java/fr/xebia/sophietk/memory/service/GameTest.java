package fr.xebia.sophietk.memory.service;

import com.google.common.collect.Lists;
import org.junit.Test;

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

		List<CardPosition> positions = Lists.newArrayList();
		positions.add(new CardPosition(0, 1));

		Turn result = game.play(positions);

		assertEquals(-1, result.getTurnScore());
		assertEquals("You cannot turn less or more than 2 cards", result.getMessage());
		assertNull(result.getCards());
	}

	@Test
	public void should_lose_1_point_when_playing_more_than_two_cards() {
		Game game = new Game();

		List<CardPosition> positions = Lists.newArrayList();
		positions.add(new CardPosition(0, 1));
		positions.add(new CardPosition(0, 1));
		positions.add(new CardPosition(0, 1));

		Turn result = game.play(positions);

		assertEquals(-1, result.getTurnScore());
		assertEquals("You cannot turn less or more than 2 cards", result.getMessage());
		assertNull(result.getCards());
	}

	@Test
	public void should_win_no_point_when_playing_same_position() {
		Game game = new Game();

		List<CardPosition> positions = Lists.newArrayList();
		positions.add(new CardPosition(0, 1));
		positions.add(new CardPosition(0, 1));

		Turn result = game.play(positions);

		assertEquals(0, result.getTurnScore());
		assertNull(result.getMessage());
		assertNotNull(result.getCards());
		assertEquals(2, result.getCards().size());
		assertEquals(result.getCards().get(0), result.getCards().get(1));
		assertFalse(result.getCards().get(0).isFound());
		assertFalse(result.getCards().get(1).isFound());
	}

	@Test
	public void should_win_10_points_when_finding_a_cards_pair() {
		Game game = new Game();

		List<CardPosition> positions = findSomeUndiscoveredCardsPair(game);

		Turn result = game.play(positions);

		assertEquals(10, result.getTurnScore());
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

		List<CardPosition> firstTurnPositions = findSomeUndiscoveredCardsPair(game);
		game.play(firstTurnPositions);

		List<CardPosition> secondTurnPositions = Lists.newArrayList();
		secondTurnPositions.add(firstTurnPositions.get(0));
		secondTurnPositions.add(findSomeNotFoundCard(game));
		Turn result = game.play(secondTurnPositions);

		assertEquals(-3, result.getTurnScore());
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

		assertEquals(0, game.getProgress(), 0);

		List<CardPosition> positions = Lists.newArrayList();
		positions.add(new CardPosition(30, 0));
		positions.add(new CardPosition(0, 5));
		Turn turn = game.play(positions);

		assertEquals(-1, turn.getTurnScore());
		assertEquals("You cannot play outside the 6x6 grid", turn.getMessage());
	}

	@Test
	public void should_lose_1_point_when_negative_coordinates() {
		Game game = new Game();

		assertEquals(0, game.getProgress(), 0);

		List<CardPosition> positions = Lists.newArrayList();
		positions.add(new CardPosition(-5, 0));
		positions.add(new CardPosition(0, -5));
		Turn turn = game.play(positions);

		assertEquals(-1 * 2, turn.getTurnScore());
		assertEquals("You cannot play outside the 6x6 grid", turn.getMessage());
	}

	@Test
	public void should_return_updated_game_progress() {
		Game game = new Game();

		assertEquals(0, game.getProgress(), 0);

		game.play(findSomeUndiscoveredCardsPair(game));
		double firstTurnGameProgress = game.getProgress();
		assertTrue(firstTurnGameProgress > 0);

		game.play(findSomeUndiscoveredCardsPair(game));
		double secondTurnGameProgress = game.getProgress();
		assertTrue(secondTurnGameProgress > 0);
		assertTrue(secondTurnGameProgress > firstTurnGameProgress);
	}

	@Test
	public void should_play_in_game_with_appropriate_size() {
		Game game = new Game();
		assertEquals(Game.DEFAULT_GRID_SIZE, game.getGridSize());

		List<CardPosition> positions = Lists.newArrayList();
		positions.add(new CardPosition(0, 0));
		positions.add(new CardPosition(Game.DEFAULT_GRID_SIZE - 1, Game.DEFAULT_GRID_SIZE - 1));
		Turn turn = game.play(positions);
		assertTrue(turn.getTurnScore() >= 0); // not outside the grid

		Game game2 = new Game(4);
		assertEquals(4, game2.getGridSize());

		Turn turn2 = game2.play(positions);
		assertTrue(turn2.getTurnScore() < 0); // outside the grid
	}

	private List<CardPosition> findSomeUndiscoveredCardsPair(Game game) {
		Card[][] grid = game.getGrid();
		Card firstCard = null;
		CardPosition firstCardPosition = null;
		CardPosition secondCardPosition = null;
		outerloop:
		for (int i = 0; i < game.getGridSize(); i++) {
			for (int j = 0; j < game.getGridSize(); j++) {
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

		List<CardPosition> positions = Lists.newArrayList();
		positions.add(firstCardPosition);
		positions.add(secondCardPosition);
		return positions;
	}

	private CardPosition findSomeNotFoundCard(Game game) {
		Card[][] grid = game.getGrid();
		CardPosition notFoundCardPosition = null;
		outerloop:
		for (int i = 0; i < game.getGridSize(); i++) {
			for (int j = 0; j < game.getGridSize(); j++) {
				if (!grid[i][j].isFound()) {
					notFoundCardPosition = new CardPosition(i, j);
					break outerloop;
				}
			}
		}

		return notFoundCardPosition;
	}
}
