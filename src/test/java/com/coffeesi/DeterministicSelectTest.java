package com.coffeesi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.util.Arrays;
import java.util.Random;

import org.junit.*;

public class DeterministicSelectTest {
    @Test
    public void testEmptyArray() {
        int[] array = {};
        assertThrows(IllegalArgumentException.class, 
                () -> DeterministicSelect.select(array, 0));
    }

    @Test
    public void testLargeKey() {
        int[] array = {9, 8, 7, 6, 5, 4, 3, 2, 1, 0};
        assertThrows(IllegalArgumentException.class, 
                () -> DeterministicSelect.select(array, 11));
    }

    @Test
    public void testSingleElement() {
        int[] array = {42};
        assertEquals(42, DeterministicSelect.select(array, 0));
    }

    @Test
    public void testArrayWithDuplicates() {
        int[] arr = {5, 1, 5, 3, 5, 2, 5};
        assertEquals(1, DeterministicSelect.select(arr, 0));
        assertEquals(2, DeterministicSelect.select(arr, 1));
        assertEquals(3, DeterministicSelect.select(arr, 2));
        assertEquals(5, DeterministicSelect.select(arr, 3));
        assertEquals(5, DeterministicSelect.select(arr, 6));
    }

    @Test
    public void testNegativeNumbers() {
        int[] array = {-20, 23, -12, 3, 0, -43, 43};
        assertEquals(-12, DeterministicSelect.select(array, 2));
    }

    @Test
    public void test100randomNumbers() {
        Random rand = new Random();
        int[] array = new int[100];
        for (int i = 0; i < array.length; i++) {
            array[i] = rand.nextInt(1000) - 500;
        }
        int[] expecteds = Arrays.copyOf(array, array.length);
        Arrays.sort(expecteds);

        for (int i = 0; i < array.length; i++) {
            assertEquals(expecteds[i], DeterministicSelect.select(array, i));
        }
    }
}
