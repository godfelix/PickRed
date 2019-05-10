package us.dontcareabout.PickRed.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import us.dontcareabout.PickRed.shared.Player;
import us.dontcareabout.PickRed.shared.Table;
import us.dontcareabout.PickRed.shared.WsMsg;
import us.dontcareabout.gwt.server.WebSocketServer;

/* TODO 精簡傳輸方式
 * 現在是只透過 websocket 炸 notify，client 透過 RPC 重新
 * 應該改成 client 第一次重撈全部
 * 之後是開桌狀況有變化時，直接透過 websocket 把資料打給 client
 */
public class TableCenter {
	//Refactory 應該改成用 Spring 注入，這樣就不會傳 WebSockerServer 進來？
	private final WebSocketServer wsServer;
	private final HashMap<String, Table> tables = new HashMap<>();

	public TableCenter(WebSocketServer wsServer) {
		this.wsServer = wsServer;
	}

	public Table create(Player master) {
		Table table = new Table(UUID.randomUUID().toString(), master, 2);//FIXME 改為自行指定
		tables.put(table.getId(), table);

		try {
			wsServer.broadcast(WsMsg.refreshTable);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return table;
	}

	//TODO 處理不同失敗狀況
	public boolean join(String tableId, Player player) {
		Table table = tables.get(tableId);

		if (table == null) { return false; }
		if (table.isFull()) { return false; }

		table.join(player);

		try {
			wsServer.broadcast(WsMsg.refreshTable);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return true;
	}

	public ArrayList<Table> getTables() {
		return new ArrayList<>(tables.values());
	}

	public void startGame(String tableId) {
		tablecast(tableId, "Game Start");//FIXME
	}

	/**
	 * 對某一 {@link Table} 下的玩家作 {@link WebSocketServer#multicast(java.util.List, String)}
	 */
	private void tablecast(String tableId, String message) {
		Table table = tables.get(tableId);
		ArrayList<String> sessionList = new ArrayList<>();

		for (Player p : table.getPlayerList()) {
			sessionList.add(p.id);
		}

		try {
			wsServer.multicast(sessionList, "Game Start");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
