package us.dontcareabout.PickRed.client.data;

import java.util.ArrayList;

import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import us.dontcareabout.PickRed.client.RpcService;
import us.dontcareabout.PickRed.client.RpcServiceAsync;
import us.dontcareabout.PickRed.client.data.MyPlayerReadyEvent.MyPlayerReadyHandler;
import us.dontcareabout.PickRed.client.data.TableDataReadyEvent.TableDataReadyHandler;
import us.dontcareabout.PickRed.client.gf.WSClient;
import us.dontcareabout.PickRed.client.gf.WSProcessor;
import us.dontcareabout.PickRed.client.ui.UiCenter;
import us.dontcareabout.PickRed.shared.Player;
import us.dontcareabout.PickRed.shared.Table;
import us.dontcareabout.PickRed.shared.WsMsg;
import us.dontcareabout.PickRed.shared.gf.SyncData;

public class DataCenter {
	private final static SimpleEventBus eventBus = new SimpleEventBus();
	private final static RpcServiceAsync rpc = GWT.create(RpcService.class);
	private final static WSClient wsClient = new WSClient("spring/websocket");
	static {
		wsClient.addProcessor(new TableProcessor());
	}

	// ==== myPlayer ==== //
	private static Player myPlayer;

	public static void wantMyPlayer() {
		rpc.getMyPlayer(new AsyncCallback<Player>() {
			@Override
			public void onSuccess(Player result) {
				myPlayer = result;
				eventBus.fireEvent(new MyPlayerReadyEvent());
			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
			}
		});
	}

	public static HandlerRegistration addMyPlayerReady(MyPlayerReadyHandler handler) {
		return eventBus.addHandler(MyPlayerReadyEvent.TYPE, handler);
	}

	public static Player getMyPlayer() {
		return myPlayer;
	}
	// ======== //

	// ==== Table ==== //
	public static void createTable() {
		rpc.createTable(new AsyncCallback<Table>() {
			@Override
			public void onSuccess(Table result) {
				UiCenter.tableView(result);
			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
			}
		});
	}

	private static ArrayList<Table> tableList;

	public static ArrayList<Table> getTableList() {
		return tableList;
	}

	public static Table findTable(String id) {
		for (Table table : tableList) {
			if (table.getId().equals(id)) {
				return table;
			}
		}
		return null;
	}

	public static void wantTables() {
		rpc.getTables(new AsyncCallback<ArrayList<Table>>() {
			@Override
			public void onSuccess(ArrayList<Table> result) {
				tableList = result;
				eventBus.fireEvent(new TableDataReadyEvent());
			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
			}
		});
	}

	public static HandlerRegistration addTableDataReady(TableDataReadyHandler handler) {
		return eventBus.addHandler(TableDataReadyEvent.TYPE, handler);
	}

	public static void joinTable(final Table table) {
		rpc.joinTable(table.getId(), new AsyncCallback<Boolean>() {
			@Override
			public void onSuccess(Boolean result) {
				if (result) {
					UiCenter.tableView(table);
				} else {
					Window.alert("加入失敗");
				}
			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}
		});
	}

	public static void startGame(Table table) {
		rpc.startGame(table.getId(), new AsyncCallback<Void>() {
			@Override
			public void onSuccess(Void result) {
				//後續是統一接受 ws 訊息，所以這裡想不到要作啥事情...
			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}
		});
	}
	// ========= //

	static class TableProcessor extends WSProcessor {
		interface Mapper extends ObjectMapper<SyncData<Table>> {}
		private Mapper mapper = GWT.create(Mapper.class);

		public TableProcessor() {
			super(WsMsg.TABLE);
		}

		@Override
		public void process(String data) {
			SyncData<Table> sd = mapper.read(data);

			//目前看起來，如果只是要處理 list 的話
			//一律都先 remove 掉然後 ADD / UPDATE 再 add 回去就好了？
			//ADD 也這樣搞是擔心 WS 給的資料是 RPC 裡頭已經有的（雖然發生機率應該不大）
			tableList.removeAll(sd.getData());

			switch(sd.getType()) {
			case ADD:
			case UPDATE:
				tableList.addAll(sd.getData());
			default: //純粹為了消除 IDE warning....
			}

			eventBus.fireEvent(new TableDataReadyEvent());
		}
	}
}
