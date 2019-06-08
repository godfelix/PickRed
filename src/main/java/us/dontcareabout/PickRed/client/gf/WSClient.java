package us.dontcareabout.PickRed.client.gf;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window.Location;

import us.dontcareabout.gwt.client.websocket.WebSocket;
import us.dontcareabout.gwt.client.websocket.event.MessageEvent;
import us.dontcareabout.gwt.client.websocket.event.MessageHandler;

/**
 * WSClient 在接收到訊息後，
 * 會依 {@link #addProcessor(WSProcessor)} 所註記的 {@link WSProcessor}
 * 來決定要如何處理該次訊息。
 */
public class WSClient {
	private WebSocket ws;
	private ArrayList<WSProcessor> processorList = new ArrayList<>();

	public WSClient(String uri) {
		ws = new WebSocket(
			GWT.getHostPageBaseURL().replace(Location.getProtocol(), "ws:") + uri
		);

		ws.addMessageHandler(new MessageHandler() {
			@Override
			public void onMessage(MessageEvent e) {
				process(e.getMessage());
			}
		});
		ws.open();
	}

	public void addProcessor(WSProcessor processor) {
		processorList.add(processor);
	}

	private void process(String message) {
		for (WSProcessor p : processorList) {
			if (message.startsWith(p.header)) {
				p.process(message.substring(p.header.length()));
				//目前不考慮一個 header 要觸發多個 processor 的狀況
				//這樣效率上也比較好
				return;
			}
		}
	}
}
