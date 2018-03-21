package us.dontcareabout.PickRed.client;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.Viewport;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;

import us.dontcareabout.gwt.client.Console;
import us.dontcareabout.gwt.client.GFEP;
import us.dontcareabout.gwt.client.iCanUse.Feature;

public class PickRedEP extends GFEP {
	private static final RpcServiceAsync rpc = GWT.create(RpcService.class);

	public PickRedEP() {
		needFeature(Feature.Canvas);
		needFeature(Feature.WebSocket);
		WSClient.init();
	}

	@Override
	protected String version() { return "0.0.1"; }

	@Override
	protected String defaultLocale() { return "zh_TW"; }

	@Override
	protected void featureFail() {
		Window.alert("這個瀏覽器我不尬意，不給用..... \\囧/");
	}

	@Override
	protected void start() {
		TextButton btn = new TextButton("yoyoyo");

		btn.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				rpc.broadcast("yoyoyo", new AsyncCallback<Void>() {
					@Override
					public void onSuccess(Void result) {
						Console.log("boradcast finish");
					}

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
					}
				});
			}
		});

		Viewport vp = new Viewport();
		vp.add(btn);
		RootPanel.get().add(vp);
	}
}
