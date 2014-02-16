package fr.xebia.sophietk.memory;

import com.google.common.annotations.VisibleForTesting;

public class CardPosition {

	private int x;
	private int y;

	@VisibleForTesting
	protected CardPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof CardPosition)) return false;

		CardPosition other = (CardPosition) o;
		return x == other.x && y == other.y;
	}
}
