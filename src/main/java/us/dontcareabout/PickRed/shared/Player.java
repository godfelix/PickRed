package us.dontcareabout.PickRed.shared;

import java.io.Serializable;

public class Player implements Serializable {
	private static final long serialVersionUID = 1L;

	public String id;
	public String name;

	//just for GWT RPC
	Player() {}

	public Player(String id, String name) {
		this.id = id;
		this.name = name;
	}
}
