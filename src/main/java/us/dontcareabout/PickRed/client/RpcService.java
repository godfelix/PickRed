package us.dontcareabout.PickRed.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import us.dontcareabout.PickRed.shared.Player;
import us.dontcareabout.PickRed.shared.Table;

@RemoteServiceRelativePath("RPC")
public interface RpcService extends RemoteService{
	Player getMyPlayer();

	ArrayList<Table> getTables();
	Table createTable();
	boolean joinTable(String tableId);

	void broadcast(String message);
}
