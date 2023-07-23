import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[] state;
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF backtrackUF;
    private int size;
    private int opensite = 0;

    // initialize the grid- done
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Invalid Grid Size");
        }

        uf = new WeightedQuickUnionUF(n * n + 1);
        backtrackUF = new WeightedQuickUnionUF(n * n + 2);

        size = n;
        state = new boolean[n * n + 2];
        state[0] = true;

        for (int i = 1; i < n * n + 2; i++) {
            state[i] = false;
        }
    }

    // check if grid is open- done
    public boolean isOpen(int row, int col) {
        if (row <= 0 || row > size || col <= 0 || col > size) {
            throw new IllegalArgumentException("Invalid range");
        }
        return state[this.serializedCode(row, col)];
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row <= 0 || row > size || col <= 0 || col > size) {
            throw new IllegalArgumentException("Invalid range");
        }

        if (!isOpen(row, col)) {
            opensite++;
            state[this.serializedCode(row, col)] = true;

            // up,down, right, left
            if (row > 1 && isOpen(row - 1, col)) {
                uf.union(this.serializedCode(row, col), this.serializedCode(row - 1, col));
                backtrackUF.union(this.serializedCode(row, col), this.serializedCode(row - 1, col));
            }
            if (row < size && isOpen(row + 1, col)) {
                uf.union(this.serializedCode(row, col), this.serializedCode(row + 1, col));
                backtrackUF.union(this.serializedCode(row, col), this.serializedCode(row + 1, col));
            }
            if (col < size && isOpen(row, col + 1)) {
                uf.union(this.serializedCode(row, col), this.serializedCode(row, col + 1));
                backtrackUF.union(this.serializedCode(row, col), this.serializedCode(row, col + 1));
            }
            if (col > 1 && isOpen(row, col - 1)) {
                uf.union(this.serializedCode(row, col), this.serializedCode(row, col - 1));
                backtrackUF.union(this.serializedCode(row, col), this.serializedCode(row, col - 1));
            }

            // edge cases
            if (row == 1) {
                uf.union(this.serializedCode(row, col), 0);
                backtrackUF.union(this.serializedCode(row, col), 0);
            }

            if (row == size) {
                backtrackUF.union(this.serializedCode(row, col), size * size + 1);
            }
        }
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return opensite;
    }

    // that site is connected to top row-done
    public boolean isFull(int row, int col) {
        if (row <= 0 || row > size || col <= 0 || col > size) {
            throw new IllegalArgumentException("Invalid range");
        }
        return uf.find(this.serializedCode(row, col)) == uf.find(0);
    }

    // top row is connected to bottom row => check all sites in the bottom to see if any is full-done
    public boolean percolates() {
        return backtrackUF.find(0) == backtrackUF.find(size * size + 1);
    }

    private int serializedCode(int row, int col) {
        return (row - 1) * size + col;
    }

    // test client (optional)
    public static void main(String[] args) {

    }
}
