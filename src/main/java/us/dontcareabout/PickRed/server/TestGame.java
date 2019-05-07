package us.dontcareabout.PickRed.server;


import us.dontcareabout.PickRed.shared.Card;
import us.dontcareabout.PickRed.shared.Player;
import us.dontcareabout.PickRed.shared.Suit;

import java.util.ArrayList;

public class TestGame {

	public static void main(String[] args) {
		testFooPlayer();
		testGamePlay();
	}

	private static void testGamePlay() {
		ArrayList<Player> players = new ArrayList<>();

		players.add(new Player("1", "1"));
		players.add(new Player("2", "2"));
		players.add(new Player("3", "3"));
		players.add(new Player("4", "4"));

		int playerNumber = players.size();

		Game game = new Game(players);

		int turn = 0;

		while (turn < 24) {
			int playerIdx = turn % playerNumber;
			FooPlayer player = game.getPlayers().get(playerIdx);

			System.out.println("\nCards on desk:\n" + game.getCardsOnDesk());

			for (FooPlayer eachPlayer : game.getPlayers()) {
				System.out.println(eachPlayer.name + ", " + eachPlayer.getHandCards());
			}

			Card card = player.playCard(player.getHandCards().get(0));
			System.out.println(player.name + " plays card: " + card);

			game.playCard(player, card);
			card = game.drawCard();
			System.out.println("Drew card: " + card);
			game.playCard(player, card);

			turn += 1;
		}

		System.out.println("\nPicked cards:");
		for (FooPlayer eachPlayer : game.getPlayers()) {
			System.out.println(eachPlayer.name + ", " + eachPlayer.getPickedCards());
		}
	}

	private static void testFooPlayer() {
		Player player1 = new Player("1", "A");
		FooPlayer fooPlayer1 = new FooPlayer(player1);

		Card card1 = new Card(Suit.diamond, 1);
		Card card2 = new Card(Suit.heart, 13);
		Card card3 = new Card(Suit.spade, 5);
		fooPlayer1.recieveCard(card1);
		fooPlayer1.recieveCard(card2);
		fooPlayer1.recieveCard(card3);

		// 測試 手牌
		test(fooPlayer1.getHandCards(), "[diamond[1], heart[13], spade[5]]");


		Card playCard1 = fooPlayer1.playCard(card1);
		Card playCard2 = fooPlayer1.playCard(card2);

		// 測試 出牌
		test(playCard1, "diamond[1]");
		test(playCard2, "heart[13]");
		test(fooPlayer1.getHandCards(), "[spade[5]]");

		// 測試 撿牌
		fooPlayer1.pickCard(card1);
		fooPlayer1.pickCard(card2);
		test(fooPlayer1.getPickedCards(), "[diamond[1], heart[13]]");
	}

	private static void test(Object test, Object exp) {
		if (!(test.toString().equals(exp.toString()))) {
			System.out.println("failed! " + "Expect: " + exp.toString() +
					",\n but get: " + test.toString());
		} else {
			System.out.println("Pass!");
		}
	}
}
