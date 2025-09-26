package com.coffeesi.util;

import java.util.Random;
import com.coffeesi.metrics.Metrics;;

public class Utils {
    public static int partition(int[] arr, int l, int r, Metrics metrics) {
        int pivot = arr[r];
        int i = l - 1;
        for (int j = l; j < r; j++) {
            metrics.addComparisons();
            if (arr[j] <= pivot) {
                swap(arr, ++i, j, metrics);
            }
        }
        swap(arr, i + 1, r, metrics);
        return i + 1;
    }

    public static void swap(int[] arr, int i, int j, Metrics metrics) {
        if (i != j) {
            int temp = arr[i];
            metrics.addAllocations();
            arr[i] = arr[j];
            metrics.addAllocations();
            arr[j] = temp;
            metrics.addAllocations();
        }
    }

    public static void shuffle(int[] arr, Metrics metrics) {
        Random rand = new Random();
        for (int i = arr.length - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            swap(arr, i, j, metrics);
        }
    }

    public static void guardNotNull(int[] arr) {
        if (arr == null)
            throw new IllegalArgumentException("array is null");
    }
}
