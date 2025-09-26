package com.coffeesi.quicksort;

import java.util.Random;
import com.coffeesi.util.Utils;
import com.coffeesi.metrics.Metrics;

public class QuickSort {
    public static void sort(int[] arr, Metrics metrics) {
        Utils.guardNotNull(arr);
        Utils.shuffle(arr, metrics);
        metrics.startTimer();
        quickSort(arr, 0, arr.length-1, metrics);
        metrics.stopTimer();
        metrics.writeToCSV("QuickSort");
    }

    private static int partition(int[] arr, int l, int r, Metrics metrics) {
        Random rand = new Random();
        int pivotIndex = rand.nextInt(r - l + 1) + l;
        Utils.swap(arr, pivotIndex, r, metrics);
        return Utils.partition(arr, l, r, metrics);
    }

    private static void quickSort(int[] arr, int l, int r, Metrics metrics) {
        if (l >= r) return;
        metrics.enterRec();

        int part = partition(arr, l, r, metrics);

        if (part - l < r - part) {
            quickSort(arr, l, part - 1, metrics);
            quickSort(arr, part + 1, r, metrics);
        } else {
            quickSort(arr, part + 1, r, metrics);
            quickSort(arr, l, part - 1, metrics);
        }

        metrics.exitRec();
    }
}
