package com.for_comprehension.function.l7_completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

class L17_CompletableFutureTraps {

    record Example1() {
        public static void main(String[] args) {
            ExecutorService e = Executors.newFixedThreadPool(2);
            CompletableFuture.supplyAsync(() -> 1, e)
              .thenApply(i -> log(i + 1))
              .thenApply(i -> log(i + 1))
              .thenApply(i -> log(i + 1))
              .thenApply(i -> log(i + 1))
              .thenApply(i -> log(i + 1))
              .thenApply(i -> log(i + 1))
              .thenAccept(System.out::println);

            e.shutdown();
        }
    }

    record Example2() {
        public static void main(String[] args) {
            ExecutorService e = Executors.newFixedThreadPool(2);
            CompletableFuture.supplyAsync(() -> process(1), e)
              .thenApplyAsync(i -> log(i + 1))
              .thenApplyAsync(i -> log(i + 1))
              .thenApplyAsync(i -> log(i + 1))
              .thenApplyAsync(i -> log(i + 1))
              .thenApplyAsync(i -> log(i + 1))
              .thenApplyAsync(i -> log(i + 1))
              .thenAccept(System.out::println)
              .join();

            e.shutdown();
        }
    }

    record Example3() {
        public static void main(String[] args) {
            ExecutorService e = Executors.newFixedThreadPool(2);
            CompletableFuture.supplyAsync(() -> process(1), e)
              .thenApplyAsync(i -> log(i + 1), e)
              .thenApplyAsync(i -> log(i + 1), e)
              .thenApplyAsync(i -> log(i + 1), e)
              .thenApplyAsync(i -> log(i + 1), e)
              .thenApplyAsync(i -> log(i + 1), e)
              .thenApplyAsync(i -> log(i + 1), e)
              .thenAccept(System.out::println)
              .join();

            e.shutdown();
        }
    }

    record Example4() {
        public static void main(String[] args) {
            ExecutorService e = Executors.newFixedThreadPool(2);
            CompletableFuture.supplyAsync(() -> process(1), e)
              .thenApplyAsync(i -> i + 1)
              .join();

            e.shutdown();
        }
    }

    record Example5() {
        public static void main(String[] args) {
            ExecutorService e = Executors.newVirtualThreadPerTaskExecutor();
            CompletableFuture.supplyAsync(() -> process(1), e)
              .thenApplyAsync(i -> log(i + 1), e)
              .thenApplyAsync(i -> log(i + 1), e)
              .thenApplyAsync(i -> log(i + 1), e)
              .thenApplyAsync(i -> log(i + 1), e)
              .thenApplyAsync(i -> log(i + 1), e)
              .thenApplyAsync(i -> log(i + 1), e)
              .thenAccept(System.out::println)
              .join();

            e.shutdown();
        }
    }

    static <T> T log(T i) {
        System.out.println("Logging " + i + " on " + Thread.currentThread());
        return i;
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
