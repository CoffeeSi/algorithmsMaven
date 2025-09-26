# Assignment 1 - Design and Analysis of Algorithms

## Architecture notes

- Metrics tracking: implemented with the `Metrics` class.
- `comparisons` and `allocations` are counted `addComparisons()` and `addAllocations()` in (`partition`, `merge` etc).  
  - Recursion depth is controlled via `enterRec()` and `exitRec()` around recursive calls.  
  - The timer measures `System.nanoTime()` when each task is started and finished.   
- Metrics are written to CSV, which lets to build graphs (time vs n, depth vs n).
- Utilities (`Utils`) contain common operations (`partition`, `swap`, `shuffle`) so that metrics are calculated in the same way. 

## Recurrence analysis for each algorithm

### Mergesort

- The algorithm splits the array in half, recursively sorts the parts, and then merges them.
- T(n) = 2T(n/2) + O(n). According to the Master theorem: O(n log n).
- A reusable buffer is used to reduce memory allocations.
- For small n, it can switch to Insertion Sort (cut-off).


### Quicksort

- Splits the array into two parts with `partition`.
- Average case: T(n) = 2T(n/2) + O(n) = O(n log n) according to the Master theorem.
- Worst case: T(n) = T(n-1) + O(n) = O(n^2).
- Random selection of pivot or median-of-3 reduces the probability of the worst case.

### Deterministic Select

- Divide into groups of 5, take the median of the medians as the pivot.
- T(n) ≤ T(n/5) + T(7n/10) + O(n).
- According to Akra–Bazzi: O(n).
- The algorithm is linear, but with a relatively large constant factor.
- Recursion is called only in the necessary half, preferably in the smaller one.

### Closest Pair of points

- Points are sorted by x, then recursively divided in half.
- After that, the "strip" is checked in y-order.
- T(n) = 2T(n/2) + O(n) = O(n log n) according to Master's theorem. 

## Plots

### Time vs n (ms)

- MergeSort is the fastest; DeterministicSelect is second. QuickSort is third, and ClosestPair takes the longest time across nn. The slopes are consistent with near-linear growth for sort/closest-pair and linear for select, but constant factors dominate at these sizes.
![Chart 1](/img/chart1.png)

### Depth vs n

- The depth of the recursion grows roughly O(log⁡n) for MergeSort, DeterministicSelect, and ClosestPair. However, QuickSort's depth is noticeably higher, which suggests that there are more unbalanced partitions or instrumentation capturing additional recursion frames. The measured depths still increase very slowly, which is what we would expect in the average case.
![Chart 1](/img/chart2.png)


### Cache effects
- For small n (10–500), the time is very short and doesn't grow quickly because the whole array fits in the CPU cache, which is the fast memory.

The Garbage Collector, or GC, 

### Garbage Collector (GC)
- Garbage Collector is a computer program that manages memory in the computer.
- In MergeSort, temporary arrays (allocations) are created at each step. When n becomes large, the memory load increases, and GC pauses are possible. This makes the time less precise.

## Summary

### Alignment
- MergeSort, QuickSort (average case), and Closest Pair all grow at a rate of approximately n log n. Deterministic select grows linearly. This matches the theory.
- The recursion depth for all of them stays about log n.
- Allocation pattern: MergeSort uses one extra buffer, QuickSort is almost in-place, and Select and Closest Pair create temporary arrays as expected.

### Mismatches:
- Deterministic select is slower than sorting for the tested sizes because its pivot routine has significant overhead, even though it is linear in theory.
- The depth of QuickSort varies slightly because the splits are not perfectly even, but it is still far from the worst case.
- Closest Pair is slower due to the many sqrt calls and extra temporary arrays.
Counting swaps as "allocations" makes QuickSort appear to use more memory than it actually does.

### Conclusion 
- The shapes of the curves follow the recurrences. Differences in raw speed come from implementation overhead (pivot selection and sqrt), cache effects, and how metrics were counted. There is no complexity gap.

## Author

Yevgeniy Averyanov SE-2436