/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class FastCollinearPoints {
    private int segmentCount;
    private LineSegment[] lineSegments;

    public FastCollinearPoints(Point[] points) { // finds all line segments
        // bestie check :)
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
        this.segmentCount = 0;
    }

    public int numberOfSegments() {
        return segmentCount;
    }

    public LineSegment[] segments() {
        return lineSegments.clone();
    }

    /*
        Create LineSegment[] totalSegment to store line segment
        For each point[i]:
            Create a clone[] for points, from points[i+1] to end
            Sort clone[] bySlope to point[i]
            currentSegmentSize is 2
            for j = 1 : clone.length:
                if slope([j-1] = j[j]
                    currentSegmentSize++
                else
                    if currentSize >= 4:
                        add to total segment
                    reset currentSegmentSize = 2
            if (currentSegmentSize >= 4)
                add to totalSegmentSize (startPoint,lastPoint)

            Add everything to this.lineSegment of size segmentCount;

    */
    private void analyzeSegment(Point[] points) {
        int arrSize = points.length;
        LineSegment[] totalSeg = new LineSegment[4 * arrSize];
        Arrays.sort(points);
        Point[] copyPoints;

        // O(n)
        for (int i = 0; i < arrSize; i++) {
            int copySize = arrSize - i - 1;
            Point startPoint = points[i];
            copyPoints = new Point[copySize];
            System.arraycopy(points, i + 1, copyPoints, 0, copySize);

            // O(NlgN)
            Arrays.sort(copyPoints, startPoint.slopeOrder());

            int segmentSize = 2;
            // O(N)
            for (int j = 1; j < copySize; j++) {
                if (Double.compare(startPoint.slopeTo(copyPoints[j]),
                                   startPoint.slopeTo(copyPoints[j - 1])) == 0) {
                    segmentSize++;
                }
                else {
                    if (segmentSize >= 4) {
                        totalSeg[this.segmentCount++] =
                                new LineSegment(startPoint, copyPoints[j - 1]);
                    }
                    segmentSize = 2;
                }
            }

            if (segmentSize >= 4) {
                totalSeg[this.segmentCount++] =
                        new LineSegment(startPoint, copyPoints[copyPoints.length - 1]);
            }
        }

        this.lineSegments = new LineSegment[this.segmentCount];
        for (int i = 0; i < this.segmentCount; i++) {
            lineSegments[i] = totalSeg[i];
        }
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
