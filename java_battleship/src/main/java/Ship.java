package battleship;

public class Ship {
    private String name;
    private int length;
    private int[] rows;      // row positions
    private int[] cols;      // column positions
    private boolean[] hits;  // hit status for each segment

    public Ship(String name, int length) {
        this.name = name;
        this.length = length;
        this.rows = new int[length];
        this.cols = new int[length];
        this.hits = new boolean[length];
    }

    public String getName() {
        return name;
    }

    public int getLength() {
        return length;
    }

    public int[] getRows() {
        return rows;
    }

    public int[] getCols() {
        return cols;
    }

    public void setPosition(int index, int row, int col) {
        rows[index] = row;
        cols[index] = col;
    }

    public boolean registerHit(int row, int col) {
        for (int i = 0; i < length; i++) {
            if (rows[i] == row && cols[i] == col) {
                hits[i] = true;
                return true; // hit!
            }
        }
        return false; // miss
    }

    public boolean isSunk() {
        for (boolean hit : hits) {
            if (!hit) return false;
        }
        return true;
    }
}