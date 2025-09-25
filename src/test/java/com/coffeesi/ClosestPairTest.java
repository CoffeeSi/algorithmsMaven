package com.coffeesi;

import static org.junit.Assert.assertEquals;

import java.util.Random;
import org.junit.*;

import com.coffeesi.ClosestPair.Point;

public class ClosestPairTest {
    private double bruteForceClosest(ClosestPair.Point[] pts) {
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
    @Test
    public void testSimpleCase() {
        Point[] points = { 
            new Point(2, 3), 
            new Point(12, 30), 
            new Point(40, 50),
            new Point(5, 1), 
            new Point(12, 10), 
            new Point(3, 4)
        };

        double result = ClosestPair.closest(points);
        assertEquals(Math.hypot(2 - 3, 3 - 4), result, 1e-6);
    }

        @Test
    public void testTwoPoints() {
        Point[] pts = {
            new Point(0, 0),
            new Point(3, 4)
        };
        double result = ClosestPair.closest(pts);
        assertEquals(5.0, result, 1e-9);
    }

    @Test
    public void testIdenticalPoints() {
        Point[] pts = {
            new Point(1, 1),
            new Point(1, 1),
            new Point(5, 5)
        };
        double result = ClosestPair.closest(pts);
        assertEquals(0.0, result, 1e-9);
    }

    @Test
    public void testGridPoints() {
        Point[] pts = {
            new Point(0, 0),
            new Point(0, 1),
            new Point(1, 0),
            new Point(1, 1)
        };
        double result = ClosestPair.closest(pts);
        assertEquals(1.0, result, 1e-9);
    }

    @Test
    public void testKnownCase() {
        Point[] pts = {
            new Point(2, 3),
            new Point(12, 30),
            new Point(40, 50),
            new Point(5, 1),
            new Point(12, 10),
            new Point(3, 4)
        };
        double result = ClosestPair.closest(pts);
        assertEquals(Math.sqrt(2), result, 1e-9);
    }

    @Test
    public void test100RandomPoints() {
        Random rand = new Random(42);
        Point[] pts = new Point[100];
        for (int i = 0; i < pts.length; i++) {
            int x = rand.nextInt(1000);
            int y = rand.nextInt(1000);
            pts[i] = new Point(x, y);
        }

        double result = ClosestPair.closest(pts);
        double bruteForce = bruteForceClosest(pts);

        assertEquals(bruteForce, result, 1e-9);
    }
}
