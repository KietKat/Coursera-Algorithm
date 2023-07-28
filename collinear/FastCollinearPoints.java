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
        Point[] pointCopy = points.clone();

        for (int i = 0; i < pointCopy.length - 3; i++) {
            Arrays.sort(pointCopy);

            // Sort the points according to the slopes they makes with p.
            // Check if any 3 (or more) adjacent points in the sorted order
            // have equal slopes with respect to p. If so, these points,
            // together with p, are collinear.

            Arrays.sort(pointCopy, pointCopy[i].slopeOrder());

            for (int p = 0, first = 1, last = 2; last < pointCopy.length; last++) {
                // find last collinear to p point
                while (last < pointCopy.length
                        && Double.compare(pointCopy[p].slopeTo(pointCopy[first]),
                                          pointCopy[p].slopeTo(pointCopy[last])) == 0) {
                    last++;
                }
                // if found at least 3 elements, make segment if it's unique
                if (last - first >= 3 && pointCopy[p].compareTo(pointCopy[first]) < 0) {
                    lineSegments.add(new LineSegment(pointCopy[p], pointCopy[last - 1]));
                }
                // Try to find next
                first = last;
            }
        }
    }

    public static void main(String[] args) {


    }
}
