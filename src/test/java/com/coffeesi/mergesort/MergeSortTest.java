package com.coffeesi.mergesort;

import static org.junit.Assert.assertArrayEquals;

import java.util.Random;
import java.util.Arrays;
import org.junit.*;

import com.coffeesi.metrics.Metrics;

public class MergeSortTest {

    @Test
    public void testEmptyArray() {
        Metrics m = new Metrics("test");
        int[] actuals = {};
        MergeSort.sort(actuals, m);
        assertArrayEquals(new int[]{}, actuals);
    }

    @Test
    public void testSingleElement() {
        Metrics m = new Metrics("test");
        int[] actuals = {42};
        MergeSort.sort(actuals, m);
        assertArrayEquals(new int[]{42}, actuals);
    }

    @Test
    public void testSortedArray() {
        Metrics m = new Metrics("test");
        int[] actuals = {1, 2, 3, 4, 5};
        MergeSort.sort(actuals, m);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, actuals);
    }

    @Test
    public void testNegativeNumbers() {
        Metrics m = new Metrics("test");
        int[] actuals = {-20, 23, -12, 3, 0, -43, 43};
        int[] expecteds = Arrays.copyOf(actuals, actuals.length);
        Arrays.sort(expecteds);
        MergeSort.sort(actuals, m);
        assertArrayEquals(expecteds, actuals);
    }

    @Test
    public void test100randomNumbers() {
        Metrics m = new Metrics("test5");
        Random rand = new Random();
        int[] actuals = new int[100];
        for (int i = 0; i < actuals.length; i++) {
            actuals[i] = rand.nextInt(1000) - 500;
        }
        int[] expecteds = Arrays.copyOf(actuals, actuals.length);
        Arrays.sort(expecteds);

        MergeSort.sort(actuals, m);
        assertArrayEquals(expecteds, actuals);
    }
}
