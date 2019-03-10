package us.dontcareabout.PickRed.client.ui;

import java.util.ArrayList;

import com.sencha.gxt.chart.client.draw.RGB;

import us.dontcareabout.PickRed.shared.Player;
import us.dontcareabout.PickRed.shared.Table;
import us.dontcareabout.gwt.client.Console;
import us.dontcareabout.gxt.client.draw.LayerContainer;
import us.dontcareabout.gxt.client.draw.LayerSprite;
import us.dontcareabout.gxt.client.draw.component.TextButton;

public class TableView extends LayerContainer {
	private Table table;
	private TextButton startBtn = new TextButton("開始");
	private ArrayList<PlayerLayer> players = new ArrayList<>();

	public TableView() {
		startBtn.setBgColor(RGB.RED);
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
		clear();
		players.clear();

		addLayer(startBtn);

		Console.log(table.getPlayerList().size());
		for (Player player : table.getPlayerList()) {
			PlayerLayer pl = (player == table.getMaster()) ? new MasterPlayer(player) : new MemberPlayer(player);
			players.add(pl);
			addLayer(pl);
		}

		onResize(getOffsetWidth(), getOffsetHeight());
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
	}

	private class MasterPlayer extends PlayerLayer {
		MasterPlayer(Player player) {
			super(player);
			setBgColor(RGB.PINK);
			button.setText("關桌");
		}
	}

	private class MemberPlayer extends PlayerLayer {
		MemberPlayer(Player player) {
			super(player);
			setBgColor(RGB.LIGHTGRAY);
			button.setText("踢");
		}
	}
}
