package sort;

import java.util.Random;

/**
 * Merge Sort
 * 
 * @author Yulia Krivitskaya
 *
 */

public class MergeSortThreaded {

	/**
	 * Merge arrays
	 * 
	 * @param arr1
	 *            - first array
	 * @param arr2
	 *            - second array
	 * @return - merge array
	 */
	public static int[] merge(int[] arr1, int arr2[]) {
		int n = arr1.length + arr2.length;
		int[] arr = new int[n];
		int i1 = 0;
		int i2 = 0;
		for (int i = 0; i < n; i++) {
			if (i1 == arr1.length) {
				arr[i] = arr2[i2++];
			} else if (i2 == arr2.length) {
				arr[i] = arr1[i1++];
			} else {
				if (arr1[i1] < arr2[i2]) {
					arr[i] = arr1[i1++];
				} else {
					arr[i] = arr2[i2++];
				}
			}
		}
		return arr;
	}

	public static void main(String[] args) throws InterruptedException {
		// fill array
		Random rand = new Random();
		int[] original = new int[10000000];
		for (int i = 0; i < original.length; i++) {
			original[i] = rand.nextInt(1000);
		}

		// sort array in one thread
		long startTimeOneThread = System.currentTimeMillis();
		Sorter runner = new Sorter(original);
		runner.start();
		runner.join();
		long stopTimeOneThread = System.currentTimeMillis();
		long elapsedTimeOneThread = stopTimeOneThread - startTimeOneThread;
		System.out.println("1-thread MergeSort takes: " + (float) elapsedTimeOneThread / 1000 + " seconds");

		// sort array in N treads
		long startTimeNThread = System.currentTimeMillis();

		/** Numbers of pool */
		int POOL = 4;
		Sorter[] sorters = new Sorter[POOL];

		for (int i = 0; i < POOL; i++) {
			int size = original.length / POOL;
			int[] subArray = new int[size];
			System.arraycopy(original, size * i, subArray, 0, size);
			sorters[i] = new Sorter(subArray);
		}
		int endPart = original.length - (original.length / POOL) * (POOL - 1);
		int[] subArray = new int[endPart];
		System.arraycopy(original, (original.length / POOL) * (POOL - 1), subArray, 0, endPart);
		sorters[POOL - 1] = new Sorter(subArray);

		for (Sorter arr : sorters) {
			arr.run();
		}
		for (Sorter arr : sorters) {
			arr.join();
		}
		for (Sorter arr : sorters) {
			arr.run();
		}
		if (POOL == 2) {
			merge(sorters[0].getIncoming(), sorters[1].getIncoming());
		}
		if (POOL > 2) {
			int[] res = sorters[0].getIncoming();
			for (int i = 1; i < POOL; i++) {
				int[] a = merge(res, sorters[i].getIncoming());
				res = a;
			}

		}

		long stopTimeNThread = System.currentTimeMillis();
		long elapsedTimeNThread = stopTimeNThread - startTimeNThread;
		System.out.println(POOL + "-thread MergeSort takes: " + (float) elapsedTimeNThread / 1000 + " seconds");

	}

}
