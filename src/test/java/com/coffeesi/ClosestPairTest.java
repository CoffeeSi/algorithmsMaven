package com.coffeesi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Random;
import org.junit.*;

import com.coffeesi.ClosestPair.Point;

public class ClosestPairTest {
    
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
        double bruteForce = ClosestPair.bruteForce(pts);

        assertEquals(bruteForce, result, 1e-9);
    }

    @Test
    public void testClosestPairSmallN() {
        Random rand = new Random();
        for (int n = 10; n <= 200; n += 50) {
            ClosestPair.Point[] pts = new ClosestPair.Point[n];
            for (int i = 0; i < n; i++) {
                pts[i] = new ClosestPair.Point(rand.nextInt(1000), rand.nextInt(1000));
            }

            double fast = ClosestPair.closest(pts);
            double brute = ClosestPair.bruteForce(pts);

            assertEquals(brute, fast, 1e-6);
        }
    }

    @Test
    public void testClosestPairLargeN() {
        int n = 100000;
        Random rand = new Random();
        ClosestPair.Point[] pts = new ClosestPair.Point[n];
        for (int i = 0; i < n; i++) {
            pts[i] = new ClosestPair.Point(rand.nextInt(1000000), rand.nextInt(1_000_000));
        }

        double result = ClosestPair.closest(pts);
        assertTrue(result >= 0);
    }
}
