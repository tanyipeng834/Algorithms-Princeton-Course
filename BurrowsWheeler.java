
import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class BurrowsWheeler {
    private static final int R = 256;

    public static void transform() {
        int first = 0;
        String input = BinaryStdIn.readString();
        CircularSuffixArray csa = new CircularSuffixArray(input);
        for (int i = 0; i < csa.length(); i++) {
            if (csa.index(i) == 0) {
                first = i;

            }

        }
        BinaryStdOut.write(first);

        for (int i = 0; i < csa.length(); i++) {
            if (csa.index(i) == 0) {
                // Write the last character in the string
                BinaryStdOut.write(input.charAt(input.length() - 1));

            }
            // If there is higher than 1 we can take the number one less than the index

            else {
                BinaryStdOut.write(input.charAt(csa.index(i)-1));
            }

        }
         BinaryStdOut.close();

    }

    // apply Burrows-Wheeler inverse transform,
    // reading from standard input and writing to standard output
    public static void inverseTransform(){

        int first = BinaryStdIn.readInt();
        int[] count = new int[R + 1];
        
        String lastCol = BinaryStdIn.readString();
        int len = lastCol.length();
        char [] firstCol = new char[len];
        int [] next = new int[len];
        // use this to store the count for each asc2 character
        for(int i=0;i<len;i++){
            count[lastCol.charAt(i)+1]++;


        }

        // Get the cumulative counts

        for (int i=0;i<R;i++){
            count[i+1] +=count[i];
        }

        for (int i=0;i<len;i++){
            int posi = count[lastCol.charAt(i)]++;
            firstCol[posi] = lastCol.charAt(i);
            next[posi] =i;



        }

        for (int i = 0; i < len; i++) {
            BinaryStdOut.write(firstCol[first]);
            first = next[first];
        }
        BinaryStdOut.close();




    }

    // if args[0] is "-", apply Burrows-Wheeler transform
    // if args[0] is "+", apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {
        if (args[0].equals("-"))
            BurrowsWheeler.transform();
        if (args[0].equals("+"))
            BurrowsWheeler.inverseTransform();

    }

}
