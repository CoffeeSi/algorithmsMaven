package com.coffeesi.metrics;

import java.io.FileWriter;
import java.io.IOException;

public class Metrics {
    private String filename;
    private long comparisons = 0;
    private long allocations = 0;
    private int currentDepth = 0;
    private int maxDepth = 0;
    private double startTime = 0;
    private double duration = 0;

    public Metrics(String filename) {
        this.filename = filename + ".csv";
        try (FileWriter writer = new FileWriter(this.filename)) {
            writer.write("algorithm,comparisons,allocations,depth,timeMs\n");
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize CSV file", e);
        }
    }

    public void addComparisons() {
        comparisons++;
    }

    public void addAllocations() {
        allocations++;
    }

    public void enterRec() {
        currentDepth++;
        if (currentDepth > maxDepth)
            maxDepth = currentDepth;
    }

    public void exitRec() {
        currentDepth--;
    }

    public void startTimer() {
        startTime = System.nanoTime();
    }

    public void stopTimer() {
        duration = System.nanoTime() - startTime;
    }

    public long getComparisons() {
        return comparisons;
    }

    public long getAllocations() {
        return allocations;
    }

    public int getMaxDepth() {
        return maxDepth;
    }

    public double getDurationMs() {
        return duration / 1000000;
    }

    public void reset() {
        comparisons = 0;
        allocations = 0;
        currentDepth = 0;
        maxDepth = 0;
        startTime = 0;
        duration = 0;
    }

    public void writeToCSV(String algorithm) {
        try (FileWriter writer = new FileWriter(filename,true)) {
            writer.write(String.format("%s,%d,%d,%d,%d%n", 
                algorithm, getComparisons(), getAllocations(), getMaxDepth(), getDurationMs()));
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
