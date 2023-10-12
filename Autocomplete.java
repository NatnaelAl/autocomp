import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Comparator;

public class Autocomplete {
    // List of all terms
    private Term[] terms;

    // Initializes the data structure from the given array of terms.
    public Autocomplete(Term[] terms) {
        // Check for null arguments
        if (terms == null)
            throw new IllegalArgumentException("Null argument");
        for (int i = 0; i < terms.length; i++) {
            if (terms[i] == null) {
                throw new IllegalArgumentException("Null argument");
            }
        }

        this.terms = new Term[terms.length];
        for (int i = 0; i < terms.length; i++) {
            this.terms[i] = terms[i];
        }

        // Sorts alphebetically
        Arrays.sort(this.terms);
    }

    // Returns all terms that start with the given prefix,
    // in descending order of weight.
    public Term[] allMatches(String prefix) {
        if (prefix == null)
            throw new IllegalArgumentException("Null argument");
        Comparator<Term> comparator2 = Term.byReverseWeightOrder();
        // Corner Case
        if (prefix.equals("")) {
            // This duplicates the array to be able to be returned without error
            Term[] allTerms = new Term[terms.length];
            for (int i = 0; i < terms.length; i++) {
                allTerms[i] = terms[i];
            }
            Arrays.sort(allTerms, comparator2);
            return allTerms;
        }

        // Turn the prefix to a term to allow for binary search
        // (arbitrary weight)
        Term compare = new Term(prefix, 1);

        // Comapre only the first r letters
        int r = prefix.length();
        Comparator<Term> comparator = Term.byPrefixOrder(r);
        int firstTndex = BinarySearchDeluxe.firstIndexOf(terms, compare, comparator);
        int lastIndex = BinarySearchDeluxe.lastIndexOf(terms, compare, comparator);

        // Case where ther is no index
        if (firstTndex == -1) {
            return new Term[0];
        }

        // Adding one will make the last index inclusive
        // Returns the sorted matches
        Term[] matches = Arrays.copyOfRange(terms, firstTndex, lastIndex + 1);
        Arrays.sort(matches, comparator2);
        return matches;

    }

    // Returns the number of terms that start with the given prefix.
    public int numberOfMatches(String prefix) {
        if (prefix == null)
            throw new IllegalArgumentException("Null argument");
        // Corner Case
        if (prefix.equals(""))
            return terms.length;

        // Turn the prefix to a term to allow for binary search
        // (arbitrary weight)
        Term compare = new Term(prefix, 1);

        // Comapre only the first r letters
        int r = prefix.length();
        Comparator<Term> comparator = Term.byPrefixOrder(r);
        int firstTndex = BinarySearchDeluxe.firstIndexOf(terms, compare, comparator);
        int lastIndex = BinarySearchDeluxe.lastIndexOf(terms, compare, comparator);

        // Corner case where there could be 0 or only 1 element
        if (firstTndex == -1) {
            return 0;
        }
        else if (lastIndex == -1) {
            return 1;
        }
        // Returns the number of values between first index and last index inclusive
        return lastIndex - firstTndex + 1;

    }

    // unit testing (required)
    public static void main(String[] args) {
        // Sample cliet written in program specifications
        // read in the terms from a file
        String filename = args[0];
        In in = new In(filename);
        int n = in.readInt();
        Term[] terms = new Term[n];
        for (int i = 0; i < n; i++) {
            long weight = in.readLong();           // read the next weight
            in.readChar();                         // scan past the tab
            String query = in.readLine();          // read the next query
            terms[i] = new Term(query, weight);    // construct the term
        }

        // read in queries from standard input and print the top k matching terms
        int k = Integer.parseInt(args[1]);
        Autocomplete autocomplete = new Autocomplete(terms);
        while (StdIn.hasNextLine()) {
            String prefix = StdIn.readLine();
            Term[] results = autocomplete.allMatches(prefix);
            StdOut.printf("%d matches\n", autocomplete.numberOfMatches(prefix));
            for (int i = 0; i < Math.min(k, results.length); i++)
                StdOut.println(results[i]);
        }
    }

}
