package us.dontcareabout.PickRed.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

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

	public void create(String masterId) {
		Table table = new Table(UUID.randomUUID().toString(), masterId);
		tables.put(table.getId(), table);

		try {
			wsServer.broadcast(WsMsg.refreshTable);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Table> getTables() {
		return new ArrayList<>(tables.values());
	}
}
