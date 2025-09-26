package com.coffeesi.closest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Random;
import org.junit.*;

import com.coffeesi.closest.ClosestPair.Point;
import com.coffeesi.metrics.Metrics;

public class ClosestPairTest {
    
    @Test
    public void testSimpleCase() {
        Metrics metrics = new Metrics("closest");
        Point[] points = { 
            new Point(2, 3), 
            new Point(12, 30), 
            new Point(40, 50),
            new Point(5, 1), 
            new Point(12, 10), 
            new Point(3, 4)
        };

        double result = ClosestPair.closest(points, metrics);
        assertEquals(Math.hypot(2 - 3, 3 - 4), result, 1e-6);
    }

    @Test
    public void testTwoPoints() {
        Metrics metrics = new Metrics("closest");
        Point[] pts = {
            new Point(0, 0),
            new Point(3, 4)
        };
        double result = ClosestPair.closest(pts, metrics);
        assertEquals(5.0, result, 1e-9);
    }

    @Test
    public void testIdenticalPoints() {
        Metrics metrics = new Metrics("closest");
        Point[] pts = {
            new Point(1, 1),
            new Point(1, 1),
            new Point(5, 5)
        };
        double result = ClosestPair.closest(pts, metrics);
        assertEquals(0.0, result, 1e-9);
    }

    @Test
    public void testGridPoints() {
        Metrics metrics = new Metrics("closest");
        Point[] pts = {
            new Point(0, 0),
            new Point(0, 1),
            new Point(1, 0),
            new Point(1, 1)
        };
        double result = ClosestPair.closest(pts, metrics);
        assertEquals(1.0, result, 1e-9);
    }

    @Test
    public void testKnownCase() {
        Metrics metrics = new Metrics("closest");
        Point[] pts = {
            new Point(2, 3),
            new Point(12, 30),
            new Point(40, 50),
            new Point(5, 1),
            new Point(12, 10),
            new Point(3, 4)
        };
        double result = ClosestPair.closest(pts, metrics);
        assertEquals(Math.sqrt(2), result, 1e-9);
    }

    @Test
    public void test100RandomPoints() {
        Metrics metrics = new Metrics("closest");
        Random rand = new Random(42);
        Point[] pts = new Point[100];
        for (int i = 0; i < pts.length; i++) {
            int x = rand.nextInt(1000);
            int y = rand.nextInt(1000);
            pts[i] = new Point(x, y);
        }

        double result = ClosestPair.closest(pts, metrics);
        double bruteForce = ClosestPair.bruteForce(pts);

        assertEquals(bruteForce, result, 1e-9);
    }

    @Test
    public void testClosestPairSmallN() {
        Metrics metrics = new Metrics("closest");
        Random rand = new Random();
        for (int n = 10; n <= 200; n += 50) {
            ClosestPair.Point[] pts = new ClosestPair.Point[n];
            for (int i = 0; i < n; i++) {
                pts[i] = new ClosestPair.Point(rand.nextInt(1000), rand.nextInt(1000));
            }

            double fast = ClosestPair.closest(pts, metrics);
            double brute = ClosestPair.bruteForce(pts);

            assertEquals(brute, fast, 1e-6);
        }
    }

    @Test
    public void testClosestPairLargeN() {
        Metrics metrics = new Metrics("closest");
        int n = 100000;
        Random rand = new Random();
        ClosestPair.Point[] pts = new ClosestPair.Point[n];
        for (int i = 0; i < n; i++) {
            pts[i] = new ClosestPair.Point(rand.nextInt(1000000), rand.nextInt(1_000_000));
        }

        double result = ClosestPair.closest(pts, metrics);
        assertTrue(result >= 0);
    }
}
