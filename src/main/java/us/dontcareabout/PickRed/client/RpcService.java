package us.dontcareabout.PickRed.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import us.dontcareabout.PickRed.shared.Player;

@RemoteServiceRelativePath("RPC")
public interface RpcService extends RemoteService{
	Player getMyPlayer();

	void broadcast(String message);
}
