package sg.edu.nus.comp.cs4218.misc;

import sg.edu.nus.comp.cs4218.exception.SortException;

public class MergeSort {
	private static final int ZERO = 0;
	private static final int DIGIT_START = 48;
	private static final int DIGIT_END = 57;
	private static final int CAPITAL_START = 65;
	private static final int CAPITAL_END = 90;
	private static final int LOWER_START = 97;
	private static final int LOWER_END = 122;
	private final boolean numFlag;

	public MergeSort(boolean numFlag) {
		this.numFlag = numFlag;
	}

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
	 * @return an sorted array
	 * @throws SortException
	 */
	public String[] mergeSort(String[] toSort, int pos1, int pos2) throws SortException {
		if (toSort == null) {
			throw new SortException("Array not found");
		}
		if (pos1 < ZERO || pos2 > toSort.length - 1) {
			throw new SortException("Postion cannot be negative or beyond array's size");
		}
		int front = pos1;
		int mid = 0;
		int back = pos2;

		if (front < back) {
			mid = (front + back) / 2;
			mergeSort(toSort, front, mid);
			mergeSort(toSort, mid + 1, back);
			merge(toSort, front, mid, back);
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
	 * @return an sorted array
	 */
	private void merge(String[] toSort, int front, int mid, int back) {
		int rightOfMid = mid + 1;
		int left = front;
		int iterate = 0;
		String[] tempArr = new String[back - front + 1];

		while (left <= mid && rightOfMid <= back) {
			if (this.numFlag) {// alphanum
				if (compareIncludeNumOrder(toSort[left], toSort[rightOfMid]) <= 0) {
					tempArr[iterate++] = toSort[left++];
				} else {
					tempArr[iterate++] = toSort[rightOfMid++];
				}
			} else {// natural sort
				if (customCompare(toSort[left], toSort[rightOfMid]) <= 0) {
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
	private String extractFirstNumberWordFromLine(String line) {
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
	private String checkIfWordIsDigitLastResort(String line, String result) {
		String assumedNumberWord = "";
		StringBuilder stringBuilder = new StringBuilder("");
		if (result.isEmpty()) {
			for (int i = 0; i < line.length(); i++) {
				if (!Character.isDigit(line.charAt(i))) {
					break;
				}
				stringBuilder.append(line.charAt(i));
			}
			assumedNumberWord = stringBuilder.toString();
		} else {
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
	private String checkIfStringIsDigit(String line) {
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
	private int compareIncludeNumOrder(String str1, String str2) {
		if (str1.isEmpty() && !str2.isEmpty()) {
			return -1;
		} else if (!str1.isEmpty() && str2.isEmpty()) {
			return 1;
		} else {
			return checkOnlyAtFirstNumWord(str1, str2);
		}

	}

	/**
	 * This method attempts to compare the first Number word of both strings
	 * 
	 * @param str1
	 *            first input string
	 * @param str2
	 *            second input string
	 * @return int
	 */
	private int checkOnlyAtFirstNumWord(String str1, String str2) {
		String assumedNumStr1;
		String assumedNumStr2;
		assumedNumStr1 = extractFirstNumberWordFromLine(str1);
		assumedNumStr2 = extractFirstNumberWordFromLine(str2);

		if (assumedNumStr1.length() > 0 && assumedNumStr2.length() > 0) {
			if (assumedNumStr1.compareTo(assumedNumStr2) == 0) {
				return customCompare(str1, str2);
			} else {
				return Integer.parseInt(assumedNumStr1) - Integer.parseInt(assumedNumStr2);
			}
		} else {
			return customCompare(str1, str2);
		}
	}

	/**
	 * This method compares both string character by character based on its
	 * precedence
	 * 
	 * @param str1
	 * @param str2
	 * @return int which indicates if a particular string should appear before
	 *         or after the other
	 * 
	 */
	public int customCompare(String str1, String str2) {
		int shortLength = str1.length() >= str2.length() ? str2.length() : str1.length();
		int result = str1.length() < str2.length() ? -1 : 1;
		if (str1.equals(str2)) {
			return 0;
		}
		for (int i = 0; i < shortLength; i++) {
			if (getRank(str1.charAt(i)) < getRank(str2.charAt(i))) {
				return 1;
			} else if (getRank(str1.charAt(i)) > getRank(str2.charAt(i))) {
				return -1;
			} else {
				if (str1.charAt(i) - str2.charAt(i) == 0) {
					continue;
				} else {
					return str1.charAt(i) - str2.charAt(i);
				}
			}
		}
		return result;
	}

	/**
	 * This methods identify the precedence/ rank of a character. Precedence
	 * goes in this order Special -> Digit -> Capital -> Lower
	 * 
	 * @param currChar
	 *            input character
	 * @return int rank number
	 */
	private int getRank(char currChar) {
		int asciiNum = currChar;
		int result = -1;
		if (asciiNum >= CAPITAL_START && asciiNum <= CAPITAL_END) {
			result = 2;
		} else if (asciiNum >= LOWER_START && asciiNum <= LOWER_END) {
			result = 1;
		} else if (asciiNum >= DIGIT_START && asciiNum <= DIGIT_END) {
			result = 3;
		} else {
			result = 4;// special characters
		}
		return result;
	}
}