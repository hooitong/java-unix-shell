
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ErrorTest0 {

  public static boolean debug = false;

  @Test
  public void test1() throws Throwable {

    if (debug) { System.out.format("%n%s%n","ErrorTest0.test1"); }


    sg.edu.nus.comp.cs4218.misc.MergeSort mergeSort1 = new sg.edu.nus.comp.cs4218.misc.MergeSort(false);
    java.lang.String str3 = mergeSort1.extractFirstNumberWordFromLine("");
    int i5 = mergeSort1.getRank('!');
    java.lang.String[] str_array6 = null;
    // during test generation this statement threw an exception of type java.lang.StackOverflowError in error
    java.lang.String[] str_array9 = mergeSort1.mergeSort(str_array6, (-29), 28);

  }

}
