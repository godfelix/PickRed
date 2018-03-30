package us.dontcareabout.PickRed.client.data;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;

import us.dontcareabout.PickRed.client.RpcService;
import us.dontcareabout.PickRed.client.RpcServiceAsync;
import us.dontcareabout.PickRed.client.data.MyPlayerReadyEvent.MyPlayerReadyHandler;
import us.dontcareabout.PickRed.shared.Player;

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
	//
}
