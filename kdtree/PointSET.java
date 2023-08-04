/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class PointSET {
    private Set<Point2D> set;

    public PointSET() { // construct an empty set of points
        this.set = new TreeSet<>();
    }

    public boolean isEmpty() { // is the set empty?
        return this.set.isEmpty();
    }

    public int size() { // number of points in the set
        return this.set.size();
    }

    public void insert(Point2D p) { // add the point to the set (if it is not already in the set)
        if (p == null) throw new IllegalArgumentException("Null input");
        this.set.add(p);
    }

    public boolean contains(Point2D p) { // does the set contain point p?
        if (p == null) throw new IllegalArgumentException("Null input");
        return this.set.contains(p);
    }

    public void draw() { // draw all points to standard draw
        this.set.forEach(Point2D::draw);
    }

    /*
        Points in rect satisfy:
            point.x >= rect.xmin() and <= rect.xmax()
            point.y >= rect.ymin() and <= rect.ymax()
    */
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("Null input");
        List<Point2D> inside = new ArrayList<Point2D>();

        for (Point2D curr : this.set) {
            if (Double.compare(curr.x(), rect.xmax()) <= 0 &&
                    Double.compare(curr.x(), rect.xmin()) >= 0 &&
                    Double.compare(curr.y(), rect.ymax()) <= 0 &&
                    Double.compare(curr.y(), rect.ymin()) >= 0) {
                inside.add(curr);
            }
        }

        return inside;
    }

    // Brute-force: check the distance between with all points, and return the smallest one.
    public Point2D nearest(Point2D p) { // a nearest neighbor in the set to point p
        if (p == null) throw new IllegalArgumentException();
        if (this.set.isEmpty()) {
            return null;
        }

        Point2D goal = null;
        double minDistanceSquared = Double.POSITIVE_INFINITY;

        for (Point2D point : this.set) {
            double distanceSquared = p.distanceSquaredTo(point);
            if (Double.compare(distanceSquared, minDistanceSquared) < 0) {
                minDistanceSquared = distanceSquared;
                goal = point;
            }
        }
        return goal;
    }

    public static void main(String[] args) {

    }
}
