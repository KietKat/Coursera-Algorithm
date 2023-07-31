/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Solver {
    private Board initialBoard;

    private List<Board> solution;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }

        this.solution = new ArrayList<>();
        this.initialBoard = initial;

        if (this.initialBoard.isGoal()) {
            solution.add(this.initialBoard);
        }
        else solve();
    }

    private void solve() {
        MinPQ<SearchNode> minPQ = new MinPQ<>(new ManhattanCompartor());
        MinPQ<SearchNode> twinMinPQ = new MinPQ<>(new ManhattanCompartor());

        /**
         * Two types of Boards:
         *     Board can achieve goal by itself
         *     Board can achieve goal by alternating its twin
         * Since only one of them can appear, "current" will trace if board is type 1,
         * twin will trace if board is type 2.
         * We use the same path for both board. If twin can achieve the goal first regardless,
         * we can conclude that current is not solvable :)
         */

        SearchNode current = new SearchNode(this.initialBoard, 0, null);
        minPQ.insert(current);

        SearchNode twin = new SearchNode(this.initialBoard.twin(), 0, null);
        twinMinPQ.insert(twin);

        while (true) {
            current = minPQ.delMin();
            if (current.board.isGoal()) {
                break; // Found a solution for the initial board
            }

            for (Board neighbor : current.board.neighbors()) {
                if (current.previous == null || !current.previous.board.equals(neighbor)) {
                    minPQ.insert(new SearchNode(neighbor, current.numOfMove + 1, current));
                }
            }

            twin = twinMinPQ.delMin();
            if (twin.board.isGoal()) {
                break; // Found a solution for the twin board (no solution for the initial board)
            }

            for (Board neighbor : twin.board.neighbors()) {
                if (twin.previous == null || !twin.previous.board.equals(neighbor)) {
                    twinMinPQ.insert(new SearchNode(neighbor, twin.numOfMove + 1, twin));
                }
            }
        }

        // adding the boards to solution by using previous
        if (current.board.isGoal() && !twin.board.isGoal()) {
            while (current.previous != null) {
                this.solution.add(current.board);
                current = current.previous;
            }
            Collections.reverse(this.solution);
        }
    }

    private class SearchNode {
        int numOfMove;
        Board board;
        SearchNode previous;
        int priority;

        SearchNode(Board board, int numOfMove, SearchNode previous) {
            this.numOfMove = numOfMove;
            this.board = board;
            this.previous = previous;
            // Control 2 : Caching the priority count
            this.priority = this.board.manhattan() + numOfMove;
        }
    }

    private class ManhattanCompartor implements Comparator<SearchNode> {
        public int compare(SearchNode o1, SearchNode o2) {
            return o1.priority - o2.priority;
        }
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (this.solution.size() == 0) return -1;
        return this.solution.size() - 1;
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        if (this.solution.isEmpty()) return false;
        return true;
    }

    // sequence of boards in the shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (this.solution.size() == 0) return null;

        List<Board> returnSolution = new ArrayList<>();
        for (int i = 0; i < this.solution.size(); i++) {
            returnSolution.add(this.solution.get(i));
        }

        return returnSolution;
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
