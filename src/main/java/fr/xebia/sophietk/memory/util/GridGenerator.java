package fr.xebia.sophietk.memory.util;


import com.google.common.collect.Lists;
import fr.xebia.sophietk.memory.service.Card;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GridGenerator {

	private static final List<String> CARDS_SYMBOLS = Arrays.asList("balloon", "dog", "coat", "boat", "umbrella");
	private static final List<String> CARDS_COLORS = Arrays.asList("red", "blue", "yellow", "green");

	private static List<Card> newAllCards() {
		List<Card> allPossibleCards = new ArrayList<Card>();
		for (String symbol : CARDS_SYMBOLS) {
			for (String color : CARDS_COLORS) {
				allPossibleCards.add(new Card(symbol, color));
			}
		}
		return allPossibleCards;
	}

	public static List<Card> generate(int cardsCouple) {
		List<Card> cards = newAllCards();
		Collections.shuffle(cards);
		cards = cards.subList(0, cardsCouple);

		// Duplicate cards (references AND objects)
		List<Card> cards2 = Lists.newArrayList();
		for (Card card : cards) cards2.add(card.clone());
		cards.addAll(cards2);

		Collections.shuffle(cards);

		return cards;
	}
}
