package us.dontcareabout.PickRed.client.ui;

import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.container.Viewport;

public class UiCenter {
	private final static Viewport viewport = new Viewport();

	private static FloorView mainView;


	public static void start() {
		RootPanel.get().add(viewport);
		mainView();
	}

	public static void mainView() {
		if (mainView == null) {
			mainView = new FloorView();
		}

		switchTo(mainView);
	}

	private static void switchTo(Widget widget) {
		viewport.clear();
		viewport.add(widget);
		viewport.forceLayout();
	}
}
