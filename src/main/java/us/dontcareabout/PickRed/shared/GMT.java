package us.dontcareabout.PickRed.shared;

import java.util.Random;

/**
 * Game Master Toolkit
 */
public class GMT {
	private static final Random random = new Random();

	public static <T> void shuffling(T[] t) {
		T tmp;
		int index;

		for (int i = 0; i < t.length * 10000; i++) {
			index = random.nextInt(t.length);
			tmp = t[0];
			t[0] = t[index];
			t[index] = tmp;
		}
	}
}
