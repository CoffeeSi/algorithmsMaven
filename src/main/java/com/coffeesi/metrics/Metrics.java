package com.coffeesi.metrics;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Metrics {
    private String filename;
    private long comparisons = 0;
    private long allocations = 0;
    private int currentDepth = 0;
    private int maxDepth = 0;
    private int n = 0;
    private double startTime = 0;
    private double duration = 0;

    public Metrics(String filename) {
        this.filename = filename + ".csv";

        File file = new File(this.filename);
        boolean writeHeader = (!file.exists() || file.length() == 0);

        try (FileWriter writer = new FileWriter(this.filename, true)) {
            if (writeHeader)
                writer.write("algorithm,n,comparisons,allocations,depth,timeMs\n");
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

    public void setN(int n) {
        this.n = n;
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

    public int getN() {
        return n;
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
            writer.write(String.format("%s,%d,%d,%d,%d,%.4f%n", 
                algorithm, getN(), getComparisons(), getAllocations(), getMaxDepth(), getDurationMs()));
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
