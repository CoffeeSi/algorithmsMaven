package com.coffeesi.select;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.util.Arrays;
import java.util.Random;

import org.junit.*;

import com.coffeesi.metrics.Metrics;

public class DeterministicSelectTest {
    @Test
    public void testEmptyArray() {
        Metrics metrics = new Metrics("test");
        int[] array = {};
        assertThrows(IllegalArgumentException.class, 
                () -> DeterministicSelect.select(array, 0, metrics));
    }

    @Test
    public void testLargeKey() {
        Metrics metrics = new Metrics("test");
        metrics.setN(10);
        int[] array = {9, 8, 7, 6, 5, 4, 3, 2, 1, 0};
        assertThrows(IllegalArgumentException.class, 
                () -> DeterministicSelect.select(array, 11, metrics));
    }

    @Test
    public void testSingleElement() {
        Metrics metrics = new Metrics("test");
        metrics.setN(1);
        int[] array = {42};
        assertEquals(42, DeterministicSelect.select(array, 0, metrics));
    }

    @Test
    public void testArrayWithDuplicates() {
        Metrics metrics = new Metrics("test");
        metrics.setN(7);
        int[] arr = {5, 1, 5, 3, 5, 2, 5};
        assertEquals(1, DeterministicSelect.select(arr, 0, metrics));
        assertEquals(2, DeterministicSelect.select(arr, 1, metrics));
        assertEquals(3, DeterministicSelect.select(arr, 2, metrics));
        assertEquals(5, DeterministicSelect.select(arr, 3, metrics));
        assertEquals(5, DeterministicSelect.select(arr, 6, metrics));
    }

    @Test
    public void testNegativeNumbers() {
        Metrics metrics = new Metrics("test");
        metrics.setN(7);
        int[] array = {-20, 23, -12, 3, 0, -43, 43};
        assertEquals(-12, DeterministicSelect.select(array, 2, metrics));
    }

    @Test
    public void test100randomNumbers() {
        Metrics metrics = new Metrics("test");
        metrics.setN(100);
        Random rand = new Random();
        int[] array = new int[100];
        for (int i = 0; i < array.length; i++) {
            array[i] = rand.nextInt(1000) - 500;
        }
        int[] expecteds = Arrays.copyOf(array, array.length);
        Arrays.sort(expecteds);

        for (int i = 0; i < array.length; i++) {
            assertEquals(expecteds[i], DeterministicSelect.select(array, i, metrics));
        }
    }
}
