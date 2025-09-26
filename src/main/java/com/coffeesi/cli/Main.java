package com.coffeesi.cli;

import java.util.Random;

import com.coffeesi.closest.ClosestPair;
import com.coffeesi.closest.ClosestPair.Point;
import com.coffeesi.mergesort.MergeSort;
import com.coffeesi.metrics.Metrics;
import com.coffeesi.quicksort.QuickSort;
import com.coffeesi.select.DeterministicSelect;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Choose algorithm (mergesort/quicksort/select/closest)");
            System.out.println("java -jar assignment1-1.0.jar [algo] [n]");

        }
        String cmd = args[0].toLowerCase().strip();
        int amount = (args.length > 1) ? Integer.parseInt(args[1]) : 100;

        Metrics metrics = new Metrics("results");
        metrics.setN(amount);

        if (cmd.equals("mergesort")) {
            int[] arr = generateRandomNumbers(amount);
            MergeSort.sort(arr, metrics);
        }
        else if (cmd.equals("quicksort")) {
            int[] arr = generateRandomNumbers(amount);
            QuickSort.sort(arr, metrics);
        }
        else if (cmd.equals("select")) {
            int[] arr = generateRandomNumbers(amount);
            DeterministicSelect.select(arr, 0, metrics);
        }
        else if (cmd.equals("closest")) {
            Point[] points = generateRandomPoints(amount);
            ClosestPair.closest(points, metrics);
        }
        else
            System.out.println("Choose algorithm (mergesort/quicksort/select/closest)");
    }

    private static int[] generateRandomNumbers(int n) {
        Random rand = new Random();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++)
            arr[i] = rand.nextInt(10000);
        return arr;
    }

    private static Point[] generateRandomPoints(int n) {
        Random random = new Random();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            points[i] = new Point(random.nextInt(10000), random.nextInt(10000));
        }
        return points;
    }
}
