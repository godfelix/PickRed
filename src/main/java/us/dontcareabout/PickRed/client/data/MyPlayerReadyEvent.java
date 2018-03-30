package us.dontcareabout.PickRed.client.data;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

import us.dontcareabout.PickRed.client.data.MyPlayerReadyEvent.MyPlayerReadyHandler;

public class MyPlayerReadyEvent extends GwtEvent<MyPlayerReadyHandler> {
	public static final Type<MyPlayerReadyHandler> TYPE = new Type<MyPlayerReadyHandler>();

	@Override
	public Type<MyPlayerReadyHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(MyPlayerReadyHandler handler) {
		handler.onMyPlayerReady(this);
	}

	public interface MyPlayerReadyHandler extends EventHandler{
		public void onMyPlayerReady(MyPlayerReadyEvent event);
	}
}
