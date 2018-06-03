package us.dontcareabout.PickRed.client.layer;

import java.util.ArrayList;

import com.sencha.gxt.chart.client.draw.RGB;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.event.StoreAddEvent;
import com.sencha.gxt.data.shared.event.StoreClearEvent;
import com.sencha.gxt.data.shared.event.StoreDataChangeEvent;
import com.sencha.gxt.data.shared.event.StoreFilterEvent;
import com.sencha.gxt.data.shared.event.StoreHandlers;
import com.sencha.gxt.data.shared.event.StoreRecordChangeEvent;
import com.sencha.gxt.data.shared.event.StoreRemoveEvent;
import com.sencha.gxt.data.shared.event.StoreSortEvent;
import com.sencha.gxt.data.shared.event.StoreUpdateEvent;

import us.dontcareabout.PickRed.shared.Table;
import us.dontcareabout.gxt.client.draw.LSprite;
import us.dontcareabout.gxt.client.draw.LayerSprite;
import us.dontcareabout.gxt.client.draw.component.TextButton;

//TODO 還沒有辦法解決隨著 table 數量而正確改變大小的問題
public class TableListLayer extends LayerSprite {
	private final int UNIT_W = 100;
	private final int UNIT_H = 70;

	private ListStore<Table> tableList = new ListStore<>(new ModelKeyProvider<Table>() {
		@Override
		public String getKey(Table item) {
			return item.getId();
		}
	});

	public TableListLayer() {
		tableList.addStoreHandlers(new StoreHandlers<Table>() {
			@Override
			public void onAdd(StoreAddEvent<Table> event) {
				render();
			}

			@Override
			public void onRemove(StoreRemoveEvent<Table> event) {
				render();
			}

			@Override
			public void onFilter(StoreFilterEvent<Table> event) {
				render();
			}

			@Override
			public void onClear(StoreClearEvent<Table> event) {
				render();
			}

			@Override
			public void onUpdate(StoreUpdateEvent<Table> event) {
				render();
			}

			@Override
			public void onDataChange(StoreDataChangeEvent<Table> event) {
				render();
			}

			@Override
			public void onRecordChange(StoreRecordChangeEvent<Table> event) {
				render();
			}

			@Override
			public void onSort(StoreSortEvent<Table> event) {
				render();
			}
		});
	}

	public void setData(ArrayList<Table> data) {
		tableList.clear();
		tableList.addAll(data);
	}

	private void render() {
		clear();

		for (Table table : tableList.getAll()) {
			TableUnit tl = new TableUnit(table);
			add(tl);
		}

		//XXX 目前似乎只有這個排列組合（包含用 ListStore）才能正常顯示
		redeploy();
		redraw(true);
		adjustMember();
	}

	@Override
	protected void adjustMember() {
		double height = Math.ceil(tableList.size() / Math.floor(getWidth() / UNIT_W)) * UNIT_H;

		if (getHeight() != height) {
			resize(getWidth(), height);
			return;
		}

		int row = 0;
		int col = 0;

		for (LSprite ls : getMembers()) {
			if (! (ls instanceof TableUnit)) { continue; }

			TableUnit tl = (TableUnit) ls;
			tl.setLX(col * UNIT_W);
			tl.setLY(row * UNIT_H);
			tl.resize(UNIT_W - 5, UNIT_H - 10);

			if ((col + 2) * UNIT_W >= getWidth()) {
				col = -1;
				row++;
			}

			col++;
		}
	}

	private class TableUnit extends LayerSprite {
		private TextButton master = new TextButton();
		private TextButton status = new TextButton();

		public TableUnit(Table table) {
			setBgColor(RGB.LIGHTGRAY);
			setBgRadius(10);

			master.setText(table.getMaster().name);
			add(master);

			status.setText(table.getPlayerList().size() + " / " + table.getMax());
			add(status);
		}

		@Override
		protected void adjustMember() {
			master.resize(getWidth(), getHeight() / 2);
			status.resize(getWidth(), getHeight() / 2);
			status.setLY(getHeight() / 2);
		}
	}
}
