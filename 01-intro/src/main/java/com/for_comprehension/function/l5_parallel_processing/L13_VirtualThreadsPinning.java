package com.for_comprehension.function.l5_parallel_processing;

import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.pivovarit.collectors.ParallelCollectors.parallel;

class L13_VirtualThreadsPinning {

    record Example() {

        // https://openjdk.org/jeps/491 - "The reason for pinning"
        // virtual thread pinning
        public static void main(String[] args) throws Exception {
            try (var e = Executors.newVirtualThreadPerTaskExecutor()) {
                timed(() -> {
                    Stream.iterate(0, i -> i + 1)
                      .limit(100)
                      .collect(parallel(i -> process(i), Collectors.toList(), e, Integer.MAX_VALUE))
                      .join();
                });
            }
        }

        private static <T> T process(T i) {
            try {
                System.out.printf("Processing %s on " + Thread.currentThread() + "\n", i);
                synchronized (new Object()) {
                    Thread.sleep(1_000);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return i;
        }

        static void timed(Runnable run) {
            long start = System.currentTimeMillis();
            run.run();
            System.out.printf("Time taken: %dms%n", System.currentTimeMillis() - start);
        }
    }
}
