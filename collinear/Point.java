/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdDraw;

import java.util.Arrays;
import java.util.Comparator;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point


    /**
     * Initializes a new point.
     *
     * @param x the <em>x</em>-coordinate of the point
     * @param y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        if (that.y == y && that.x == x) {
            return Double.NEGATIVE_INFINITY;
        }
        else if (that.x == x) {
            return Double.POSITIVE_INFINITY;
        }
        else if (that.y == y) {
            return +0.0;
        }
        else {
            return (double) (that.y - y) / (double) (that.x - x);
        }
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     * point (x0 = x1 and y0 = y1);
     * a negative integer if this point is less than the argument
     * point; and a positive integer if this point is greater than the
     * argument point
     */
    public int compareTo(Point that) {
        /* YOUR CODE HERE */
        if (this.y < that.y) {
            return -1;
        }
        else if (this.y > that.y) {
            return 1;
        }
        else {
            if (this.x < that.x) {
                return -1;
            }
            else if (this.x > that.x) {
                return 1;
            }
        }
        return 0;
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        /* YOUR CODE HERE */
        return new BySlope();
    }

    private class BySlope implements Comparator<Point> {
        public int compare(Point p1, Point p2) {
            if (Double.compare(slopeTo(p1), slopeTo(p2)) < 0) {
                return -1;
            }
            else if (Double.compare(slopeTo(p1), slopeTo(p2)) > 0) {
                return 1;
            }
            else {
                return 0;
            }
        }
    }


    /**
     * Returns a string representation of this point.
     * This method is provided for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        Point[] arr = new Point[9];
        arr[1] = new Point(0, 0);
        arr[2] = new Point(5, 0);
        assert arr[1].slopeTo(arr[2]) == 0.0;

        arr[3] = new Point(-5, 0);
        assert arr[1].slopeTo(arr[3]) == 0.0;

        arr[4] = new Point(0, 5);
        assert arr[1].slopeTo(arr[4]) == Double.POSITIVE_INFINITY;

        arr[5] = new Point(0, -5);
        assert arr[1].slopeTo(arr[5]) == Double.POSITIVE_INFINITY;

        arr[6] = new Point(1, 1);
        assert arr[1].slopeTo(arr[6]) == 1.0;

        arr[7] = new Point(-1, -1);
        assert arr[1].slopeTo(arr[7]) == 1.0;

        arr[0] = new Point(19000, 10000);
        arr[8] = new Point(1234, 5678);
        System.out.println(arr[0].slopeTo(arr[8]));

        Arrays.sort(arr, arr[0].slopeOrder());
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
    }
}
