package algorithms;

import metrics.PerformanceTracker;

public class MinHeap {
    private int[] heap; // Heap array (1-based indexing)
    private int size;   // Current number of elements
    private int capacity; // Current capacity (excluding index 0)
    private final PerformanceTracker tracker;

    public MinHeap(int initialCapacity) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive");
        }
        this.capacity = initialCapacity;
        this.heap = new int[capacity + 1];
        this.size = 0;
        this.tracker = new PerformanceTracker();
    }

    public void insert(int key) {
        if (size == capacity) {
            resize();
        }
        size++;
        heap[size] = key;
        tracker.incrementArrayAccess(); // Write access
        heapifyUp(size);
    }

    public int extractMin() {
        if (isEmpty()) {
            throw new IllegalStateException("Heap is empty");
        }
        int min = heap[1];
        tracker.incrementArrayAccess(); // Read root
        heap[1] = heap[size];
        tracker.incrementArrayAccess(); // Write root
        size--;
        if (size > 0) {
            heapifyDown(1);
        }
        return min;
    }

    public void decreaseKey(int index, int newKey) {
        if (index < 1 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
        tracker.incrementArrayAccess(); // Read current key
        tracker.incrementComparison();
        if (newKey > heap[index]) {
            throw new IllegalArgumentException("New key must be smaller than current");
        }
        heap[index] = newKey;
        tracker.incrementArrayAccess(); // Write new key
        heapifyUp(index);
    }

    public void merge(MinHeap other) {
        if (other == null) {
            throw new IllegalArgumentException("Cannot merge null heap");
        }
        if (other.isEmpty()) {
            return;
        }
        int newSize = size + other.size;
        if (newSize > capacity) {
            capacity = Math.max(capacity * 2, newSize);
            int[] newHeap = new int[capacity + 1];
            System.arraycopy(heap, 0, newHeap, 0, heap.length);
            heap = newHeap;
            tracker.incrementMemoryAllocation((capacity + 1) * Integer.BYTES); // Track allocation
        }
        System.arraycopy(other.heap, 1, heap, size + 1, other.size);
        size = newSize;
        buildHeap(); // O(n) rebuild
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public int getMin() {
        if (isEmpty()) {
            throw new IllegalStateException("Heap is empty");
        }
        tracker.incrementArrayAccess();
        return heap[1];
    }

    public PerformanceTracker getTracker() {
        return tracker;
    }

    private void heapifyUp(int i) {
        while (i > 1) {
            int parent = parent(i);
            tracker.incrementArrayAccess(); // Read child
            tracker.incrementArrayAccess(); // Read parent
            tracker.incrementComparison();
            if (heap[i] < heap[parent]) {
                swap(i, parent);
                i = parent;
            } else {
                break;
            }
        }
    }

    private void heapifyDown(int i) {
        while (true) {
            int left = left(i);
            int right = right(i);
            int smallest = i;
            int smallestVal = heap[smallest];
            tracker.incrementArrayAccess(); //Read initial smallest
            
            if (left <= size) {
                int leftVal = heap[left];
                tracker.incrementArrayAccess(); // Read left
                tracker.incrementComparison();
                if (leftVal < smallestVal) {
                    smallest = left;
                    smallestVal = leftVal;
                }
            }
            if (right <= size) {
                int rightVal = heap[right];
                tracker.incrementArrayAccess(); // Read right
                tracker.incrementComparison();
                if (rightVal < smallestVal) {
                    smallest = right;
                    smallestVal = rightVal;
                }
            }
            if (smallest != i) {
                swap(i, smallest);
                i = smallest;
            } else {
                break;
            }
        }
    }

    private void buildHeap() {
        for (int i = size / 2; i >= 1; i--) {
            heapifyDown(i);
        }
    }

    private void resize() {
        capacity *= 2;
        int[] newHeap = new int[capacity + 1];
        System.arraycopy(heap, 0, newHeap, 0, size + 1);
        heap = newHeap;
        tracker.incrementMemoryAllocation((capacity + 1) * Integer.BYTES); // Approximate int size
    }

    private void swap(int i, int j) {
        int temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
        tracker.incrementSwap();
        tracker.incrementArrayAccess(); // Read i
        tracker.incrementArrayAccess(); // Read j
        tracker.incrementArrayAccess(); // Write i
        tracker.incrementArrayAccess(); // Write j
    }

    private int parent(int i) {
        return i / 2;
    }

    private int left(int i) {
        return 2 * i;
    }

    private int right(int i) {
        return 2 * i + 1;
    }
}
