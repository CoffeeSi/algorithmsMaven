package com.coffeesi;

import java.util.Random;

public class QuickSort {
    public static void sort(int[] arr) {
        Utils.guardNotNull(arr);
        Utils.suffle(arr);
        quickSort(arr, 0, arr.length-1);
    }

    private static int partition(int[] arr, int l, int r) {
        Random rand = new Random();
        int pivotIndex = rand.nextInt(r - l + 1) + l;
        Utils.swap(arr, pivotIndex, r);
        return Utils.partition(arr, l, r);
    }

    private static void quickSort(int[] arr, int l, int r) {
        while (l < r) {
            int part = partition(arr, l, r);
            if (part - l < r - part) {
                quickSort(arr, l, part - 1);
                l = part + 1;
            } else {
                quickSort(arr, part + 1, r);
                r = part - 1;
            }
        }
    }
}
