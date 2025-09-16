package com.coffeesi;

import static org.junit.Assert.assertArrayEquals;

import java.util.Random;
import java.util.Arrays;
import org.junit.*;

public class MergeSortTest {

    @Test
    public void testEmptyArray() {
        int[] actuals = {};
        MergeSort.sort(actuals);
        assertArrayEquals(new int[]{}, actuals);
    }

    @Test
    public void testSingleElement() {
        int[] actuals = {42};
        MergeSort.sort(actuals);
        assertArrayEquals(new int[]{42}, actuals);
    }

    @Test
    public void testSortedArray() {
        int[] actuals = {1, 2, 3, 4, 5};
        MergeSort.sort(actuals);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, actuals);
    }

    @Test
    public void testNegativeNumbers() {
        int[] actuals = {-20, 23, -12, 3, 0, -43, 43};
        int[] expecteds = Arrays.copyOf(actuals, actuals.length);
        MergeSort.sort(actuals);
    }

    @Test
    public void test100randomNumbers() {
        Random rand = new Random();
        int[] actuals = new int[100];
        for (int i = 0; i < actuals.length; i++) {
            actuals[i] = rand.nextInt(1000) - 500;
        }
        int[] expecteds = Arrays.copyOf(actuals, actuals.length);
        Arrays.sort(expecteds);

        MergeSort.sort(actuals);
        assertArrayEquals(expecteds, actuals);
    }
}
