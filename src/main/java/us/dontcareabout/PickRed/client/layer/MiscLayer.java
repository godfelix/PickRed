package us.dontcareabout.PickRed.client.layer;

import com.sencha.gxt.chart.client.draw.RGB;

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
		add(createTableBtn);
	}

	@Override
	protected void adjustMember() {
		double w = getWidth();
		double h = getHeight();

		myName.setLX(0);
		myName.setLY(0);
		myName.onResize(w - 5, 100);
		createTableBtn.setLX(0);
		createTableBtn.setLY(110);
		createTableBtn.onResize(w - 5, h - 120);
	}
}
