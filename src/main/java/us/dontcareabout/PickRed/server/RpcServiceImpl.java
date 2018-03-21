package us.dontcareabout.PickRed.server;

import java.io.IOException;

import javax.servlet.ServletException;

import org.springframework.web.context.support.WebApplicationContextUtils;

import us.dontcareabout.PickRed.client.RpcService;
import us.dontcareabout.gwt.server.GFServiceServlet;
import us.dontcareabout.gwt.server.WebSocketServer;

public class RpcServiceImpl extends GFServiceServlet implements RpcService {
	private static final long serialVersionUID = 1L;

	private WebSocketServer wsServer;

	@Override
	public void init() throws ServletException {
		super.init();
		wsServer = WebApplicationContextUtils
			.getWebApplicationContext(this.getServletContext())
			.getBean(WebSocketServer.class);
	}

	@Override
	public void broadcast(String message) {
		try {
			wsServer.broadcast(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
