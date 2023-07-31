/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.ArrayList;
import java.util.List;

public class Board {
    private final int[][] tiles;
    private final int size;

    private int zeroX;
    private int zeroY;
    private int manhattanDistance;
    private int hammingDistance;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.size = tiles[0].length;
        this.tiles = new int[this.size][this.size];

        // clone tiles
        for (int i = 0; i < this.size; i++) {
            this.tiles[i] = tiles[i].clone();
        }

        calculateDistance();
    }


    // string representation of this board
    public String toString() {
        StringBuilder strRes = new StringBuilder().append(this.size);

        for (int i = 0; i < this.size; i++) {
            StringBuilder row = new StringBuilder().append("\n");
            for (int j = 0; j < this.size; j++)
                row.append(" ").append(Integer.toString(tiles[i][j]));
            strRes.append(row);
        }
        return strRes.toString();
    }

    // board dimension n
    public int dimension() {
        return this.size;
    }


    private void calculateDistance() {
        this.hammingDistance = 0;
        this.manhattanDistance = 0;

        // calculate hamming, manhattan distance and location of empty tile
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                if (this.tiles[i][j] != 0) {
                    // manhattan distance
                    int correctX = (this.tiles[i][j] - 1) / this.size;
                    int correctY = this.tiles[i][j] - 1 - this.size * correctX;
                    this.manhattanDistance += Math.abs(correctX - i) + Math.abs(correctY - j);

                    //
                    if (this.tiles[i][j] != i * this.size + j + 1)
                        this.hammingDistance++;
                }
                else { // empty pile position
                    this.zeroX = i;
                    this.zeroY = j;
                }
            }
        }

    }

    // number of tiles out of place
    public int hamming() {
        return this.hammingDistance;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        return this.manhattanDistance;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                if (this.tiles[i][j] != i * this.size + j + 1
                        && this.tiles[i][j] != 0)
                    return false;
            }
        }
        return true;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) return true;

        if (y == null || y.getClass() != this.getClass()) return false;

        Board that = (Board) y;

        if (this.size != that.size) return false;
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                if (this.tiles[i][j] != that.tiles[i][j])
                    return false;
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        List<Board> returnNeighbors = new ArrayList<>();

        addNeighbors(zeroX - 1, zeroY, returnNeighbors);
        addNeighbors(zeroX + 1, zeroY, returnNeighbors);
        addNeighbors(zeroX, zeroY - 1, returnNeighbors);
        addNeighbors(zeroX, zeroY + 1, returnNeighbors);

        return returnNeighbors;
    }


    private void addNeighbors(int x, int y, List<Board> returnNeighbors) {
        if (x < this.size && x >= 0 && y < this.size && y >= 0) {
            returnNeighbors.add(swap(zeroX, x, zeroY, y));
        }
    }

    private Board swap(int fromX, int toX, int fromY, int toY) {
        int[][] clone = new int[this.size][this.size];

        for (int i = 0; i < this.size; i++) {
            clone[i] = this.tiles[i].clone();
        }

        // Swap
        int temp = clone[fromX][fromY];
        clone[fromX][fromY] = clone[toX][toY];
        clone[toX][toY] = temp;
        return new Board(clone);
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int swapToX;
        if (this.zeroX == 0) {
            swapToX = this.zeroX + 1;
        }
        else {
            swapToX = this.zeroX - 1;
        }

        int swapToY;
        if (this.zeroY == 0) {
            swapToY = this.zeroY + 1;
        }
        else {
            swapToY = this.zeroY - 1;
        }
        return swap(this.zeroX, swapToX, this.zeroY, swapToY);
    }

    public static void main(String[] args) {
        int[][] board = new int[3][3];
        board[0][0] = 8;
        board[0][1] = 1;
        board[0][2] = 3;
        board[1][0] = 4;
        board[1][1] = 0;
        board[1][2] = 2;
        board[2][0] = 7;
        board[2][1] = 6;
        board[2][2] = 5;

        Board brd = new Board(board);

        board[2][2] = 3;
        Board brd1 = new Board(board);
        System.out.println(brd.hamming());
        System.out.println(brd.manhattan());
        System.out.println(brd.toString());
        System.out.println(brd.isGoal());
        System.out.println(brd.equals(brd1));

        for (Board neighbor : brd.neighbors()) {
            System.out.println(neighbor.toString());
        }

        System.out.println(brd.twin().toString());
    }
}
