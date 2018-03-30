package us.dontcareabout.PickRed.client.data;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.SimpleEventBus;

import us.dontcareabout.PickRed.client.RpcService;
import us.dontcareabout.PickRed.client.RpcServiceAsync;

public class DataCenter {
	private final static SimpleEventBus eventBus = new SimpleEventBus();
	private final static RpcServiceAsync rpc = GWT.create(RpcService.class);
}
