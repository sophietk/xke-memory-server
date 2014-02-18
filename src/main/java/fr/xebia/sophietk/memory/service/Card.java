package fr.xebia.sophietk.memory.service;

import com.google.common.base.Objects;

public class Card implements Cloneable {

	private String symbol = "nothing";
	private String color = "white";
	private boolean found = false;

	public Card() {
	}

	public Card(String symbol, String color) {
		this.symbol = symbol;
		this.color = color;
	}

	public String getSymbol() {
		return symbol;
	}

	public String getColor() {
		return color;
	}

	public boolean isFound() {
		return found;
	}

	public void switchFound() {
		this.found = true;
	}

	public boolean sameAs(Card otherCard) {
		return Objects.equal(this.symbol, otherCard.symbol) && Objects.equal(this.color, otherCard.color);
	}

	@Override
	public String toString() {
		return color + " " + symbol;
	}

	@Override
	public Card clone() {
		return new Card(this.symbol, this.color);
	}
}
