package us.dontcareabout.PickRed.client.gf;

/**
 * 目前 WebSocket 傳遞的都是字串，
 * 所以在接收到字串之後必須透過 {@link #header} 來分辨該次訊息是何用途。
 * 因此抽出這個 class 以讓 {@link WSClient} 可以有處理上的彈性。
 */
public abstract class WSProcessor {
	public final String header;

	public WSProcessor(String header) {
		this.header = header;
	}

	public abstract void process(String data);
}
