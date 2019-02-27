package us.dontcareabout.PickRed.client.ui;

import com.sencha.gxt.core.client.dom.ScrollSupport.ScrollMode;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;

import us.dontcareabout.PickRed.client.data.DataCenter;
import us.dontcareabout.PickRed.client.data.TableDataReadyEvent;
import us.dontcareabout.PickRed.client.data.TableDataReadyEvent.TableDataReadyHandler;
import us.dontcareabout.PickRed.client.layer.MiscLayer;
import us.dontcareabout.PickRed.client.layer.TableListLayer;

public class FloorView extends VerticalLayoutContainer {
	private TableListLayer tableList = new TableListLayer();
	private MiscLayer misc = new MiscLayer();

	public FloorView() {
		VerticalLayoutContainer tableContainer = new VerticalLayoutContainer();
		tableContainer.setScrollMode(ScrollMode.AUTOY);
		tableContainer.add(tableList, new VerticalLayoutData(1, -1));
		add(tableContainer, new VerticalLayoutData(1, 1));
		add(misc, new VerticalLayoutData(1, 60));

		DataCenter.addTableDataReady(new TableDataReadyHandler() {
			@Override
			public void onTableDataReady(TableDataReadyEvent event) {
				tableList.setData(DataCenter.getTableList());
			}
		});
		DataCenter.wantTables();
	}
}
