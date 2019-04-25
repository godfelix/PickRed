package us.dontcareabout.PickRed.server;

import us.dontcareabout.PickRed.shared.Card;
import us.dontcareabout.PickRed.shared.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FooPlayer extends Player {
	private ArrayList<Card> handCards = new ArrayList<>();
	private ArrayList<Card> pickedCards = new ArrayList<>();

	public FooPlayer(Player player) {
		super(player.id, player.name);
	}

	public void recieveCard(Card card) {
		handCards.add(card);
	}

	public Card playCard(Card card) {
		handCards.remove(card);
		return card;
	}

	public void pickCard(Card card) {
		pickedCards.add(card);
	}

	public List<Card> getHandCards() {
		return Collections.unmodifiableList(handCards);
	}

	public List<Card> getPickedCards() {
		return Collections.unmodifiableList(pickedCards);
	}
}

