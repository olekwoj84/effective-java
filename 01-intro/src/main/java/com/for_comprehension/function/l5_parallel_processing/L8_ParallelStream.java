package com.for_comprehension.function.l5_parallel_processing;

import java.util.List;
import java.util.stream.Stream;

class L8_ParallelStream {
    record Example() {
        public static void main(String[] args) {
           timed(() -> {
               List<String> result = Stream.of("a", "b", "c", "d", "e", "f", "g", "h")
                 .parallel()
                 .map(i -> process(i))
                 .toList();
           });
        }

        private static <T> T process(T i) {
            try {
                System.out.printf("Processing %s%n", i);
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
