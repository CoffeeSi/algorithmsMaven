package com.coffeesi.closest;

import java.util.Arrays;
import java.util.Comparator;

import com.coffeesi.metrics.Metrics;

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

    public static double closest(Point[] pts, Metrics metrics) {
        if (pts.length < 2) {
            throw new IllegalArgumentException("not enough points");
        }
        Arrays.sort(pts, Comparator.comparingDouble(p -> p.x));
        metrics.startTimer();
        double result = rec(pts, 0, pts.length, metrics);
        metrics.stopTimer();
        metrics.writeToCSV("ClosestPair");
        return result;
    }

    private static double rec(Point[] pts, int l, int r, Metrics metrics) {
        metrics.enterRec();
        if (r - l <= 3) {
            double d = Double.POSITIVE_INFINITY;
            for (int i = l; i < r; i++)
                for (int j = i + 1; j < r; j++)
                    d = Math.min(d, dist(pts[i], pts[j]));
            Arrays.sort(pts, l, r, Comparator.comparingDouble(p -> p.y));
            metrics.exitRec();
            return d;
        }

        int m = (l + r) / 2;
        double mid = pts[m].x;
        double d = Math.min(rec(pts, l, m, metrics), rec(pts, m, r, metrics));
        Point[] tmp = new Point[r - l];
        metrics.addAllocations();

        merge(pts, l, m, r, tmp, metrics);

        for (int i = l; i < r; i++)
            pts[i] = tmp[i - l];
        Point[] strip = new Point[r-l];
        metrics.addAllocations();
        int size = 0;
        for (int i = l; i < r; i++)
            if (Math.abs(pts[i].x - mid) < d)
                strip[size++] = pts[i];
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size && strip[j].y - strip[i].y < d; j++) {
                metrics.addComparisons();
                d = Math.min(d, dist(strip[i], strip[j]));
            }
        }

        metrics.exitRec();
        return d;
    }

    private static void merge(Point[] a, int l, int m, int r, Point[] tmp, Metrics metrics) {
        int i = l, j = m, k = 0;
        while (i < m && j < r) {
            metrics.addComparisons();
            tmp[k++] = a[i].y < a[j].y ? a[i++] : a[j++];
        }
        while (i < m) tmp[k++] = a[i++];
        while (j < r) tmp[k++] = a[j++];
    }

    public static double bruteForce(Point[] pts) {
        double min = Double.POSITIVE_INFINITY;
        for (int i = 0; i < pts.length; i++) {
            for (int j = i + 1; j < pts.length; j++) {
                double dx = pts[i].x - pts[j].x;
                double dy = pts[i].y - pts[j].y;
                double d = Math.sqrt(dx * dx + dy * dy);
                if (d < min) {
                    min = d;
                }
            }
        }
        return min;
    }
}