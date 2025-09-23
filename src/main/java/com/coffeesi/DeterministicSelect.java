package com.coffeesi;

public class DeterministicSelect {
    public static int select(int[] arr, int k) {
        Utils.guardNotNull(arr);
        if (k < 0 || k >= arr.length)
            throw new IllegalArgumentException("key out of range");
        return deterministicSelect(arr, 0, arr.length-1, k);
    }

    private static int deterministicSelect(int[] arr, int left, int right, int k) {
        int n = right - left + 1;
        if (n <= 5) {
            insertionSort(arr, left, right);
            return arr[left + k];
        }
        
        int pivot = medianOfMedians(arr, left, right);
        int pivotIndex = partition(arr, left, right, pivot);
        int rank = pivotIndex - left;
        
        if (k < rank)
            return deterministicSelect(arr, left, pivotIndex - 1, k);
        if (k > rank) 
            return deterministicSelect(arr, pivotIndex + 1, right, k - rank - 1);
        else
            return arr[pivotIndex];

    }
    
    private static int medianOfMedians(int[] arr, int left, int right) {
        int n = right - left + 1;
        if (n <= 5) {
            insertionSort(arr, left, right);
            return arr[left + n / 2];
        }
        
        int numGroups = (n + 4) / 5;
        int[] medians = new int[numGroups];
        
        for (int i = 0; i < numGroups; i++) {
            int groupLeft = left + i * 5;
            int groupRight = Math.min(groupLeft + 4, right);

            insertionSort(arr, groupLeft, groupRight);
            int medianIndex = groupLeft + (groupRight - groupLeft) / 2;
            medians[i] = arr[medianIndex];
        }
        
        return deterministicSelect(medians, 0, medians.length - 1, medians.length / 2);
    }

    private static int partition(int[] arr, int left, int right, int pivot) {
        for (int i = left; i <= right; i++) {
            if (arr[i] == pivot) {
                Utils.swap(arr, i, right);
                break;
            }
        }
        return Utils.partition(arr, left, right);
    }
    
    private static void insertionSort(int[] arr, int left, int right) {
        for (int i = left + 1; i <= right; i++) {
            int key = arr[i];
            int j = i - 1;
            
            while (j >= left && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }
}