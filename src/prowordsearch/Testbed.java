package prowordsearch;

import java.util.Arrays;

public class Testbed {
	public static void main(String[] args) {
        //Initialization
		WordSearch search = new WordSearch("APPLEXYPXLHJKEEDEGGLLXXCGFPDGOGNMYNTAHUUPUQDGBTSBTHIGHMSILKXLTHIS",8,8);
		String[] find = "APPLE AXE APEX CAT HEX EGG HAT COMPUTER GUM THIS TUG THIGH".split(" ");

        //Creating solver
		System.out.println(search);
		WordSearchSolver solver = new WordSearchSolver(search);
        System.out.println("The WordSearchSolver will use "+solver.getNumThreads()+" threads.\n");

        //Testing solver
        System.out.println("Does the word search contain \"PYP\"? "+solver.wordSearchContains("PYP"));
        System.out.println("Looking for these words: "+ Arrays.toString(find));
        System.out.println("Found these words: " + solver.wordSearchContainsWhich(find));

        //Benchmarking
        System.out.println("\nPerforming some benchmarking:");
        WordSearch benchSearch = new WordSearch(2000,2000);
        System.out.println("Made a random 2000x2000 WordSearch.\nNow searching for some words...");

        WordSearchSolver bencher = new WordSearchSolver(benchSearch);

        final int NLOOPS = 1000;
        long avg = 0;
        for (int i=0; i<NLOOPS; i++) {
            long start = System.currentTimeMillis();
            bencher.wordSearchContainsAll(find);
            long end = System.currentTimeMillis();
            avg += end-start;
        }

        System.out.println("Benchmark completed in an average of "+(((double)avg/NLOOPS))+" milliseconds.");
	}
}