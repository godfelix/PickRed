package us.dontcareabout.PickRed.shared;

import java.util.Random;

/**
 * Game Master Toolkit
 */
public class GMT {
	private static final Random random = new Random();

	public static <T> void shuffling(T[] array) {
		//Fisher-Yates
		T tmp;
		int index;
		int last = array.length - 1;

		while(last > 0) {
			index = random.nextInt(last + 1);
			tmp = array[last];
			array[last] = array[index];
			array[index] = tmp;
			last--;
		}
	}
}
