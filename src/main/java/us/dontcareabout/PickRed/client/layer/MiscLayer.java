package us.dontcareabout.PickRed.client.layer;

import com.sencha.gxt.chart.client.draw.RGB;
import com.sencha.gxt.chart.client.draw.sprite.SpriteSelectionEvent;
import com.sencha.gxt.chart.client.draw.sprite.SpriteSelectionEvent.SpriteSelectionHandler;

import us.dontcareabout.PickRed.client.data.DataCenter;
import us.dontcareabout.gxt.client.draw.LayerSprite;
import us.dontcareabout.gxt.client.draw.component.TextButton;

public class MiscLayer extends LayerSprite {
	private TextButton myName = new TextButton();
	private TextButton createTableBtn = new TextButton("開桌");

	public MiscLayer() {
		myName.setText(DataCenter.getMyPlayer().name);
		myName.setMargin(5);
		add(myName);

		createTableBtn.setBgColor(RGB.RED);
		createTableBtn.setBgRadius(10);
		createTableBtn.setMargin(5);
		createTableBtn.addSpriteSelectionHandler(new SpriteSelectionHandler() {
			@Override
			public void onSpriteSelect(SpriteSelectionEvent event) {
				DataCenter.createTable();
			}
		});
		add(createTableBtn);
	}

	@Override
	protected void adjustMember() {
		double w = getWidth();
		double h = getHeight();

		myName.setLX(0);
		myName.setLY(0);
		myName.resize(200, h);
		createTableBtn.setLX(205);
		createTableBtn.setLY(0);
		createTableBtn.resize(w - 210, h);
	}
}
