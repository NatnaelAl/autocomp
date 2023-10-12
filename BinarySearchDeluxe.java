import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class BinarySearchDeluxe {

    // Returns the index of the first key in the sorted array a[]
    // that is equal to the search key, or -1 if no such key.
    public static <Key> int firstIndexOf(Key[] a, Key key, Comparator<Key> compara) {
        testCase(a, key, compara);
        /* @citation Adapted from: Robert Sedgewick and Kevin Wayne. Computer
         * Science: An Interdisciplinary Approach. Addison-Wesley Professional,
         * 2016, Section 1.4 */
        int lo = 0;
        int hi = a.length - 1;
        // Keeps track of any index found during BS
        int occurence = -1;

        while (lo <= hi) {
            int mid = (lo + hi) >>> 1;
            int compare = compara.compare(key, a[mid]);
            if (compare < 0)
                hi = mid - 1;
            else if (compare > 0)
                lo = mid + 1;
            else {
                // Continues the binary seach while also storing index of the
                // first occurence of the key
                occurence = mid;
                hi = mid - 1;
            }
        }
        // Returns -1 if occurence was never updated with another other
        // instance of the key in the list
        return occurence;
    }

    // Returns the index of the last key in the sorted array a[]
    // that is equal to the search key, or -1 if no such key.
    public static <Key> int lastIndexOf(Key[] a, Key key, Comparator<Key> comparator) {
        testCase(a, key, comparator);
        /* @citation Adapted from: Robert Sedgewick and Kevin Wayne. Computer
         * Science: An Interdisciplinary Approach. Addison-Wesley Professional,
         * 2016, Section 1.4 */

        // finding the first matching index in the reverse of the array
        // (which is in reverse-sorted order), replaces less with
        // greater, lo with hi, and index 0 with len - 1
        int lo = a.length - 1;
        int hi = 0;
        int occurence = -1;

        while (lo >= hi) {
            int mid = (lo + hi) >>> 1;
            int compare = comparator.compare(key, a[mid]);
            if (compare > 0)
                hi = mid + 1;
            else if (compare < 0)
                lo = mid - 1;
            else {
                // Continues the binary seach while also storing index of the
                // last occurence of the key
                occurence = mid;
                hi = mid + 1;
            }
        }
        // Returns -1 if occurence was never updated with another other
        // instance of the key in the list
        return occurence;

    }

    // Throw an IllegalArgumentException if any argument to either
    // firstIndexOf() or lastIndexOf() is null
    private static <Key> void testCase(Key[] a, Key key, Comparator<Key> comparator) {
        if (a == null || key == null || comparator == null)
            throw new IllegalArgumentException("Null arguments");
    }

    // unit testing (required)
    public static void main(String[] args) {
        String[] a = { "A", "A", "C", "G", "G", "T" };
        Comparator<String> comparator = String.CASE_INSENSITIVE_ORDER;
        int index = BinarySearchDeluxe.firstIndexOf(a, "G", comparator);
        int index2 = BinarySearchDeluxe.lastIndexOf(a, "G", comparator);
        StdOut.println("Index of first 'G' " + index);
        StdOut.println("Index of last 'G' " + index2);
    }
}
