import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Comparator;

public class Term implements Comparable<Term> {

    // Numeric weight of Term
    private long weight;
    // Query string related to Term
    private String query;

    // Initializes a term with the given query string and weight.
    public Term(String query, long weight) {
        if (query == null || weight < 0)
            throw new IllegalArgumentException("Invalid arguments");
        this.weight = weight;
        this.query = query;
    }

    // Compares Terms according to the reversed order of weight
    private static class RevWeightComp implements java.util.Comparator<Term> {
        public int compare(Term obj1, Term obj2) {

            // Becasue we order by reverse weight, the comparison is the oposite
            // of typical greater value
            // returns int > 0 if obj1 < obj2
            // returns int < 0 if obj1 > obj2
            // returns 0 otherwise
            return Long.compare(obj2.weight, obj1.weight);

        }
    }

    // Compares the two terms in descending order by weight.
    public static Comparator<Term> byReverseWeightOrder() {
        return new RevWeightComp();
    }

    // Compares Terms according to the order of just thier prefixes
    private static class ByPrefixComparator implements Comparator<Term> {
        // Given substring length to compare
        private int r;

        // constructor to initalize r
        public ByPrefixComparator(int r) {
            this.r = r;
        }

        public int compare(Term obj1, Term obj2) {
            int len1 = obj1.query.length();
            int len2 = obj2.query.length();
            // // Compares only up to R is length of string is less than R
            int maxLen = Math.min(Math.min(len1, len2), r);
            for (int i = 0; i < maxLen; i++) {
                char first = obj1.query.charAt(i);
                char second = obj2.query.charAt(i);

                if (first > second)
                    return 1;
                else if (first < second)
                    return -1;
            }
            // If they are the same until max comparable length, compare thier
            // lengths to determine comparison
            if (maxLen == r || (len1 == len2)) {
                return 0;
            }
            else if (maxLen == len1) {
                return -1;
            }
            return 1;
        }
    }

    // Compares the two terms in lexicographic order,
    // but using only the first r characters of each query.
    public static Comparator<Term> byPrefixOrder(int r) {
        if (r < 0)
            throw new IllegalArgumentException("Invalid arguments");
        return new ByPrefixComparator(r);
    }

    // Compares the two terms in lexicographic order by query.
    public int compareTo(Term that) {
        return query.compareTo(that.query);
    }

    // Returns a string representation of this term in the following format:
    // the weight, followed by a tab, followed by the query.
    public String toString() {
        return (weight + "\t" + query);
    }

    // unit testing (required)
    public static void main(String[] args) {
        // Create an array of Term objects for testing
        Term[] terms = new Term[6];

        terms[0] = new Term("john", 5);
        terms[1] = new Term("mike", 3);
        terms[2] = new Term("terry", 7);
        terms[3] = new Term("micheal", 2);
        terms[4] = new Term("mark", 5);
        terms[5] = new Term("mikey", 6);

        // Test compareTo method
        StdOut.println("Compare 'john' to 'mike': " + terms[0].compareTo(terms[1]));

        // Test byReverseWeightOrder method
        StdOut.println("\nTesting byReverseWeightOrder method:");
        Comparator<Term> reverseWeightComparator = Term.byReverseWeightOrder();
        Arrays.sort(terms, reverseWeightComparator);
        StdOut.println("sorted by reverse weight order:");
        for (Term term : terms) {
            StdOut.println(term.toString());
        }

        // Test byPrefixOrder method
        int r = 3;
        Comparator<Term> prefixComparator = Term.byPrefixOrder(r);
        Arrays.sort(terms, prefixComparator);
        StdOut.println("sorted by first " + r + " characters:");
        for (Term term : terms) {
            StdOut.println(term.toString());
        }
    }


}
