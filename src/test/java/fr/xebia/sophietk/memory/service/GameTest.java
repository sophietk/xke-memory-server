package fr.xebia.sophietk.memory.service;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class GameTest {

	@Test
	public void should_play_in_same_game() {
		Game game = new Game();

		List<Integer> positions = Lists.newArrayList(0, 1);
		Turn result = game.play(positions);
		Turn result2 = game.play(positions);

		assertEquals(result.getCards().get(0), result2.getCards().get(0));
		assertEquals(result.getCards().get(1), result2.getCards().get(1));
	}

	@Test
	public void should_lose_1_point_when_playing_only_one_card() {
		Game game = new Game();

		List<Integer> positions = Lists.newArrayList(0);
		Turn result = game.play(positions);

		assertTrue(result.getTurnScore() == -1);
		assertEquals("You cannot turn less or more than 2 cards", result.getMessage());
		assertNull(result.getCards());
	}

	@Test
	public void should_lose_1_point_when_playing_more_than_two_cards() {
		Game game = new Game();

		List<Integer> positions = Lists.newArrayList(0, 0, 0);
		Turn result = game.play(positions);

		assertTrue(result.getTurnScore() == -1);
		assertEquals("You cannot turn less or more than 2 cards", result.getMessage());
		assertNull(result.getCards());
	}

	@Test
	public void should_win_no_point_when_playing_same_position() {
		Game game = new Game();

		List<Integer> positions = Lists.newArrayList(0, 0);
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

		List<Integer> positions = findSomeUndiscoveredCardsCouple(game);
		Turn result = game.play(positions);

		assertTrue(result.getTurnScore() == 10);
		assertNull(result.getMessage());
		assertNotNull(result.getCards());
		assertEquals(2, result.getCards().size());
		assertTrue(result.getCards().get(0).sameAs(result.getCards().get(1)));
		assertTrue(result.getCards().get(0).isFound());
		assertTrue(result.getCards().get(1).isFound());
	}

	@Test
	public void should_lose_3_points_when_a_played_card_was_already_found() {
		Game game = new Game();

		List<Integer> firstTurnPositions = findSomeUndiscoveredCardsCouple(game);
		game.play(firstTurnPositions);

		List<Integer> secondTurnPositions = new ArrayList<Integer>();
		secondTurnPositions.add(firstTurnPositions.get(0));
		secondTurnPositions.add(findSomeNotFoundCard(game));
		Turn result = game.play(secondTurnPositions);

		assertTrue(result.getTurnScore() == -3);
		assertNull(result.getMessage());
		assertNotNull(result.getCards());
		assertEquals(2, result.getCards().size());
		assertFalse(result.getCards().get(0).sameAs(result.getCards().get(1)));
		assertTrue(result.getCards().get(0).isFound());
		assertFalse(result.getCards().get(1).isFound());
	}

	@Test
	public void should_lose_1_point_when_outside_the_grid() {
		Game game = new Game();

		assertTrue(game.getProgress() == 0);

		List<Integer> positions = Lists.newArrayList(0, 4242);
		Turn turn = game.play(positions);

		assertTrue(-1 == turn.getTurnScore());
		assertEquals("You cannot play outside the grid (40 cards)", turn.getMessage());
	}

	@Test
	public void should_lose_1_point_when_negative_coordinates() {
		Game game = new Game();

		assertTrue(game.getProgress() == 0);

		List<Integer> positions = Lists.newArrayList(-5, -5);
		Turn turn = game.play(positions);

		assertTrue(-1 * 2 == turn.getTurnScore());
		assertEquals("You cannot play outside the grid (40 cards)", turn.getMessage());
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

	@Test
	public void should_play_in_game_with_appropriate_size() {
		Game game = new Game();
		assertEquals(Game.DEFAULT_NB_CARDS_COUPLE * 2, game.getGrid().size());

		List<Integer> positions = Lists.newArrayList(0, Game.DEFAULT_NB_CARDS_COUPLE * 2 - 1);
		Turn turn = game.play(positions);
		assertTrue(turn.getTurnScore() >= 0); // not outside the grid

		Game game2 = new Game(5);
		assertEquals(5 * 2, game2.getGrid().size());

		Turn turn2 = game2.play(positions);
		assertTrue(turn2.getTurnScore() < 0); // outside the grid
	}

	private List<Integer> findSomeUndiscoveredCardsCouple(Game game) {
		List<Card> grid = game.getGrid();
		Card firstCard = null;
		Integer firstCardPosition = null;
		Integer secondCardPosition = null;

		for (Card card : grid) {
			int position = grid.indexOf(card);
			if (firstCardPosition == null && !card.isFound()) {
				firstCard = card;
				firstCardPosition = position;
			}
			if (firstCardPosition != null && position != firstCardPosition && card.sameAs(firstCard)) {
				secondCardPosition = position;
				break;
			}
		}

		return Lists.newArrayList(firstCardPosition, secondCardPosition);
	}

	private Integer findSomeNotFoundCard(Game game) {
		List<Card> grid = game.getGrid();

		for (Card card : grid) {
			if (!card.isFound()) {
				return grid.indexOf(card);
			}
		}
		return null;
	}
}
