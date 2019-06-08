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
	private List<Player> playerList = new ArrayList<>();
	private int max;
	private long createTime;

	//just for GWT RPC
	Table() {}

	public Table(String id, Player master, int max) {
		this.id = id;
		this.master = master;
		this.max = max;
		join(master);
		createTime = new Date().getTime();
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

	public long getCreateTime() {
		return createTime;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setMaster(Player master) {
		this.master = master;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public void setPlayerList(List<Player> list) {
		playerList.clear();
		playerList.addAll(list);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Table other = (Table) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
