package com.for_comprehension.function.l5_parallel_processing;

import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.pivovarit.collectors.ParallelCollectors.parallel;

class L12_PollutedForkJoinVsVirtualThreads {

    record Example() {
        public static void main(String[] args) throws Exception {
            System.out.println("Runtime.getRuntime().availableProcessors() = " + Runtime.getRuntime().availableProcessors());

            clogSharedForkJoinPool();

            Thread.sleep(1000);

            try (var e = Executors.newVirtualThreadPerTaskExecutor()) {
                timed(() -> {
                    Stream.iterate(0, i -> i + 1)
                      .limit(10_000)
                      .collect(parallel(i -> process(i), Collectors.toList(), e, Integer.MAX_VALUE))
                      .join();
                });
            }
        }

        private static void clogSharedForkJoinPool() {
            Thread.ofPlatform().start(() -> {
                Stream.iterate(0, i -> i + 1)
                  .limit(Runtime.getRuntime().availableProcessors())
                  .parallel()
                  .forEach(i -> {
                      try {
                          Thread.sleep(Integer.MAX_VALUE);
                      } catch (InterruptedException e) {
                          throw new RuntimeException(e);
                      }
                  });
            });
        }

        private static <T> T process(T i) {
            try {
//                System.out.printf("Processing %s on " + Thread.currentThread() + "\n", i);
                Thread.sleep(200_000);
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
