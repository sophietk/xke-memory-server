package fr.xebia.sophietk.memory.service;

public class CardPosition {

	private int x;
	private int y;

	public CardPosition() {
	}

	public CardPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof CardPosition)) return false;

		CardPosition other = (CardPosition) o;
		return x == other.x && y == other.y;
	}
}
