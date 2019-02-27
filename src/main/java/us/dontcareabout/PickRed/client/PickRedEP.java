package us.dontcareabout.PickRed.client;

import com.google.gwt.user.client.Window;

import us.dontcareabout.PickRed.client.data.DataCenter;
import us.dontcareabout.PickRed.client.data.MyPlayerReadyEvent;
import us.dontcareabout.PickRed.client.data.MyPlayerReadyEvent.MyPlayerReadyHandler;
import us.dontcareabout.PickRed.client.ui.UiCenter;
import us.dontcareabout.gwt.client.GFEP;
import us.dontcareabout.gwt.client.iCanUse.Feature;

public class PickRedEP extends GFEP {
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
		DataCenter.addMyPlayerReady(new MyPlayerReadyHandler() {
			@Override
			public void onMyPlayerReady(MyPlayerReadyEvent event) {
				UiCenter.start();
			}
		});
		DataCenter.wantMyPlayer();
	}
}
