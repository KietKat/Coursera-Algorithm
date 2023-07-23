/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    // perform independent trials on an n-by-n grid
    private int num;
    private final double confidenceConstant = 1.96;
    private double[] outcomes;


    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Invalid trials and grid size");
        }

        // store trial
        outcomes = new double[trials];
        num = trials;

        // run perc for i trials with size nxn
        for (int i = 0; i < trials; i++) {
            Percolation perc = new Percolation(n);

            while (!perc.percolates()) {
                int id1 = StdRandom.uniformInt(n) + 1;
                int id2 = StdRandom.uniformInt(n) + 1;

                if (!perc.isOpen(id1, id2)) {
                    perc.open(id1, id2);
                    outcomes[i]++;
                }
            }

            outcomes[i] = outcomes[i] / (n * n);
        }
    }

    public double mean() {
        return StdStats.mean(outcomes);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(outcomes);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return this.mean() -
                (confidenceConstant * this.stddev()) / Math.sqrt(num);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return this.mean() +
                (confidenceConstant * this.stddev()) / Math.sqrt(num);
    }

    public static void main(String[] args) {
        int size = StdIn.readInt();
        int trials = StdIn.readInt();
        PercolationStats percstat = new PercolationStats(size, trials);
        StdOut.println("mean                    = " + percstat.mean());
        StdOut.println("stddev                  = " + percstat.stddev());
        StdOut.println("95% confidence interval = [" + percstat.confidenceLo()
                               + ", " + percstat.confidenceHi() + "]");
    }
}
