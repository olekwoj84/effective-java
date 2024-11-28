package com.for_comprehension.function.l6_future;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

class L16_FutureOperations {

    // wait for the first one
    record Example1() {
        public static void main(String[] args) {
            ExecutorService executorService = Executors.newFixedThreadPool(2);
            Future<Integer> f1 = executorService.submit(() -> process(1));
            Future<Integer> f2 = executorService.submit(() -> process(2));

            System.out.println("la la la...");

            while (!f1.isDone() && !f2.isDone()) {
//                System.out.println("are we done yet?");
                Thread.onSpinWait(); // x86 PAUSE
            }

            if (f1.isDone()) {
                System.out.println("f1.resultNow() = " + f1.resultNow());
            } else {
                System.out.println("f2.resultNow() = " + f2.resultNow());
            }

            executorService.shutdownNow();
        }
    }

    // wait for the first one
    record Example2() {
        public static void main(String[] args) {
            ExecutorService executorService = Executors.newFixedThreadPool(2);
            BlockingQueue<Integer> resultsQueue = new ArrayBlockingQueue<>(2);
            executorService.submit(() -> {
                Integer result = process(1);
                resultsQueue.add(result);
                return result;
            });
            executorService.submit(() -> {
                Integer result = process(2);
                resultsQueue.add(result);
                return result;
            });

            System.out.println("la la la...");

            try {
                Integer result = resultsQueue.take();
                System.out.println("result = " + result);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            executorService.shutdownNow();
        }
    }

    static <T> T process(T i) {
        try {
            System.out.println("Processing " + i + " on " + Thread.currentThread());
            Thread.sleep(ThreadLocalRandom.current().nextInt(1_000, 10_000));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return i;
    }

    static <T> T timed(Supplier<T> run) {
        long start = System.currentTimeMillis();
        T result = run.get();
        System.out.printf("Time taken: %dms%n", System.currentTimeMillis() - start);
        return result;
    }
}
