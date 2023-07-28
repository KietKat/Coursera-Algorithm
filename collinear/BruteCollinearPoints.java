/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 ******************************************************************************/

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class BruteCollinearPoints {
    private int seg;
    private LineSegment[] lineSeg;

    public BruteCollinearPoints(Point[] points) { // finds all line segments containing 4 points
        // null array
        if (points == null)
            throw new IllegalArgumentException("Null Array of Points");
        // null elements
        for (int i = 0; i < points.length; i++) {
            // null elements
            if (points[i] == null) throw new IllegalArgumentException("Null Elements");
            // repeated points
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0)
                    throw new IllegalArgumentException("Duplicate Elements");
            }
        }

        analyzeSegment(points.clone());
    }

    public int numberOfSegments() {
        return seg;
    }       // the number of line segments

    private void analyzeSegment(Point[] points) {
        // max number of line segments of n points is n choose 4, or n*(n-1)/2
        LineSegment[] tmpSegments = new LineSegment[6];
        Arrays.sort(points);
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                for (int k = j + 1; k < points.length; k++) {
                    for (int m = k + 1; m < points.length; m++) {
                        Point p = points[i];
                        Point q = points[j];
                        Point r = points[k];
                        Point s = points[m];
                        if (Double.compare(p.slopeTo(q), p.slopeTo(r)) == 0
                                && Double.compare(p.slopeTo(q), p.slopeTo(s)) == 0) {
                            tmpSegments[this.seg++] = new LineSegment(p, s);
                        }
                    }
                }
            }
        }
        this.lineSeg = new LineSegment[seg];
        for (int i = 0; i < seg; i++) {
            this.lineSeg[i] = tmpSegments[i];
        }
    }

    public LineSegment[] segments() { // the line segment
        return lineSeg.clone();
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
