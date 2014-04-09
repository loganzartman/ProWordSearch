package prowordsearch;

import java.util.ArrayList;
import java.util.List;

public class WordSearchSolver {
	private WordSearch search;
    private int nCores;

	public WordSearchSolver(WordSearch search) {
        this.search = search;

        //determine number of cores to efficiently distribute workload
        nCores = Runtime.getRuntime().availableProcessors();
        nCores -= nCores%2; //only use an even number of cores for now
	}

	/**
	 * Search a WordSearch for the provided strings.
     * Note: CASE SENSITIVE!
     * @param words the words to look for
	 * @param listener a FoundListener which will perform actions when words are found
	 * @return the result of listener.isFound()
	 */
	public boolean performWordSearch(String[] words, FoundListener listener) {
		//divide wordsearch into smaller rectangular cells
		int height = nCores >= 2 ? 2 : 1;
		int width  = nCores / height;
		int cellWidth  = search.width() / width;
		int cellHeight = search.height() / height;

		int i = 0;
		Thread[] threads = new Thread[width*height];

		//start a thread for each cell
		for (int w = 0; w < width; w++) {
			for (int h = 0; h < height; h++) {
				WordSearchThread runnable = new WordSearchThread(
					search,
					words,
					w * cellWidth,
					h * cellHeight,
					cellWidth,
					cellHeight,
					listener
				);

				Thread thread = new Thread(runnable);

				threads[i++] = thread;
				thread.start();
			}
		}

		//wait for completion of threads
		for (Thread t:threads) {
			try {
				t.join();
			}
			catch (InterruptedException e) {
                e.printStackTrace();
			}
		}

		return listener.isFound();
	}

    /**
     * Search the WordSearch for a given word.
     * @param word the word to look for
     * @return whether the WordSearch contains this word
     */
    public boolean wordSearchContains(String word) {
        FoundAllListener listener = new FoundAllListener();
        return performWordSearch(new String[]{word}, listener);
    }

    /**
     * Search the WordSearch for ALL given words.
     * @param words the words to look for
     * @return whether the WordSearch contains ALL of the words
     */
    public boolean wordSearchContainsAll(String[] words) {
        FoundAllListener listener = new FoundAllListener();
        return performWordSearch(words, listener);
    }

    /**
     * Search the WordSearch for ANY of the given words.
     * @param words the words to look for
     * @return whether the WordSearch contains ANY of the words
     */
    public boolean wordSearchContainsAny(String[] words) {
        FoundAnyListener listener = new FoundAnyListener();
        return performWordSearch(words, listener);
    }

    /**
     * Determine which words are contained in the WordSearch
     * @param words the words to look for
     * @return an array containing the words that were found
     */
    public List<String> wordSearchContainsWhich(String[] words) {
        FoundAnyListener listener = new FoundAnyListener();
        performWordSearch(words, listener);
        return listener.getFoundWords();
    }

    public static interface FoundListener {
        public synchronized void foundWord(String word, boolean status);
        public boolean isFound();
    }

	public static class FoundAllListener implements FoundListener {
        private boolean found = true;
        private List<String> foundWords = new ArrayList<String>();

        public synchronized void foundWord(String word, boolean status) {
            found = found && status;
            if (status) {foundWords.add(word);}
        }

        public synchronized List<String> getFoundWords() {
            return foundWords;
        }

        public boolean isFound() {
            return found;
        }
    }

    public static class FoundAnyListener implements FoundListener {
        private boolean found = false;
        private List<String> foundWords = new ArrayList<String>();

        public synchronized void foundWord(String word, boolean status) {
            found = found || status;
            if (status) {foundWords.add(word);}
        }

        public synchronized List<String> getFoundWords() {
            return foundWords;
        }

        public boolean isFound() {
            return found;
        }
    }

    public int getNumThreads() {
        return nCores;
    }
}
