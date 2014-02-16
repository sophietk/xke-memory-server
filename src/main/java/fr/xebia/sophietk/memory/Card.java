package fr.xebia.sophietk.memory;

public class Card {

	private String symbol = "nothing";
	private String color = "white";

	public Card(String symbol, String color) {
		this.symbol = symbol;
		this.color = color;
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
