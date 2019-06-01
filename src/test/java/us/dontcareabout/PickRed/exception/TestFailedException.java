package us.dontcareabout.PickRed.exception;

public class TestFailedException extends RuntimeException {
	public TestFailedException(String msg) {
		super("\n" + msg);
	}
}
