package fr.xebia.sophietk.memory;

public class Card {

	private String symbol = "nothing";
	private String color = "white";
	private boolean found = false;

	public Card(String symbol, String color) {
		this.symbol = symbol;
		this.color = color;
	}

	public boolean isFound() {
		return found;
	}

	public void switchFound() {
		this.found = true;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Card)) return false;

		Card other = (Card) o;
		return this.symbol.equals(other.symbol) && this.color.equals(other.color);
	}

	@Override
	public String toString() {
		return color + " " + symbol;
	}
}
