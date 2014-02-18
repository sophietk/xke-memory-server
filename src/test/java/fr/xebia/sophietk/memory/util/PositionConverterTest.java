package fr.xebia.sophietk.memory.util;

import com.google.common.collect.Lists;
import fr.xebia.sophietk.memory.service.CardPosition;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class PositionConverterTest {

	@Test
	public void should_convert_positions() {
		List<Integer> position1 = Lists.newArrayList(3, 2);
		List<Integer> position2 = Lists.newArrayList(18, 1);

		List<CardPosition> positions = PositionConverter.toCardPosition(Lists.newArrayList(position1, position2));

		assertEquals(2, positions.size());
		assertEquals(3, positions.get(0).getX());
		assertEquals(2, positions.get(0).getY());
		assertEquals(18, positions.get(1).getX());
		assertEquals(1, positions.get(1).getY());
	}
}
