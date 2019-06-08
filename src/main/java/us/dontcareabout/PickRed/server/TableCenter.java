package us.dontcareabout.PickRed.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import us.dontcareabout.PickRed.shared.Player;
import us.dontcareabout.PickRed.shared.Table;
import us.dontcareabout.PickRed.shared.WsMsg;
import us.dontcareabout.PickRed.shared.gf.SyncData;
import us.dontcareabout.PickRed.shared.gf.SyncType;
import us.dontcareabout.gwt.server.WebSocketServer;

public class TableCenter {
	//Refactory 應該改成用 Spring 注入，這樣就不會傳 WebSockerServer 進來？
	private final WebSocketServer wsServer;
	private final HashMap<String, Table> tables = new HashMap<>();
	private final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX").create();

	public TableCenter(WebSocketServer wsServer) {
		this.wsServer = wsServer;
	}

	public Table create(Player master) {
		Table table = new Table(UUID.randomUUID().toString(), master, 2);//FIXME 改為自行指定
		tables.put(table.getId(), table);

		SyncData<Table> dp = new SyncData<>();
		dp.setType(SyncType.ADD);
		dp.addData(table);

		try {
			wsServer.broadcast(WsMsg.TABLE + gson.toJson(dp));
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

		SyncData<Table> dp = new SyncData<>();
		dp.setType(SyncType.UPDATE);
		dp.addData(table);

		try {
			wsServer.broadcast(WsMsg.TABLE + gson.toJson(dp));
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
			wsServer.multicast(sessionList, message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
