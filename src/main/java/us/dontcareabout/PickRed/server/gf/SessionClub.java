package us.dontcareabout.PickRed.server.gf;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

//Refactory GF
/**
 * 處理 session 的社團參與狀況 XD。
 */
@WebListener
public class SessionClub implements HttpSessionListener {
	public static final String ATTRIBUTE_NAME = "GF:SessionClub";

	/** 是否已經塞進 {@link ServletContext} 的 flag */
	private boolean injectContext;
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
		for (String session : sessionSet(club)) {
			clubSet(session).remove(club);
		}

		clubMap.remove(club);
	}

	public void dead(String session) {
		for (String club : clubSet(session)) {
			sessionSet(club).remove(session);
		}

		sessionMap.remove(session);
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

	@Override
	public void sessionCreated(HttpSessionEvent event) {
		//TODO 改用 Spring 解決？
		//由於 instance 是由 JSP Container 建立的
		//所以必須塞進 ServletContext 當中，這樣其他地方才能拿來用
		if (injectContext) { return; }

		event.getSession().getServletContext().setAttribute(ATTRIBUTE_NAME, this);
		injectContext = true;
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		dead(event.getSession().getId());
	}
}
