package us.dontcareabout.PickRed.server;

import java.util.HashMap;

import us.dontcareabout.PickRed.shared.Player;

//反正是 POC、而且都叫 Faker 了，所以一切都可以亂來 \囧/
public class Faker {
	private static int playerCount = 0;
	private static HashMap<String, Player> players = new HashMap<>();

	static Player getPlayer(String sessionId) {
		Player result = players.get(sessionId);

		if (result == null) {
			result = new Player(sessionId, "Player-" + playerCount++);
			players.put(sessionId, result);
		}

		return result;
	}
}
