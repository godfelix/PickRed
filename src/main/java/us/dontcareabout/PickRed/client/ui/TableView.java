package us.dontcareabout.PickRed.client.ui;

import java.util.ArrayList;

import com.sencha.gxt.chart.client.draw.RGB;
import com.sencha.gxt.chart.client.draw.sprite.SpriteSelectionEvent;
import com.sencha.gxt.chart.client.draw.sprite.SpriteSelectionEvent.SpriteSelectionHandler;

import us.dontcareabout.PickRed.client.data.DataCenter;
import us.dontcareabout.PickRed.client.data.TableDataReadyEvent;
import us.dontcareabout.PickRed.client.data.TableDataReadyEvent.TableDataReadyHandler;
import us.dontcareabout.PickRed.shared.Player;
import us.dontcareabout.PickRed.shared.Table;
import us.dontcareabout.gxt.client.draw.LayerContainer;
import us.dontcareabout.gxt.client.draw.LayerSprite;
import us.dontcareabout.gxt.client.draw.component.TextButton;

public class TableView extends LayerContainer {
	private Table table;
	private TextButton startBtn = new TextButton("開始");
	private ArrayList<PlayerLayer> players = new ArrayList<>();

	public TableView() {
		startBtn.setBgColor(RGB.RED);
		startBtn.setHidden(true);
		startBtn.addSpriteSelectionHandler(new SpriteSelectionHandler() {
			@Override
			public void onSpriteSelect(SpriteSelectionEvent event) {
				DataCenter.startGame(table);
			}
		});
		addLayer(startBtn);

		DataCenter.addTableDataReady(new TableDataReadyHandler() {
			@Override
			public void onTableDataReady(TableDataReadyEvent event) {
				if (table == null) { return; }

				table = DataCenter.findTable(table.getId());
				refresh();
			}
		});
	}

	@Override
	protected void onResize(int width, int height) {
		startBtn.setLX(100);
		startBtn.setLY(height - 55);
		startBtn.resize(width - 200, 45);

		double x = 10;
		double unitW = width / table.getMax() - 20;
		for (PlayerLayer pl : players) {
			pl.setLX(x);
			pl.setLY(5);
			x += unitW + 20;
			pl.resize(unitW, height - 70);
		}

		super.onResize(width, height);
	}

	public void setData(Table table) {
		this.table = table;
		refresh();
	}

	private void refresh() {
		for (PlayerLayer pl : players) {
			pl.undeploy();
		}
		players.clear();

		for (Player player : table.getPlayerList()) {
			PlayerLayer pl = (player.equals(table.getMaster())) ? new MasterPlayer(player) : new MemberPlayer(player);
			players.add(pl);
			addLayer(pl);
		}

		startBtn.setHidden(!(table.isFull() && iAmMaster()));

		onResize(getOffsetWidth(), getOffsetHeight());
	}

	private boolean iAmMaster() {
		return DataCenter.getMyPlayer().equals(table.getMaster());
	}

	private class PlayerLayer extends LayerSprite {
		final Player player;
		TextButton name = new TextButton();
		TextButton button = new TextButton();

		PlayerLayer(Player player) {
			this.player = player;

			name.setText(player.name);
			add(name);

			button.setBgColor(RGB.BLACK);
			button.setTextColor(RGB.WHITE);
			button.setBgRadius(5);
			add(button);

			setBgRadius(5);
		}

		@Override
		protected void adjustMember() {
			name.setLX(5);
			name.setLY(5);
			name.resize(getWidth() - 10, getHeight() - 60);

			button.setLX(5);
			button.setLY(getHeight() - 55);
			button.resize(getWidth() - 10, 50);
		}

		boolean hereIam() {
			return player.equals(DataCenter.getMyPlayer());
		}
	}

	private class MasterPlayer extends PlayerLayer {
		MasterPlayer(Player player) {
			super(player);
			setBgColor(RGB.PINK);

			if (hereIam()) {
				button.setText("關桌");
			} else {
				button.setHidden(true);
			}
		}
	}

	private class MemberPlayer extends PlayerLayer {
		MemberPlayer(Player player) {
			super(player);
			setBgColor(RGB.LIGHTGRAY);

			if (iAmMaster()) {
				button.setText("踢");
			} else if (hereIam()) {
				button.setText("離開");
			} else {
				button.setHidden(true);
			}
		}
	}
}
