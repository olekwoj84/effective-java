package com.for_comprehension.function.l7_completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

class L17_CompletableFuture {

    record Example1() {
        public static void main(String[] args) {
            ExecutorService e = Executors.newFixedThreadPool(2);
            CompletableFuture.supplyAsync(() -> process(1), e)
              .thenApply(i -> i + 1) // map(i -> i + 1)
              .thenAccept(System.out::println);

            System.out.println("la la la...");
        }
    }

    record Example2() {
        public static void main(String[] args) {
            ExecutorService e = Executors.newFixedThreadPool(2);
            CompletableFuture<Integer> cf1 = CompletableFuture.supplyAsync(() -> process(1), e);
            CompletableFuture<Integer> cf2 = CompletableFuture.supplyAsync(() -> process(2), e);

            cf1
              .thenApply(i -> i)
              .applyToEither(cf2, i -> i)
              .thenAccept(System.out::println);

            System.out.println("la la la...");

            e.shutdown();
        }
    }

    record Example3() {
        public static void main(String[] args) {
            ExecutorService e = Executors.newFixedThreadPool(2);
            CompletableFuture<Integer> cf1 = CompletableFuture.supplyAsync(() -> process(1), e);

            var result = cf1
              .thenApply(i -> i)
              .thenCompose(i -> CompletableFuture.supplyAsync(() -> process(i), e)) // flatMap(i -> cf2)
              .thenAccept(System.out::println);

            System.out.println("la la la...");

            result.join();
            e.shutdown();
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
