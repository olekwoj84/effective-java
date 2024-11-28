package com.for_comprehension.function.l6_future;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class L15_Future {
    record Example() {
        public static void main(String[] args) throws Exception {
            try (var e = Executors.newCachedThreadPool()) {
                Future<Integer> future = e.submit(() -> process(42));
                System.out.println("la la la...");

                Thread.sleep(1000);

                System.out.println("are we done yet?");
                if (future.isDone()) {
                    System.out.println("yes");
                    Integer i = future.resultNow();
                    System.out.println("got: " + i);
                } else {
                    System.out.println("no, la la la...");
                }

                try {
                    Integer i = future.get();
                    System.out.println("got finally: " + i);
                } catch (InterruptedException ex) {
                    System.out.println("interrupted, abandoning...");
                    future.cancel(true);
                } catch (ExecutionException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }

        private static <T> T process(T i) {
            try {
                System.out.printf("Processing %s on " + Thread.currentThread() + "\n", i);
                Thread.sleep(5_000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return i;
        }
    }
}
