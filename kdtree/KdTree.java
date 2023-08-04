/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.List;

public class KdTree {
    private Node root;
    private int size;

    public KdTree() { // construct an empty set of points
        this.root = null;
        this.size = 0;
    }

    public boolean isEmpty() { // is the set empty?
        return this.root == null;
    }

    public int size() { // number of points in the set
        return this.size;
    }

    // compare x if is vertical, and y if is not vertical
    public void insert(Point2D p) { // add the point to the set (if it is not already in the set)
        if (p == null) throw new IllegalArgumentException("Null input");

        if (root == null || !contains(p)) {
            this.size++;
            this.root = insert(p, root, true);
        }
    }


    private Node insert(Point2D p, Node node, boolean isVertical) {
        if (node == null) {
            return new Node(null, null, p, p.x(), p.y(), isVertical);
        }

        if (node.isVertical) {
            if (Double.compare(node.xKey, p.x()) > 0) {
                // go left
                node.left = insert(p, node.left, !isVertical);
            }
            else {
                // go right
                node.right = insert(p, node.right, !isVertical);
            }
        }
        else {
            if (Double.compare(node.yKey, p.y()) > 0) {
                // go bottom
                node.left = insert(p, node.left, !isVertical);
            }
            else {
                // go top
                node.right = insert(p, node.right, !isVertical);
            }
        }

        return node;
    }

    public boolean contains(Point2D p) { // does the set contain point p?
        if (p == null) throw new IllegalArgumentException("Null input");

        return contains(p, root, true);
    }

    private boolean contains(Point2D p, Node curr, boolean isVertical) {
        if (curr == null) return false;

        if (Double.compare(curr.xKey, p.x()) == 0 &&
                Double.compare(curr.yKey, p.y()) == 0) {
            return true;
        }

        if (isVertical) {
            if (Double.compare(curr.xKey, p.x()) > 0)
                return contains(p, curr.left, !curr.isVertical);
            else
                return contains(p, curr.right, !curr.isVertical);
        }
        else {
            if (Double.compare(curr.yKey, p.y()) > 0)
                return contains(p, curr.left, !curr.isVertical);
            else
                return contains(p, curr.right, !curr.isVertical);
        }
    }

    public void draw() { // draw all points to standard draw
        draw(root, new RectHV(0, 0, 1, 1), root.isVertical);
    }

    private void draw(Node node, RectHV rect, boolean vertical) {
        if (node == null) {
            return;
        }

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.02);
        node.point.draw();

        boolean isVertical = !node.isVertical;
        StdDraw.setPenRadius();
        if (vertical) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(node.point.x(), rect.ymin(), node.point.x(), rect.ymax());
            draw(node.left, new RectHV(rect.xmin(), rect.ymin(), node.point.x(), rect.ymax()),
                 isVertical);
            draw(node.right, new RectHV(node.point.x(), rect.ymin(), rect.xmax(), rect.ymax()),
                 isVertical);
        }
        else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(rect.xmin(), node.point.y(), rect.xmax(), node.point.y());
            draw(node.left, new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), node.point.y()),
                 isVertical);
            draw(node.right, new RectHV(rect.xmin(), node.point.y(), rect.xmax(), rect.ymax()),
                 isVertical);
        }
    }

    /*
        Points in rect satisfy:
            point.x >= rect.xMin() and <= rect.xMax()
            point.y >= rect.yMin() and <= rect.yMax()
    */
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("Null input");

        List<Point2D> inside = new ArrayList<Point2D>();
        range(rect, inside, this.root);
        return inside;
    }

    private void range(RectHV rect, List<Point2D> inside, Node curr) {
        if (curr == null) return;

        if (rect.contains(curr.point)) {
            inside.add(curr.point);
        }

        if (curr.isVertical) { // check left right
            if (Double.compare(curr.xKey, rect.xmin()) >= 0) { // rect is left or intersects
                range(rect, inside, curr.left);
            }
            if (Double.compare(curr.xKey, rect.xmax()) <= 0) { // rect is right or intersects
                range(rect, inside, curr.right);
            }
        }
        else { // check top bot
            if (Double.compare(curr.yKey, rect.ymin()) >= 0) { // rect is below or intersects
                range(rect, inside, curr.left);
            }
            if (Double.compare(curr.yKey, rect.ymax()) <= 0) { // rect is above or intersects
                range(rect, inside, curr.right);
            }
        }
    }


    // pruning rules
    public Point2D nearest(Point2D p) { // a nearest neighbor in the set to point p
        if (p == null) throw new IllegalArgumentException("Null input");

        if (isEmpty()) {
            return null;
        }
        return nearest(p, root, root.point);
    }

    private Point2D nearest(Point2D p, Node node, Point2D mp) {
        Point2D min = mp;
        if (node == null) return min;

        if (Double.compare(p.distanceSquaredTo(mp), p.distanceSquaredTo(node.point)) > 0) {
            min = node.point;
        }

        if (node.isVertical) {
            if (p.x() < node.xKey) {
                min = nearest(p, node.left, min);
                if (node.right != null &&
                        Double.compare(p.distanceSquaredTo(min),
                                       (p.x() - node.xKey) * (p.x() - node.xKey)) > 0) {
                    min = nearest(p, node.right, min);
                }
            }
            else {
                min = nearest(p, node.right, min);
                if (node.left != null &&
                        Double.compare(p.distanceSquaredTo(min),
                                       (p.x() - node.xKey) * (p.x() - node.xKey)) > 0) {
                    min = nearest(p, node.left, min);
                }
            }
        }
        else {
            if (p.y() < node.yKey) {
                min = nearest(p, node.left, min);
                if (node.right != null &&
                        Double.compare(p.distanceSquaredTo(min),
                                       (p.y() - node.yKey) * (p.y() - node.yKey)) > 0) {
                    min = nearest(p, node.right, min);
                }
            }
            else {
                min = nearest(p, node.right, min);
                if (node.left != null &&
                        Double.compare(p.distanceSquaredTo(min),
                                       (p.y() - node.yKey) * (p.y() - node.yKey)) > 0) {
                    min = nearest(p, node.left, min);
                }
            }
        }

        return min;
    }

    private class Node {
        Node left;
        Node right;
        Point2D point;
        double xKey; // x-coordinate
        double yKey; // y-coordinate
        boolean isVertical;

        Node(Node left, Node right, Point2D point, double xKey, double yKey, boolean isVertical) {
            this.left = left;
            this.right = right;
            this.point = point;
            this.xKey = xKey;
            this.yKey = yKey;
            this.isVertical = isVertical;
        }
    }

    public static void main(String[] args) {

    }
}
