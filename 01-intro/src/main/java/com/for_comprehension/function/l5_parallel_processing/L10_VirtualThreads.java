package com.for_comprehension.function.l5_parallel_processing;

import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.pivovarit.collectors.ParallelCollectors.parallel;

class L10_VirtualThreads {

    // platform threads
    // 10    - 2022ms
    // 100   - 2026ms
    // 1000  - 2081ms
    // 2000  - 2121ms
    // 5000  - 2511ms
    // 9000  - 3198ms
    // 10_000  java.lang.OutOfMemoryError: unable to create native thread

    // virtual threads
    // 10         - 2016ms
    // 100        - 2019ms
    // 1000       - 2032ms
    // 2000       - 2031ms
    // 5000       - 2045ms
    // 9000       - 2084ms
    // 10_000     - 2080ms
    // 20_000     - 2151ms
    // 50_000     - 2350ms
    // 100_000    - 2589ms
    // 200_000    - 3489ms
    // 500_000    - 6564ms
    // 1000_000   - 11500ms
    // 2000_000   - 20860ms
    // 5000_000   - 36351ms

    record Example1() {
        public static void main(String[] args) throws Exception {
            try (var e = Executors.newCachedThreadPool()) {
                timed(() -> {
                    Stream.iterate(0, i -> i + 1)
                      .limit(5_000)
                      .collect(parallel(i -> process(i), Collectors.toList(), e, Integer.MAX_VALUE))
                      .join();
                });
            }
        }

        private static <T> T process(T i) {
            try {
//                System.out.printf("Processing %s on " + Thread.currentThread() + "\n", i);
                Thread.sleep(2_000);
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

    record Example2() {
        public static void main(String[] args) throws Exception {
            try (var e = Executors.newVirtualThreadPerTaskExecutor()) {
                timed(() -> {
                    Stream.iterate(0, i -> i + 1)
                      .limit(5_000_000)
                      .collect(parallel(i -> process(i), Collectors.toList(), e, Integer.MAX_VALUE))
                      .join();
                });
            }
        }

        private static <T> T process(T i) {
            try {
//                System.out.printf("Processing %s on " + Thread.currentThread() + "\n", i);
                Thread.sleep(2_000);
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
