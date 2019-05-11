package us.dontcareabout.PickRed.server.gf;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//Refactory GF
/**
 * 處理 session 的社團參與狀況 XD。
 */
public class SessionClub {
	private HashMap<String, Set<String>> clubMap = new HashMap<>();
	private HashMap<String, Set<String>> sessionMap = new HashMap<>();

	public void join(String club, String session) {
		sessionSet(club).add(session);
		clubSet(session).add(club);
	}

	public void join(String club, List<String> sessionList) {
		sessionSet(club).addAll(sessionList);

		for (String session : sessionList) {
			clubSet(session).add(club);
		}
	}

	public void leave(String club, String session) {
		sessionSet(club).remove(session);
		clubSet(session).remove(club);
	}

	public void leave(String club, List<String> sessionList) {
		sessionSet(club).removeAll(sessionList);

		for (String session : sessionList) {
			clubSet(session).remove(club);
		}
	}

	public void close(String club) {
		clubMap.remove(club);
	}

	public Set<String> sessionSet(String id) {
		Set<String> result = clubMap.get(id);

		if (result == null) {
			result = new HashSet<>();
			clubMap.put(id, result);
		}

		return result;
	}

	public Set<String> clubSet(String session) {
		Set<String> result = sessionMap.get(session);

		if (result == null) {
			result = new HashSet<>();
			sessionMap.put(session, result);
		}

		return result;
	}
}
