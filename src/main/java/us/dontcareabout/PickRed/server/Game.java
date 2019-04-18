package us.dontcareabout.PickRed.server;

import us.dontcareabout.PickRed.shared.Card;
import us.dontcareabout.PickRed.shared.GMT;
import us.dontcareabout.PickRed.shared.Player;
import us.dontcareabout.PickRed.shared.Suit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Game {
	public ArrayList<Player> players;
	private Card[] cards = Card.genDeck();
	private int cardIdx = 0;
	public ArrayList<Card> cardsOnDesk = new ArrayList<>();

	public Map<Player, ArrayList<Card>> handCard = new HashMap<>();
	public Map<Player, ArrayList<Card>> cardsPicked = new HashMap<>();

	public Game(ArrayList<Player> players) {
		this.players = players;
		GMT.shuffling(cards);
		dealCard();
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

	private void dealCard() {
		Card[][] tempCards = new Card[4][6];

		for (int j = 0; j < 6; j++) {
			for (int i = 0; i < 4; i++) {
				tempCards[i][j] = drawCard();
			}
		}

		for (int idx = 0; idx < 4; idx++) {
			cardsOnDesk.add(drawCard());
			handCard.put(players.get(idx), new ArrayList<>(Arrays.asList(tempCards[idx])));
			cardsPicked.put(players.get(idx), new ArrayList<Card>());
		}
	}

	/**
	 * 玩家撿牌。
	 */
	private void pick(Player player, Card card1, Card card2) {
		cardsPicked.get(player).add(card1);
		cardsPicked.get(player).add(card2);
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
	 * 計算玩家手中的牌，為 0 時表示遊戲結束。
	 *
	 * @return true 遊戲結束
	 */
	public boolean isEnd() {
		int number = 0;
		for (Player player : players) {
			number += handCard.get(player).size();
		}
		return number == 0;
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
}
