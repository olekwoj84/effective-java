package com.for_comprehension.function.l5_parallel_processing;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

class L9_CPUBoundProcessing {

    record Example1() {
        public static void main(String[] args) {
            Integer[] ints = Stream.generate(() -> ThreadLocalRandom.current().nextInt(1, 100))
              .limit(100_000_000)
              .toArray(Integer[]::new);

            System.out.println("Runtime.getRuntime().availableProcessors() = " + Runtime.getRuntime()
              .availableProcessors());

            System.out.println("Arrays.sort(ints)");
            timed(() -> Arrays.sort(ints));

            System.out.println("Arrays.parallelSort(ints)");
            timed(() -> Arrays.parallelSort(ints));
        }
    }

    static <T> T process(T i) {
        try {
            System.out.println("processing: " + i + " on " + Thread.currentThread().getName());
            Thread.sleep(1_000);
        } catch (InterruptedException e) {
        }

        return i;
    }

    static void timed(Runnable run) {
        long start = System.currentTimeMillis();
        run.run();
        System.out.printf("Time taken: %dms%n", System.currentTimeMillis() - start);
    }
}
