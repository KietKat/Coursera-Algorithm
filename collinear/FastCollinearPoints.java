/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private final ArrayList<LineSegment> lineSegments = new ArrayList<>();

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
        analyzeSegment(points.clone());
    }

    public int numberOfSegments() {
        return lineSegments.size();
    }

    public LineSegment[] segments() {
        return lineSegments.toArray(new LineSegment[lineSegments.size()]);
    }

    /* @param points is a clone of the imp
        for each points[i] (Til points.length-3 because beyond that, no combination satisfy)
            sort(points) to get it in right order
            - critical bc we need this to determine an order of points from smallest to biggest
            - no duplicate or subsegment
            sort by slope of each points[i]
            for loop (2 pointer DP) start & end:
                increment end until slope(points[i]) to slope(points[end]) is not the same
                if (end-start >=3 and points[start] > points[end])
                    we have a line segment!
                update start = end to find new lineSegment :)
    */
    private void analyzeSegment(Point[] points) {
        for (int i = 0; i < points.length - 3; i++) {
            Arrays.sort(points);
            Arrays.sort(points, points[i].slopeOrder());

            for (int start = 1, end = 2; end < points.length; end++) {
                while (end < points.length &&
                        Double.compare(points[i].slopeTo(points[start]),
                                       points[i].slopeTo(points[end])) == 0) {
                    end++;
                }
                if (end - start >= 3 && points[start].compareTo(points[i]) > 0) {
                    lineSegments.add(new LineSegment(points[i], points[end - 1]));
                }
                start = end;
            }
        }

    }

    public static void main(String[] args) {


    }
}
