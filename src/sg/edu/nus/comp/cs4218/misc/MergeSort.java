package sg.edu.nus.comp.cs4218.misc;

public class MergeSort {
	private static final int ZERO = 0;
	private static final int WHITESPACE = 32;

	/**
	 * Uses the merge sort algorithm. Start by dividing them before calling
	 * merge method
	 * 
	 * @param toSort
	 *            array to be sorted
	 * @param pos1
	 *            starting position of the string
	 * @param pos2
	 *            ending position of the string
	 * @param numFlag
	 *            set true to enable alphanum ordering
	 * @return an sorted array
	 */
	public static String[] mergeSort(String[] toSort, int pos1, int pos2,
			boolean numFlag) {
		int front = pos1;
		int mid = 0;
		int back = pos2;

		if (front < back) {
			mid = (front + back) / 2;
			mergeSort(toSort, front, mid, numFlag);
			mergeSort(toSort, mid + 1, back, numFlag);
			merge(toSort, front, mid, back, numFlag);
		}

		return toSort;
	}

	/**
	 * Sort contents in a file Natural ordering is the default ordering change
	 * numFlag to true to enable alphanum ordering
	 * 
	 * @param toSort
	 *            array to be sorted
	 * @param front
	 *            starting position of array
	 * @param mid
	 *            middle position of array
	 * @param back
	 *            ending position of array
	 * @param numFlag
	 *            set true to enable alphanum ordering
	 * @return an sorted array
	 */
	public static void merge(String[] toSort, int front, int mid, int back,
			boolean numFlag) {
		int rightOfMid = mid + 1;
		int left = front;
		int iterate = 0;
		String[] tempArr = new String[back - front + 1];

		while (left <= mid && rightOfMid <= back) {
			if (numFlag) {// alphanum
				if (compareIncludeNumOrder(toSort[left], toSort[rightOfMid]) <= 0) {
					tempArr[iterate++] = toSort[left++];
				} else {
					tempArr[iterate++] = toSort[rightOfMid++];
				}
			} else {// natural sort
				if (toSort[left].compareTo(toSort[rightOfMid]) <= 0) {
					tempArr[iterate++] = toSort[left++];
				} else {
					tempArr[iterate++] = toSort[rightOfMid++];
				}
			}

		}

		while (left <= mid) {
			tempArr[iterate++] = toSort[left++];
		}
		while (rightOfMid <= back) {
			tempArr[iterate++] = toSort[rightOfMid++];
		}

		System.arraycopy(tempArr, ZERO, toSort, front, tempArr.length);
	}

	/**
	 * Attempts to get the first number of a line. If the line itself is a
	 * number, the line will be returned If a line consists of words and the
	 * first word happens to be a number, only the first word will be returned
	 * 
	 * @param line
	 *            input string
	 * @return a string
	 */
	public static String extractFirstNumberWordFromLine(String line) {
		String result = "";
		result = checkIfStringIsDigit(line);
		result = checkIfWordIsDigitLastResort(line, result);
		return result;
	}

	/**
	 * This method gets the first word of the line and checks if it consists
	 * only of numbers
	 * 
	 * @param line
	 *            input string
	 * @param result
	 *            an empty string if the input line does not consists of numbers
	 * @return the first word of a string that is a number
	 */
	private static String checkIfWordIsDigitLastResort(String line,
			String result) {
		String assumedNumberWord = "";
		StringBuilder stringBuilder = new StringBuilder("");
		if (result.isEmpty()) {
			for (int i = 0; i < line.length(); i++) {
				if(!Character.isDigit(line.charAt(i))){
					break;
				}
				stringBuilder.append(line.charAt(i));
			}
			assumedNumberWord = stringBuilder.toString();
		}else{
			assumedNumberWord = result;
		}
		return assumedNumberWord;
	}

	/**
	 * It checks if the entire string consists only of numbers
	 * 
	 * @param line
	 *            input string
	 * @return the orginal string
	 */
	private static String checkIfStringIsDigit(String line) {
		String result = "";
		int count = 0;
		for (int i = 0; i < line.length(); i++) {
			if (Character.isDigit(line.charAt(i))) {
				count++;
			}
		}
		if (count == line.length()) {
			result = line;
		}
		return result;
	}

	/**
	 * Compare Strings in alphanum then in natural order
	 * 
	 * @param str1
	 *            first input string
	 * @param str2
	 *            second input string
	 * @return int
	 */
	public static int compareIncludeNumOrder(String str1, String str2) {
		if (str1.isEmpty() && !str2.isEmpty()) {
			return -1;
		} else if (!str1.isEmpty() && str2.isEmpty()) {
			return 1;
		} else {
			return checkOnlyAtFirstNumWord(str1, str2);
		}

	}

	/**
	 * @param str1
	 * @param str2
	 * @return int
	 */
	private static int checkOnlyAtFirstNumWord(String str1, String str2) {
		String assumedNumStr1;
		String assumedNumStr2;
		assumedNumStr1 = extractFirstNumberWordFromLine(str1);
		assumedNumStr2 = extractFirstNumberWordFromLine(str2);

		if (!assumedNumStr1.isEmpty() && !assumedNumStr2.isEmpty()) {
			if (assumedNumStr1.compareTo(assumedNumStr2) == 0) {
				return str1.compareTo(str2);
			} else {
				return Integer.parseInt(assumedNumStr1)
						- Integer.parseInt(assumedNumStr2);
			}
		}else{
			return str1.compareTo(str2);
		}
	}
}