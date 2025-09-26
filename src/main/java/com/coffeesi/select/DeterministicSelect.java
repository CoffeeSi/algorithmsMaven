package com.coffeesi.select;

import com.coffeesi.util.Utils;
import com.coffeesi.metrics.Metrics;


public class DeterministicSelect {
    public static int select(int[] arr, int k, Metrics metrics) {
        Utils.guardNotNull(arr);
        if (k < 0 || k >= arr.length)
            throw new IllegalArgumentException("key out of range");

        metrics.startTimer();
        int result = deterministicSelect(arr, 0, arr.length-1, k, metrics);
        metrics.stopTimer();
        metrics.writeToCSV("DeterministicSelect");
        return result;
    }

    private static int deterministicSelect(int[] arr, int left, int right, int k, Metrics metrics) {
        metrics.enterRec();
        int n = right - left + 1;
        if (n <= 5) {
            insertionSort(arr, left, right, metrics);
            metrics.exitRec();
            return arr[left + k];
        }
        
        int pivot = medianOfMedians(arr, left, right, metrics);
        int pivotIndex = partition(arr, left, right, pivot, metrics);
        int rank = pivotIndex - left;
        
        int result;
        if (k < rank)
            result = deterministicSelect(arr, left, pivotIndex - 1, k, metrics);
        else if (k > rank) 
            result = deterministicSelect(arr, pivotIndex + 1, right, k - rank - 1, metrics);
        else
            result = arr[pivotIndex];

        metrics.exitRec();
        return result;
    }
    
    private static int medianOfMedians(int[] arr, int left, int right, Metrics metrics) {
        int n = right - left + 1;
        if (n <= 5) {
            insertionSort(arr, left, right, metrics);
            return arr[left + n / 2];
        }
        
        int numGroups = (n + 4) / 5;
        int[] medians = new int[numGroups];
        metrics.addAllocations();
        
        for (int i = 0; i < numGroups; i++) {
            int groupLeft = left + i * 5;
            int groupRight = Math.min(groupLeft + 4, right);

            insertionSort(arr, groupLeft, groupRight, metrics);
            int medianIndex = groupLeft + (groupRight - groupLeft) / 2;
            medians[i] = arr[medianIndex];
        }
        
        return deterministicSelect(medians, 0, medians.length - 1, medians.length / 2, metrics);
    }

    private static int partition(int[] arr, int left, int right, int pivot, Metrics metrics) {
        for (int i = left; i <= right; i++) {
            metrics.addComparisons();
            if (arr[i] == pivot) {
                Utils.swap(arr, i, right, metrics);
                break;
            }
        }
        return Utils.partition(arr, left, right, metrics);
    }
    
    private static void insertionSort(int[] arr, int left, int right, Metrics metrics) {
        for (int i = left + 1; i <= right; i++) {
            int key = arr[i];
            int j = i - 1;
            
            while (j >= left) {
                metrics.addComparisons();
                if (arr[j] > key) { 
                    arr[j + 1] = arr[j];
                    j--;
                } else break;
            }
            arr[j + 1] = key;
        }
    }
}