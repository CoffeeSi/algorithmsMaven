package com.coffeesi.mergesort;

import com.coffeesi.metrics.Metrics;
import com.coffeesi.util.Utils;

public class MergeSort {
    private static final int CUTOFF_THRESHOLD = 16;

    public static void sort(int[] arr, Metrics metrics) {
        Utils.guardNotNull(arr);
        metrics.addAllocations();
        int[] buffer = new int[arr.length];
        metrics.startTimer();
        mergeSort(arr, buffer, 0, arr.length - 1, metrics);
        metrics.stopTimer();
        metrics.writeToCSV("MergeSort");
    }

    private static void mergeSort(int[] arr, int[] buffer, int l, int r, Metrics metrics) {
        if (l >= r) return;
        metrics.enterRec();
        if (r - l + 1 <= CUTOFF_THRESHOLD) {
            insertionSort(arr, l, r, metrics);
            metrics.exitRec();
            return;
        }

        int m = (l + r)/2;
        mergeSort(arr, buffer, l, m, metrics);
        mergeSort(arr, buffer, m + 1, r, metrics);

        merge(arr, buffer, l, m, r, metrics);
        metrics.exitRec();
    }

    private static void merge(int[] arr, int[] buffer, int l, int m, int r, Metrics metrics) {
        for (int i = l; i <= r; i++)
            buffer[i] = arr[i];

        int i = l;
        int j = m + 1;
        int k = l;

        while (i <= m && j <= r) {
            metrics.addComparisons();
            if (buffer[i] <= buffer[j])
                arr[k++] = buffer[i++];
            else
                arr[k++] = buffer[j++];
        }

        while (i <= m)
            arr[k++] = buffer[i++];
    }

    private static void insertionSort(int[] arr, int l, int r, Metrics metrics) {
        for (int i = l + 1; i <= r; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= l) {
                metrics.addComparisons();
                if (arr[j] > key) {
                    arr[j + 1] = arr[j];
                    j--;
                } else
                    break;           
            }
            arr[++j] = key;
        }
    }
}
