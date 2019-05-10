package us.dontcareabout.PickRed.server;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;

import org.springframework.web.context.support.WebApplicationContextUtils;

import us.dontcareabout.PickRed.client.RpcService;
import us.dontcareabout.PickRed.shared.Player;
import us.dontcareabout.PickRed.shared.Table;
import us.dontcareabout.gwt.server.GFServiceServlet;
import us.dontcareabout.gwt.server.WebSocketServer;

public class RpcServiceImpl extends GFServiceServlet implements RpcService {
	private static final long serialVersionUID = 1L;

	private WebSocketServer wsServer;
	private TableCenter tableCenter;

	@Override
	public void init() throws ServletException {
		super.init();
		wsServer = WebApplicationContextUtils
			.getWebApplicationContext(this.getServletContext())
			.getBean(WebSocketServer.class);
		tableCenter = new TableCenter(wsServer);
	}

	@Override
	public Player getMyPlayer() {
		return Faker.getPlayer(getSessionId());
	}

	@Override
	public ArrayList<Table> getTables() {
		return tableCenter.getTables();
	}

	@Override
	public Table createTable() {
		return tableCenter.create(getMyPlayer());
	}

	@Override
	public boolean joinTable(String tableId) {
		return tableCenter.join(tableId, getMyPlayer());
	}

	@Override
	public void startGame(String tableId) {
		tableCenter.startGame(tableId);
	}

	@Override
	public void broadcast(String message) {
		try {
			wsServer.broadcast(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
