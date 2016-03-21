package sg.edu.nus.comp.cs4218.misc;

import java.util.ArrayList;

public class LineComparison {
	private static final int ZERO = 0;
	private static final int COL_ZERO = 0;
	private static final int COL_ONE = 1;
	private static final int COL_TWO = 2;
	private static final String TAB_LINE = "\t";
	private final ArrayList<String> strList1;
	private final ArrayList<String> strList2;

	public LineComparison(ArrayList<String> strList1, ArrayList<String> strList2) {
		this.strList1 = strList1;
		this.strList2 = strList2;
	}

	/**
	 * This method compare lines in the given two input list
	 * 
	 * @return ArrayList<ArrayList<String>> the zero index returns the
	 *         commfirst, the first index returns the commsecond the second
	 *         index returns, the commallmatches
	 */
	public ArrayList<ArrayList<String>> compareLines() {
		int indexLineFile1 = 0;
		int indexLineFile2 = 0;
		MergeSort mergeSort = new MergeSort(false);
		ArrayList<ArrayList<String>> mainAl = new ArrayList<ArrayList<String>>();
		mainAl.add(new ArrayList<String>());
		mainAl.add(new ArrayList<String>());
		mainAl.add(new ArrayList<String>());
		while (true) {
			int result = 0;
			String lineA = "";
			String lineB = "";
			lineA = getLineFromList(this.strList1, indexLineFile1);
			lineB = getLineFromList(this.strList2, indexLineFile2);
			if (lineA.length() == 0 && lineB.length() == 0) {
				break;
			} else if (lineA.length() == 0 && lineB.length() != 0) {
				mainAl.get(COL_ZERO).add(TAB_LINE);
				mainAl.get(COL_ONE).add(lineB);
				mainAl.get(COL_TWO).add(TAB_LINE);
				indexLineFile2++;
			} else if (lineA.length() != 0 && lineB.length() == 0) {
				mainAl.get(COL_ZERO).add(lineA);
				mainAl.get(COL_ONE).add(TAB_LINE);
				mainAl.get(COL_TWO).add(TAB_LINE);
				indexLineFile1++;
			} else {// compare lines that are not empty
				result = mergeSort.customCompare(lineA, lineB);
				if (result == ZERO) {
					mainAl.get(COL_ZERO).add(TAB_LINE);
					mainAl.get(COL_ONE).add(TAB_LINE);
					mainAl.get(COL_TWO).add(lineA);
					indexLineFile1++;
					indexLineFile2++;

				} else if (result < ZERO) {// output to first col
					mainAl.get(COL_ZERO).add(lineA);
					mainAl.get(COL_ONE).add(TAB_LINE);
					mainAl.get(COL_TWO).add(TAB_LINE);
					indexLineFile1++;
				} else {// output to second column
					mainAl.get(COL_ZERO).add(TAB_LINE);
					mainAl.get(COL_ONE).add(lineB);
					mainAl.get(COL_TWO).add(TAB_LINE);
					indexLineFile2++;
				}
			}
		}
		return mainAl;
	}

	/**
	 * This method gets the particular line from a list based on the index
	 * provided
	 * 
	 * @param strList
	 *            <String>
	 * @param lineIndexFile1
	 * @return string content of the string
	 */
	private String getLineFromList(ArrayList<String> strList, int lineIndexFile1) {
		String currentLine = "";
		if (lineIndexFile1 < strList.size()) {
			currentLine = strList.get(lineIndexFile1);
		}
		return currentLine;
	}
}