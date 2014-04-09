package prowordsearch;

public class WordSearch {
	private char[][] grid;
	private int width, height;

	/**
	 * Construct a WordSearch from a String.
	 * The string should consist of each row concatenated from top to bottom.
	 * No separators may be included.
	 * @param str the string to parse
	 * @param width the width of the grid
	 * @param height the height of the grid
	 */
	public WordSearch(String str, int width, int height) {
		str = str.toUpperCase(); //case insensitivity
		this.width = width;
		this.height = height;
		grid = new char[width][height];
		for (int x=0; x<width; x++) {
			for (int y=0; y<height; y++) {
				grid[x][y] = str.charAt((y*width)+x);
			}
		}
	}

	/**
	 * Construct a WordSearch from a String array.
	 * The array should be formatted like so:
	 * [
	 *   ["123"],
	 *   ["456"],
	 *   ["789"]
	 * ]
	 * Width and height are detected automatically.
	 * @param str the string array to parse
	 */
	public WordSearch(String[] str) {
		width = str[0].length();
		height = str.length;

		grid = new char[width][height];
		for (int y=0; y<height; y++) {
			str[y] = str[y].toUpperCase(); //case insensitivity
			for (int x=0; x<width; x++) {
				grid[x][y] = str[y].charAt(x);
			}
		}
	}

	/**
	 * Construct a WordSearch from an existing char array.
	 * Width and height are detected automatically.
	 * @param arr char array
	 */
	public WordSearch(char[][] arr) {
		width = arr.length;
		height = arr[0].length;
		grid = arr;
	}

    /**
     * Construct a WordSearch with random chars.
     * @param width the width of the grid
     * @param height the height of the grid
     */
    public WordSearch(int width, int height) {
        this.width = width;
        this.height = height;
        grid = new char[width][height];
        for (int x=0; x<width; x++) {
            for (int y=0; y<height; y++) {
                grid[x][y] = (char)(Math.random()*('Z'-'A'+1)+'A');
            }
        }
    }

	/* Accessors */
	public char[][] getGrid() {
		return grid;
	}
	public synchronized int width() {
		return width;
	}
	public synchronized int height() {
		return height;
	}
	public synchronized char charAt(int x, int y) {
		return grid[x][y];
	}

	@Override
	public String toString() {
		StringBuilder buffer = new StringBuilder();
		for (int x=0; x<width; x++) {
			for (int y=0; y<height; y++) {
				buffer.append(grid[x][y]+" ");
			}
			buffer.append("\n");
		}
		return buffer.toString();
	}
}