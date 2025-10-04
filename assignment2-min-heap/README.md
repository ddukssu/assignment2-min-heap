# Assignment 2: Min-Heap Implementation

## Overview
Implementation of a binary Min-Heap with decrease-key and merge operations. Tracks metrics for analysis.

## Usage
- Compile: `mvn compile`
- Test: `mvn test`
- Benchmark: `java -cp target/classes cli.BenchmarkRunner 10000 all`
- Outputs time and metrics to console (extend to CSV in docs/performance-plots).

## Complexity
- Time: Insert/Extract/Decrease-Key: O(log n), Merge: O(n)
- Space: O(n)

Edge cases handled: empty, single, duplicates.
