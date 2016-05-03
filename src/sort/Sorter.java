package sort;

import java.util.Arrays;
/**
 * Sort int[] in one thread
 * @author Yulia Krivitskaya
 *
 */

public class Sorter extends Thread {

	/** incoming array  */
	private int[] incoming;

	public int[] getIncoming() {
		return incoming;
	}

	/**
	 * Sort array
	 * 
	 * @param a - array int[]
	 */
	public void sort(int[] a) {
		Arrays.sort(a);
	}

	/**
	 * Constructor
	 * @param arr - array
	 */
	Sorter(int[] arr) {
		incoming = arr;
	}

	/**
	 * Method run thread
	 */
	public void run() {
		sort(incoming);
	}
}
