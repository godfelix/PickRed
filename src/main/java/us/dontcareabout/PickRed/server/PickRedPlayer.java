package us.dontcareabout.PickRed.server;

import us.dontcareabout.PickRed.shared.Card;
import us.dontcareabout.PickRed.shared.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PickRedPlayer extends Player {
	private ArrayList<Card> handCards = new ArrayList<>();
	private ArrayList<Card> pickedCards = new ArrayList<>();

	public PickRedPlayer(Player player) {
		super(player.id, player.name);
	}

	public void recieveCard(Card card) {
		handCards.add(card);
	}

	public void playCard(Card card) {
		handCards.remove(card);
	}

	public void pickCard(Card card1, Card card2) {
		pickedCards.add(card1);
		pickedCards.add(card2);
	}

	public List<Card> getHandCards() {
		return Collections.unmodifiableList(handCards);
	}

	public List<Card> getPickedCards() {
		return Collections.unmodifiableList(pickedCards);
	}
}

