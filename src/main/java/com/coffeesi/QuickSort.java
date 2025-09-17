package com.coffeesi;

import java.util.Random;

public class QuickSort {
    public static void sort(int[] arr) {
        if (arr == null || arr.length < 2) return;
        quickSort(arr, 0, arr.length-1);
    }

    private static int partition(int[] arr, int l, int r) {
        Random rand = new Random();
        
        int pivotIndex = rand.nextInt(r - l + 1) + l;
        int pivot = arr[pivotIndex];
        swap(arr, pivotIndex, r);

        int i = l - 1;
        for (int j = l; j < r; j++) {
            if (arr[j] <= pivot) {
                i++;
                swap(arr, i, j);     
            }
        }
        swap(arr, i+1, r);
        return i+1;
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

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
