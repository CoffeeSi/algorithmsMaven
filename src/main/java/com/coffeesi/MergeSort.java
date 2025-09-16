package com.coffeesi;

import java.util.Arrays;
import java.util.Random;

public class MergeSort {
    private static final int CUTOFF_THRESHOLD = 16;

    public static void sort(int[] arr) {
        if (arr == null || arr.length < 2) return;
        int[] buffer = new int[arr.length];
        mergeSort(arr, buffer, 0, arr.length - 1);
    }

    private static void mergeSort(int[] arr, int[] buffer, int l, int r) {
        if (l >= r) return;
        if (r - l + 1 <= CUTOFF_THRESHOLD) {
            insertionSort(arr, l, r);
            return;
        }

        int m = (l + r)/2;
        mergeSort(arr, buffer, l, m);
        mergeSort(arr, buffer, m + 1, r);

        merge(arr, buffer, l, m, r);
    }

    private static void merge(int[] arr, int[] buffer, int l, int m, int r) {
        for (int i = l; i <= r; i++)
            buffer[i] = arr[i];

        int i = l;
        int j = m + 1;
        int k = l;

        while (i <= m && j <= r) {
            if (buffer[i] <= buffer[j])
                arr[k++] = buffer[i++];
            else
                arr[k++] = buffer[j++];
        }

        while (i <= m)
            arr[k++] = buffer[i++];
    }

    private static void insertionSort(int[] arr, int l, int r) {
        for (int i = l + 1; i <= r; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= l && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[++j] = key;
        }
    }
}
