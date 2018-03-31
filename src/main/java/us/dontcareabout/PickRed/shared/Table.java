package us.dontcareabout.PickRed.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Table implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	private String masterId;
	private final ArrayList<Player> playerList = new ArrayList<>();
	private int max;

	//just for GWT RPC
	Table() {}

	public Table(String id, String masterId) {
		this.id = id;
		this.masterId = masterId;
	}

	public boolean join(Player player) {
		if (!isFull()) { return false; }

		playerList.add(player);
		return true;
	}

	public void leave(String player) {
		playerList.remove(player);
	}

	public boolean isFull() {
		return playerList.size() == max;
	}

	public List<Player> getPlayerList() {
		return Collections.unmodifiableList(playerList);
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public String getId() {
		return id;
	}

	public String getMasterId() {
		return masterId;
	}
}
