package prowordsearch;

public class WordSearchThread implements Runnable {
	private WordSearch search;
	private String[] words;
	private int x,y,w,h;
	private WordSearchSolver.FoundListener listener;

	public WordSearchThread(WordSearch search, String[] words, int x, int y, int w, int h, WordSearchSolver.FoundListener listener) {
		this.search = search;
		this.words = words;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
        this.listener = listener;
	}

	public void run() {
		for (int xx = x; xx<x+w; xx++) {
			for (int yy = y; yy<y+h; yy++) {
				for (int i = 0; i < words.length; i++) {
					if (check(words[i], 0, xx, yy)) {
						listener.foundWord(words[i],true);
						return;
					}
                    else {
                        listener.foundWord(words[i],false);
                    }
				}
			}
		}
	}

	public boolean check(String word, int ind, int xx, int yy) {
		if (ind<word.length()) {
			if ((xx >= 0) && (yy >= 0) && (xx < search.width()) && (yy < search.height()) && (word.charAt(ind) == search.charAt(xx, yy))) {
				return check(word,ind+1,xx+1,yy+0) ||
					   check(word,ind+1,xx-1,yy+0) ||
					   check(word,ind+1,xx+0,yy+1) ||
					   check(word,ind+1,xx+0,yy-1) ||
					   //diags:
					   check(word,ind+1,xx+1,yy+1) ||
					   check(word,ind+1,xx+1,yy-1) ||
					   check(word,ind+1,xx-1,yy+1) ||
					   check(word,ind+1,xx-1,yy-1)  ; //jfc
			}
			return false;
		}
		return true;
	}

	public static interface FoundCallback {
		public abstract void notify(boolean found);
	}
}