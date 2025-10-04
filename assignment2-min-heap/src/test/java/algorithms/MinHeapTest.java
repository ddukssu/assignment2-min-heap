package algorithms;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MinHeapTest {
    private MinHeap heap;

    @BeforeEach
    void setUp() {
        heap = new MinHeap(10);
    }

    @Test
    void testInsertAndExtractMin() {
        heap.insert(5);
        heap.insert(3);
        heap.insert(8);
        heap.insert(1);
        assertEquals(1, heap.extractMin());
        assertEquals(3, heap.extractMin());
        assertEquals(5, heap.extractMin());
        assertEquals(8, heap.extractMin());
    }

    @Test
    void testDecreaseKey() {
        heap.insert(10);
        heap.insert(20);
        heap.insert(30);
        heap.decreaseKey(3, 5);
        assertEquals(5, heap.extractMin());
        assertEquals(10, heap.extractMin());
        assertEquals(20, heap.extractMin());
    }

    @Test
    void testMerge() {
        heap.insert(4);
        heap.insert(6);
        MinHeap other = new MinHeap(5);
        other.insert(1);
        other.insert(3);
        other.insert(5);
        heap.merge(other);
        assertEquals(1, heap.extractMin());
        assertEquals(3, heap.extractMin());
        assertEquals(4, heap.extractMin());
        assertEquals(5, heap.extractMin());
        assertEquals(6, heap.extractMin());
    }

    @Test
    void testEdgeCases() {
        assertTrue(heap.isEmpty());
        assertThrows(IllegalStateException.class, heap::extractMin);
        assertThrows(IllegalStateException.class, heap::getMin);

        heap.insert(42);
        assertEquals(42, heap.getMin());
        assertEquals(42, heap.extractMin());
        assertTrue(heap.isEmpty());

        heap.insert(5);
        heap.insert(5);
        heap.insert(5);
        assertEquals(5, heap.extractMin());
        assertEquals(5, heap.extractMin());
        assertEquals(5, heap.extractMin());

        heap.insert(10);
        assertThrows(IllegalArgumentException.class, () -> heap.decreaseKey(1, 20));
        assertThrows(IndexOutOfBoundsException.class, () -> heap.decreaseKey(0, 5));
        assertThrows(IndexOutOfBoundsException.class, () -> heap.decreaseKey(2, 5));
    }

    @Test
    void testResize() {
        for (int i = 0; i < 15; i++) { // Beyond initial 10
            heap.insert(i);
        }
        assertEquals(0, heap.getMin());
    }

    @Test
    void testMetrics() {
        heap.insert(5);
        heap.insert(3);
        heap.extractMin();
        PerformanceTracker tracker = heap.getTracker();
        assertTrue(tracker.getComparisons() > 0);
        assertTrue(tracker.getSwaps() > 0);
        assertTrue(tracker.getArrayAccesses() > 0);
    }
}