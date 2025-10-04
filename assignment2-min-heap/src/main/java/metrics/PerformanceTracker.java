package metrics;

public class PerformanceTracker {
    private long comparisons = 0;
    private long swaps = 0;
    private long arrayAccesses = 0;
    private long memoryAllocations = 0; // In bytes (approximate)

    public void incrementComparison() {
        comparisons++;
    }

    public void incrementSwap() {
        swaps++;
    }

    public void incrementArrayAccess() {
        arrayAccesses++;
    }

    public void incrementMemoryAllocation(long bytes) {
        memoryAllocations += bytes;
    }

    public long getComparisons() {
        return comparisons;
    }

    public long getSwaps() {
        return swaps;
    }

    public long getArrayAccesses() {
        return arrayAccesses;
    }

    public long getMemoryAllocations() {
        return memoryAllocations;
    }

    public void reset() {
        comparisons = 0;
        swaps = 0;
        arrayAccesses = 0;
        memoryAllocations = 0;
    }

    @Override
    public String toString() {
        return "PerformanceTracker{" +
                "comparisons=" + comparisons +
                ", swaps=" + swaps +
                ", arrayAccesses=" + arrayAccesses +
                ", memoryAllocations=" + memoryAllocations +
                '}';
    }
}