package com.coffeesi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ClosestPair {
    public static class Point {
        public int x;
        public int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private static double dist(Point p1, Point p2) {
        return Math.sqrt(Math.pow(p1.x - p2.x, 2) + 
            Math.pow(p1.y - p2.y, 2));
    }

    public static double closest(Point[] pts) {
        if (pts.length < 2) {
            throw new IllegalArgumentException("not enough points");
        }
        Arrays.sort(pts, Comparator.comparingDouble(p -> p.x));
        return rec(pts, 0, pts.length);
    }

    private static double rec(Point[] pts, int l, int r) {
        if (r - l <= 3) {
            double d = Double.POSITIVE_INFINITY;
            for (int i = l; i < r; i++)
                for (int j = i + 1; j < r; j++)
                    d = Math.min(d, dist(pts[i], pts[j]));
            Arrays.sort(pts, l, r, Comparator.comparingDouble(p -> p.y));
            return d;
        }

        int m = (l + r) / 2;
        double mid = pts[m].x;
        double d = Math.min(rec(pts, l, m), rec(pts, m, r));
        Point[] tmp = new Point[r - l];

        merge(pts, l, m, r, tmp);

        for (int i = l; i < r; i++)
            pts[i] = tmp[i - l];
        List<Point> strip = new ArrayList<>();
        for (int i = l; i < r; i++)
            if (Math.abs(pts[i].x - mid) < d)
                strip.add(pts[i]);
        for (int i = 0; i < strip.size(); i++) {
            for (int j = i + 1; j < strip.size() && strip.get(j).y - strip.get(i).y < d; j++)
                d = Math.min(d, dist(strip.get(i), strip.get(j)));
        }
        return d;
    }

    private static void merge(Point[] a, int l, int m, int r, Point[] tmp) {
        int i = l, j = m, k = 0;
        while (i < m && j < r)
            tmp[k++] = a[i].y < a[j].y ? a[i++] : a[j++];
        while (i < m)
            tmp[k++] = a[i++];
        while (j < r)
            tmp[k++] = a[j++];
    }

    public static void main(String[] args) {
        Point[] pts = { new Point(2, 3), 
                        new Point(12, 30), 
                        new Point(40, 50),
                        new Point(5, 1), 
                        new Point(12, 10), 
                        new Point(3, 4)
                    };
        System.out.println(closest(pts));
    }
}