package us.dontcareabout.PickRed.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Table implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	private Player master;
	private ArrayList<Player> playerList = new ArrayList<>();
	private int max;
	private Date createTime;

	//just for GWT RPC
	Table() {}

	public Table(String id, Player master, int max) {
		this.id = id;
		this.master = master;
		this.max = max;
		join(master);
		createTime = new Date();
	}

	public boolean join(Player player) {
		if (isFull()) { return false; }

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

	public int getPlayerAmount() {
		return playerList.size();
	}

	public int getMax() {
		return max;
	}

	public String getId() {
		return id;
	}

	public Player getMaster() {
		return master;
	}

	public Date getCreateTime() {
		return createTime;
	}
}
