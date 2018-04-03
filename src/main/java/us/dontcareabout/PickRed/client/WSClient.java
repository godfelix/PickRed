package us.dontcareabout.PickRed.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window.Location;

import us.dontcareabout.PickRed.client.data.DataCenter;
import us.dontcareabout.PickRed.shared.WsMsg;
import us.dontcareabout.gwt.client.Console;
import us.dontcareabout.gwt.client.websocket.WebSocket;
import us.dontcareabout.gwt.client.websocket.event.MessageEvent;
import us.dontcareabout.gwt.client.websocket.event.MessageHandler;

public class WSClient {
	private static WebSocket ws;

	public static void init() {
		if (ws != null) { return; }

		ws = new WebSocket(
			GWT.getHostPageBaseURL().replace(Location.getProtocol(), "ws:") +
			"spring/websocket"
		);
		ws.addMessageHandler(new MessageHandler() {
			@Override
			public void onMessage(MessageEvent e) {
				process(e.getMessage());
			}
		});
		ws.open();
	}

	private static void process(String message) {
		Console.log(message);	//Delete
		if (WsMsg.refreshTable.equals(message)) {
			DataCenter.wantTables();
			return;
		}
	}
}
