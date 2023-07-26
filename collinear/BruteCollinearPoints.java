/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 ******************************************************************************/

import edu.princeton.cs.algs4.StdOut;

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

        analyzeSegment(points);
    }

    public int numberOfSegments() {
        return seg;
    }       // the number of line segments

    private void analyzeSegment(Point[] points) {
        // max number of line segments of n points is n choose 4, or n*(n-1)/2
        LineSegment[] tmpSegments = new LineSegment[6];
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
        // check co-linearity
        Point[] arr1 = new Point[4];

        arr1[0] = new Point(0, 0);
        arr1[1] = new Point(1, 1);
        arr1[2] = new Point(2, 2);
        arr1[3] = new Point(3, 3);

        BruteCollinearPoints bf1 = new BruteCollinearPoints(arr1);
        LineSegment[] ls1 = bf1.segments();

        for (int i = 0; i < ls1.length; i++) StdOut.println(ls1[i].toString());

        // check non co-linearity
        Point[] arr2 = new Point[4];

        arr2[0] = new Point(0, 9);
        arr2[1] = new Point(1, 1);
        arr2[2] = new Point(4, 2);
        arr2[3] = new Point(-9, 3);

        BruteCollinearPoints bf2 = new BruteCollinearPoints(arr2);
        LineSegment[] ls2 = bf2.segments();

        for (int i = 0; i < ls2.length; i++) StdOut.println(ls2[i].toString());

        // check exception
        Point[] arr3 = new Point[4];
        arr3[0] = new Point(0, 0);
        arr3[1] = new Point(2, 2);
        arr3[2] = new Point(2, 2);
        arr3[3] = new Point(3, 3);
        BruteCollinearPoints bf3 = new BruteCollinearPoints(arr3);
    }
}
