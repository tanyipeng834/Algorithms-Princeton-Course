import java.util.Arrays;
import java.util.Comparator;

public class CircularSuffixArray {
    private String inputString;
    private Integer[] suffixArray;
    private int lengthString;

    public CircularSuffixArray(String s) {
        if (s == null) {
            throw new IllegalArgumentException("String cannot be null");
        }
        this.inputString = s;
        this.lengthString = s.length();
        this.suffixArray = new Integer[this.lengthString];
        for (int i = 0; i < this.inputString.length(); i++) {
            this.suffixArray[i] = i;

        }

        Arrays.sort(this.suffixArray, new Comparator<Integer>() {

            public int compare(Integer int1, Integer int2) {

                int compareLength = lengthString - Math.max(int1, int2);
                for (int i = 0; i < compareLength; i++) {
                    if (inputString.charAt(int1 + i) > inputString.charAt(int2 + i)) {
                        return 1;

                    } else if (inputString.charAt(int1 + i) < inputString.charAt(int2 + i)) {
                        return -1;

                    }

                }
                // Int 2 is longer hence it should be considered larger
                if (int1 > int2) {
                    return -1;
                } else {
                    return 1;
                }

            }

        });
        for (Integer suffix : this.suffixArray) {
            System.out.println(suffix);
        }

    }

    // length of s
    public int length() {
        return this.lengthString;

    }

    // returns index of ith sorted suffix
    public int index(int i) {
        if (i < 0 || i > this.lengthString - 1) {
            throw new IllegalArgumentException("Index is out of bounds");
        }

        return this.suffixArray[i];

    }

    // unit testing (required)
    public static void main(String[] args) {

        CircularSuffixArray csa = new CircularSuffixArray("BAB");

    }

}
