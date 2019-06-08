package us.dontcareabout.PickRed.shared.gf;

import java.util.ArrayList;
import java.util.List;

public class SyncData<T> {
	private List<T> data = new ArrayList<>();
	private SyncType type;

	public List<T> getData() {
		return data;
	}
	public void setData(List<T> d) {
		data.clear();
		data.addAll(d);
	}
	public void addData(T d) {
		data.add(d);
	}
	public SyncType getType() {
		return type;
	}
	public void setType(SyncType type) {
		this.type = type;
	}
}
