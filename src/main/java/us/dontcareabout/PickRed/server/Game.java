package us.dontcareabout.PickRed.server;

import us.dontcareabout.PickRed.shared.Card;
import us.dontcareabout.PickRed.shared.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Game {
	private ArrayList<Player> players = new ArrayList<>();
	private Card[] cards = Card.genDeck();
	private int cardIdx = 0;
	private ArrayList<Card> cardsOnDesk = new ArrayList<>();

	private Map<Player, ArrayList<Card>> handCard = new HashMap<>();
	private Map<Player, ArrayList<Card>> cardsPicked = new HashMap<>();

	public Game(ArrayList<Player> players) {
		this.players = players;
		for(Card card:cards) {
			System.out.print(card+", ");
		}
		System.out.println();

		for(int i=0;i<52;i++) {
			System.out.print(drawCard()+", ");
		}
	}

	private Card drawCard() {
		cardIdx +=1;
		return cards[cardIdx -1];
	}

	private void dealCard() {}

	/**
	 * 玩家撿牌。
	 */
	private void pick(Player player, Card card1, Card card2) {}

	private void score() {}
}
