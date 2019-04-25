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
	}

	public void playCard(Card card) {
	}

	public void pickCard(Card card) {
	}

	public List<Card> getHandCards() {
		return Collections.unmodifiableList(handCards);
	}

	public List<Card> getPickedCards() {
		return Collections.unmodifiableList(pickedCards);
	}
}

