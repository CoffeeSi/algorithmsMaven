package com.coffeesi.quicksort;

import static org.junit.Assert.assertArrayEquals;

import java.util.Random;
import java.util.Arrays;
import org.junit.*;
import com.coffeesi.metrics.Metrics;

public class QuickSortTest {

    @Test
    public void testEmptyArray() {
        Metrics metrics = new Metrics("test");
        int[] actuals = {};
        QuickSort.sort(new int[]{}, metrics);
        assertArrayEquals(new int[]{}, actuals);
    }

    @Test
    public void testSingleElement() {
        Metrics metrics = new Metrics("test");
        metrics.setN(1);
        int[] actuals = {42};
        QuickSort.sort(actuals, metrics);
        assertArrayEquals(new int[]{42}, actuals);
    }

    @Test
    public void testSortedArray() {
        Metrics metrics = new Metrics("test");
        metrics.setN(5);
        int[] actuals = {1, 2, 3, 4, 5};
        QuickSort.sort(actuals, metrics);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, actuals);
    }

    @Test
    public void testNegativeNumbers() {
        Metrics metrics = new Metrics("test");
        metrics.setN(7);
        int[] actuals = {-20, 23, -12, 3, 0, -43, 43};
        QuickSort.sort(actuals, metrics);
        int[] expecteds = Arrays.copyOf(actuals, actuals.length);
        Arrays.sort(expecteds);
        assertArrayEquals(expecteds, actuals);
    }

    @Test
    public void test100randomNumbers() {
        Metrics metrics = new Metrics("test");
        metrics.setN(100);
        Random rand = new Random();
        int[] actuals = new int[100];
        for (int i = 0; i < actuals.length; i++) {
            actuals[i] = rand.nextInt(1000) - 500;
        }
        int[] expecteds = Arrays.copyOf(actuals, actuals.length);
        Arrays.sort(expecteds);

        QuickSort.sort(actuals, metrics);
        assertArrayEquals(expecteds, actuals);
    }
}