package sg.edu.nus.comp.cs4218.misc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class SortHelper {
	private static final int ONE = 1;
	private static final int ZERO = 0;
	private static final int TWO = 2;
	private static final int THREE = 3;

	private SortHelper() {
	}

	/**
	 * this method separates strings into a list based on the combination type
	 * given
	 * 
	 * @param combinationType
	 *            indicate the number of combination in a string
	 * @param toSort
	 *            input array to be sorted
	 * @return List<String> a sorted list of strings
	 */
	public static List<String> separateBasedOnType(int combinationType, String... toSort) {
		int simpleCount = 0, capitalCount = 0, specialCount = 0, numberCount = 0;
		List<String> resultList = new ArrayList<String>();
		for (int i = 0; i < toSort.length; i++) {
			String currentLine = toSort[i];
			simpleCount = SortTypeSeparator.getSimpleCharInLineCount(currentLine);
			capitalCount = SortTypeSeparator.getCapitalCharInLineCount(currentLine);
			specialCount = SortTypeSeparator.getSpecialCharInLineCount(currentLine);
			numberCount = SortTypeSeparator.getNumberCharInLineCount(currentLine);

			if (combinationType == ONE) {
				separateFromOneType(simpleCount, capitalCount, specialCount, numberCount, resultList, currentLine);
			} else if (combinationType == TWO) {
				separateFromTwoType(simpleCount, capitalCount, specialCount, numberCount, resultList, currentLine);
			} else if (combinationType == THREE) {
				separateFromThreeType(simpleCount, capitalCount, specialCount, numberCount, resultList, currentLine);
			} else {
				separateFromFourType(simpleCount, capitalCount, specialCount, numberCount, resultList, currentLine);
			}

		}
		return resultList;
	}

	/**
	 * This method adds to a list only if the string has 4 different
	 * combinations
	 * 
	 * @param simpleCount
	 *            count of lowercase characters in a string
	 * @param capitalCount
	 *            count of uppercase characters in a string
	 * @param specialCount
	 *            count of special characters in a string
	 * @param numberCount
	 *            count of digits in a string
	 * @param resultList
	 *            line would be added to list only it matches the conditions
	 * @param currentLine
	 *            input string
	 */
	private static void separateFromFourType(int simpleCount, int capitalCount, int specialCount, int numberCount,
			List<String> resultList, String currentLine) {
		if (simpleCount != 0 && capitalCount != 0 && numberCount != 0 && specialCount != 0
				&& (simpleCount + capitalCount + numberCount + specialCount) == currentLine.length()) {
			resultList.add(currentLine);
		}
	}

	/**
	 * This method adds to a list only if the string has 3 different
	 * combinations
	 * 
	 * @param simpleCount
	 *            count of lowercase characters in a string
	 * @param capitalCount
	 *            count of uppercase characters in a string
	 * @param specialCount
	 *            count of special characters in a string
	 * @param numberCount
	 *            count of digits in a string
	 * @param resultList
	 *            line would be added to list only it matches the conditions
	 * @param currentLine
	 *            input string
	 */
	private static void separateFromThreeType(int simpleCount, int capitalCount, int specialCount, int numberCount,
			List<String> resultList, String currentLine) {
		if (simpleCount != 0 && capitalCount != 0 && numberCount != 0
				&& (simpleCount + capitalCount + numberCount) == currentLine.length()) {
			resultList.add(currentLine);
		} else if (simpleCount != 0 && capitalCount != 0 && specialCount != 0
				&& (simpleCount + capitalCount + specialCount) == currentLine.length()) {
			resultList.add(currentLine);
		} else if (simpleCount != 0 && numberCount != 0 && specialCount != 0
				&& (simpleCount + numberCount + specialCount) == currentLine.length()) {
			resultList.add(currentLine);
		} else if (capitalCount != 0 && numberCount != 0 && specialCount != 0
				&& (capitalCount + numberCount + specialCount) == currentLine.length()) {
			resultList.add(currentLine);
		}
	}

	/**
	 * This method adds to a list only if the string has 2 different
	 * combinations
	 * 
	 * @param simpleCount
	 *            count of lowercase characters in a string
	 * @param capitalCount
	 *            count of uppercase characters in a string
	 * @param specialCount
	 *            count of special characters in a string
	 * @param numberCount
	 *            count of digits in a string
	 * @param resultList
	 *            line would be added to list only it matches the conditions
	 * @param currentLine
	 *            input string
	 */
	private static void separateFromTwoType(int simpleCount, int capitalCount, int specialCount, int numberCount,
			List<String> resultList, String currentLine) {
		if (simpleCount != 0 && capitalCount != 0 && (simpleCount + capitalCount) == currentLine.length()) {
			resultList.add(currentLine);
		} else if (simpleCount != 0 && numberCount != 0 && (simpleCount + numberCount) == currentLine.length()) {
			resultList.add(currentLine);
		} else if (simpleCount != 0 && specialCount != 0 && (simpleCount + specialCount) == currentLine.length()) {
			resultList.add(currentLine);
		} else if (capitalCount != 0 && numberCount != 0 && (capitalCount + numberCount) == currentLine.length()) {
			resultList.add(currentLine);
		} else if (capitalCount != 0 && specialCount != 0 && (capitalCount + specialCount) == currentLine.length()) {
			resultList.add(currentLine);
		} else if (numberCount != 0 && specialCount != 0 && (numberCount + specialCount) == currentLine.length()) {
			resultList.add(currentLine);
		}
	}

	/**
	 * This method adds to a list only if the string has 1 different
	 * combinations
	 * 
	 * @param simpleCount
	 *            count of lowercase characters in a string
	 * @param capitalCount
	 *            count of uppercase characters in a string
	 * @param specialCount
	 *            count of special characters in a string
	 * @param numberCount
	 *            count of digits in a string
	 * @param resultList
	 *            line would be added to list only it matches the conditions
	 * @param currentLine
	 *            input string
	 */
	private static void separateFromOneType(int simpleCount, int capitalCount, int specialCount, int numberCount,
			List<String> resultList, String currentLine) {
		if (simpleCount == currentLine.length()) {
			resultList.add(currentLine);
		} else if (capitalCount == currentLine.length()) {
			resultList.add(currentLine);
		} else if (numberCount == currentLine.length()) {
			resultList.add(currentLine);
		} else if (specialCount == currentLine.length()) {
			resultList.add(currentLine);
		}
	}

	/**
	 * this method calls the mergesort class to sort and return an arraylist of
	 * string
	 * 
	 * @param resultList
	 *            rhe input list
	 * @return List<String> sorted list of string
	 */
	public static List<String> sortHelper(List<String> resultList) {
		MergeSort mergeSort = new MergeSort(false);
		ArrayList<String> ansList = new ArrayList<String>(Arrays.asList(
				mergeSort.mergeSort(resultList.toArray(new String[resultList.size()]), ZERO, resultList.size() - 1)));
		return ansList;
	}

	/**
	 * this method calls the mergesort class to sort with numflag on and return
	 * an arraylist of string
	 * 
	 * @param resultList
	 *            rhe input list
	 * @return List<String> sorted list of string
	 */
	public static List<String> sortHelperWithNumFlag(List<String> resultList) {
		MergeSort mergeSort = new MergeSort(true);
		ArrayList<String> ansList = new ArrayList<String>(Arrays.asList(
				mergeSort.mergeSort(resultList.toArray(new String[resultList.size()]), ZERO, resultList.size() - 1)));
		return ansList;
	}
}
