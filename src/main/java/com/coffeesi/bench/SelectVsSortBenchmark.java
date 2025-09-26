package com.coffeesi.bench;

import java.util.Arrays;
import java.util.Random;

import org.openjdk.jmh.annotations.*;

import com.coffeesi.metrics.Metrics;
import com.coffeesi.select.DeterministicSelect;

@State(Scope.Thread)
public class SelectVsSortBenchmark {

    @Param({"100", "1000", "10000"})
    private int n;

    private int[] randomArray;
    private int k;

    @Setup
    public void setup() {
        Random rand = new Random();
        randomArray = new int[n];
        for (int i = 0; i < n; i++) {
            randomArray[i] = rand.nextInt(2000000) - 1000000;
            k = n/2;
        }
    }

    @Benchmark
    public int select() {
        Metrics metrics = new Metrics("benchmark");
        int[] array = Arrays.copyOf(randomArray, n);
        return DeterministicSelect.select(array, k, metrics);
    }

    @Benchmark
    public int sort() {
        int[] array = Arrays.copyOf(randomArray, n);
        Arrays.sort(array);
        return array[k];
    }
}
