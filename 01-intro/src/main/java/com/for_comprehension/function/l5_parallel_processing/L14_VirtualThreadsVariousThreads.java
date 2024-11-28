package com.for_comprehension.function.l5_parallel_processing;

import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.pivovarit.collectors.ParallelCollectors.parallel;

class L14_VirtualThreadsVariousThreads {

    record Example1() {
        public static void main(String[] args) throws Exception {
            try (var e = Executors.newVirtualThreadPerTaskExecutor()) {
                timed(() -> {
                    Stream.iterate(0, i -> i + 1)
                      .limit(10)
                      .collect(parallel(i -> process(i), Collectors.toList(), e, Integer.MAX_VALUE))
                      .join();
                });
            }
        }

        private static <T> T process(T i) {
            try {
                System.out.printf("Started processing %s on " + Thread.currentThread() + "\n", i);
                Thread.sleep(2_000);
                System.out.printf("Finished processing %s on " + Thread.currentThread() + "\n", i);
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
