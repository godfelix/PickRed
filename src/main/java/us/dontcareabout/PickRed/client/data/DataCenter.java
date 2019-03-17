package us.dontcareabout.PickRed.client.data;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import us.dontcareabout.PickRed.client.RpcService;
import us.dontcareabout.PickRed.client.RpcServiceAsync;
import us.dontcareabout.PickRed.client.data.MyPlayerReadyEvent.MyPlayerReadyHandler;
import us.dontcareabout.PickRed.client.data.TableDataReadyEvent.TableDataReadyHandler;
import us.dontcareabout.PickRed.client.ui.UiCenter;
import us.dontcareabout.PickRed.shared.Player;
import us.dontcareabout.PickRed.shared.Table;

public class DataCenter {
	private final static SimpleEventBus eventBus = new SimpleEventBus();
	private final static RpcServiceAsync rpc = GWT.create(RpcService.class);

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
			public void onSuccess(Table result) {}

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
	// ========= //
}
