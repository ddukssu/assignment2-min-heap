package cli;

import algorithms.MinHeap;
import java.util.Random;

public class BenchmarkRunner {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java BenchmarkRunner <size> [decrease|merge|all]");
            return;
        }

        int n = Integer.parseInt(args[0]);
        String mode = args.length > 1 ? args[1] : "all";

        MinHeap heap = new MinHeap(n / 2);
        Random rand = new Random();

        long startTime = System.nanoTime();

        for (int i = 0; i < n; i++) {
            heap.insert(rand.nextInt(1000000));
        }

        long insertTime = System.nanoTime() - startTime;

        if (mode.equals("decrease") || mode.equals("all")) {
            startTime = System.nanoTime();
            for (int i = 0; i < n / 10; i++) {
                int index = rand.nextInt(heap.size()) + 1;
                int newKey = rand.nextInt(1000);
                try {
                    heap.decreaseKey(index, newKey);
                } catch (IllegalArgumentException e) {}
            }
            long decreaseTime = System.nanoTime() - startTime;
            System.out.println("Decrease-key time (ms): " + decreaseTime / 1_000_000.0);
        }

        if (mode.equals("merge") || mode.equals("all")) {
            MinHeap other = new MinHeap(n / 2);
            for (int i = 0; i < n / 2; i++) {
                other.insert(rand.nextInt(1000000));
            }
            startTime = System.nanoTime();
            heap.merge(other);
            long mergeTime = System.nanoTime() - startTime;
            System.out.println("Merge time (ms): " + mergeTime / 1_000_000.0);
        }

        if (mode.equals("all")) {
            startTime = System.nanoTime();
            while (!heap.isEmpty()) {
                heap.extractMin();
            }
            long extractTime = System.nanoTime() - startTime;
            System.out.println("Extract all time (ms): " + extractTime / 1_000_000.0);
        }

        System.out.println("Insert time (ms): " + insertTime / 1_000_000.0);
        System.out.println(heap.getTracker()); // Output metrics (extend to CSV)
    }
}