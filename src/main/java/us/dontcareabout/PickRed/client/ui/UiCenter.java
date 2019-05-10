package us.dontcareabout.PickRed.client.ui;

import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.container.Viewport;

import us.dontcareabout.PickRed.shared.Table;

public class UiCenter {
	private final static Viewport viewport = new Viewport();

	private static FloorView mainView;
	private static TableView tableView;

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

	public static void tableView(Table table) {
		if (tableView == null) {
			tableView = new TableView();
		}

		tableView.setData(table);
		switchTo(tableView);
	}

	private static void switchTo(Widget widget) {
		viewport.clear();
		viewport.add(widget);
		viewport.forceLayout();
	}
}
