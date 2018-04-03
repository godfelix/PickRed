package us.dontcareabout.PickRed.client.data;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

import us.dontcareabout.PickRed.client.data.TableDataReadyEvent.TableDataReadyHandler;

public class TableDataReadyEvent extends GwtEvent<TableDataReadyHandler> {
	public static final Type<TableDataReadyHandler> TYPE = new Type<TableDataReadyHandler>();

	@Override
	public Type<TableDataReadyHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(TableDataReadyHandler handler) {
		handler.onTableDataReady(this);
	}

	public interface TableDataReadyHandler extends EventHandler{
		public void onTableDataReady(TableDataReadyEvent event);
	}
}
