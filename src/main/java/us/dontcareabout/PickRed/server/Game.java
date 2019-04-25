package us.dontcareabout.PickRed.server;

import us.dontcareabout.PickRed.shared.Card;
import us.dontcareabout.PickRed.shared.GMT;
import us.dontcareabout.PickRed.shared.Player;
import us.dontcareabout.PickRed.shared.Suit;

import java.util.ArrayList;


public class Game {
	private ArrayList<FooPlayer> players = new ArrayList<>();
	private Card[] cards = Card.genDeck();
	private int cardIdx = 0;
	private int playerNumber;
	private int roundLeft = 24;
	private ArrayList<Card> cardsOnDesk = new ArrayList<>();


	public Game(ArrayList<Player> players) {
		playerNumber = players.size();

		for (Player player : players) {
			this.players.add(new FooPlayer(player));
		}

		GMT.shuffling(cards);
		Card[][] dealtCards = dealCard();

		for (int idx = 0; idx < playerNumber; idx++) {
			for (Card card : dealtCards[idx]) {
				this.players.get(idx).recieveCard(card);
			}
		}

		cardsOnDesk.add(drawCard());
		cardsOnDesk.add(drawCard());
		cardsOnDesk.add(drawCard());
		cardsOnDesk.add(drawCard());
	}

	/**
	 * 玩家出排或翻牌，若能與桌牌能湊成 10，則撿牌。
	 * 撿分數最高的牌。
	 */
	public void playCard(Player player, Card card) {
		ArrayList<Card> sortedCardsOnDesk = new ArrayList(cardsOnDesk);
		sortCardsByScore(sortedCardsOnDesk);

		for (Card cardDesk : sortedCardsOnDesk) {
			if (card.number < 10 && card.number + cardDesk.number != 10) {
				continue;
			}
			if (card.number >= 10 && card.number != cardDesk.number) {
				continue;
			}
			cardsOnDesk.remove(cardDesk);
			pick(player, card, cardDesk);
			return;
		}
		cardsOnDesk.add(card);
	}

	public Card drawCard() {
		cardIdx += 1;
		return cards[cardIdx - 1];
	}

	private Card[][] dealCard() {
		int handCardNumber = roundLeft / playerNumber;
		Card[][] cards = new Card[playerNumber][handCardNumber];

		for (int j = 0; j < handCardNumber; j++) {
			for (int i = 0; i < playerNumber; i++) {
				cards[i][j] = drawCard();
			}
		}

		return cards;
	}

	/**
	 * 玩家撿牌。
	 */
	private void pick(Player player, Card card1, Card card2) {
		FooPlayer fooPlayer = players.get(players.indexOf(player));
		fooPlayer.pickCard(card1);
		fooPlayer.pickCard(card2);
	}

	/**
	 * @return 一張牌的分數
	 */
	public int score(Card card) {
		if (card.number == 1) {
			switch (card.suit) {
				case club:
					return 40;
				case spade:
					return 30;
				case heart:
					return 20;
				case diamond:
					return 20;
			}
		} else if (card.suit == Suit.heart || card.suit == Suit.diamond) {
			if (card.number < 10) {
				return card.number;
			} else {
				return 10;
			}
		}

		return 0;
	}

	/**
	 * 玩家手中沒皆沒牌時結束遊戲。
	 *
	 * @return true 遊戲結束
	 */
	public boolean isEnd() {
		roundLeft -= 1;
		return roundLeft + 1 == 0;
	}

	/**
	 * 依照牌的分數由大到小排序。
	 */
	private void sortCardsByScore(ArrayList<Card> cards) {
		int size = cards.size();

		for (int i = size - 1; i > 0; i--) {
			for (int j = 0; j < i; j++) {
				if (score(cards.get(j)) < score(cards.get(j + 1))) {
					Card temp = cards.get(j);
					cards.set(j, cards.get(j + 1));
					cards.set(j + 1, temp);
				}
			}
		}
	}

	public ArrayList<FooPlayer> getPlayers() {
		return players;
	}

	public ArrayList<Card> getCardsOnDesk() {
		return cardsOnDesk;
	}
}
