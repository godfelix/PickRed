package us.dontcareabout.PickRed.shared;

import java.io.Serializable;

public class Card implements Serializable {
	private static final long serialVersionUID = 1L;

	public final Suit suit;
	public final int number;

	public Card(Suit suit, int number) {
		this.suit = suit;
		this.number = number;
	}

	public static Card[] genDeck() {
		Card[] result = new Card[52];
		int count = 0;

		for (Suit suit : Suit.values()) {
			if (suit == Suit.joker) { continue; }

			for (int i = 1; i < 14; i++) {
				result[count] = new Card(suit, i);
				count++;
			}
		}

		return result;
	}

	@Override
	public String toString() {
		return suit + "[" + number + "]";
	}
}
