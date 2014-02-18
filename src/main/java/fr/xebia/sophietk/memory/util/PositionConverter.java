package fr.xebia.sophietk.memory.util;

import com.google.common.collect.Lists;
import fr.xebia.sophietk.memory.service.CardPosition;

import java.util.List;

public class PositionConverter {

	public static List<CardPosition> toCardPosition(List<List<Integer>> positions) {
		List<CardPosition> cardPositions = Lists.newArrayList();

		for (List<Integer> position : positions) {
			cardPositions.add(new CardPosition(position.get(0), position.get(1)));
		}

		return cardPositions;
	}
}
