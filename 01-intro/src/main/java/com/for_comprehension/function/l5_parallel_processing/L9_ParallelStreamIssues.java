package com.for_comprehension.function.l5_parallel_processing;

import java.util.List;
import java.util.stream.Stream;

class L9_ParallelStreamIssues {

    record Example() {
        public static void main(String[] args) throws Exception {
            System.out.println("Runtime.getRuntime().availableProcessors() = " + Runtime.getRuntime().availableProcessors());

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

            Thread.sleep(1000);

            timed(() -> {
                List<Integer> result = Stream.iterate(0, i -> i + 1)
                  .limit(20)
                  .parallel()
                  .map(i -> process(i))
                  .toList();
            });
        }

        private static <T> T process(T i) {
            try {
                System.out.printf("Processing %s on " + Thread.currentThread().getName() + "\n", i);
                Thread.sleep(2000);
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
