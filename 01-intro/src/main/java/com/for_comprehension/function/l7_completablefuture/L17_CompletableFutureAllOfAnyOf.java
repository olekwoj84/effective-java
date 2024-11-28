package com.for_comprehension.function.l7_completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;

class L17_CompletableFutureAllOfAnyOf {

    record Example1() {
        public static void main(String[] args) {
            var cf1 = CompletableFuture.completedFuture(1);
            var cf2 = CompletableFuture.supplyAsync(() -> process(2));

            CompletableFuture<Void> allOf = CompletableFuture.allOf(cf1, cf2);
            allOf.join();

            System.out.println("cf1.resultNow() = " + cf1.resultNow());
            System.out.println("cf2.resultNow() = " + cf2.resultNow());
        }
    }

    record Example2() {
        public static void main(String[] args) {
            var cf1 = CompletableFuture.supplyAsync(() -> process(1));
            var cf2 = CompletableFuture.supplyAsync(() -> process(2));

            CompletableFuture<Void> allOf = CompletableFuture.allOf(cf1, cf2);
            allOf.join();

            System.out.println("cf1.resultNow() = " + cf1.resultNow());
            System.out.println("cf2.resultNow() = " + cf2.resultNow());
        }
    }

    // https://github.com/testcontainers/testcontainers-java/pull/5930
    record Example3() {
        public static void main(String[] args) {
            var cf1 = CompletableFuture.supplyAsync(() -> process(1));
            var cf2 = CompletableFuture.supplyAsync(() -> {
                process(2);
                throw new RuntimeException("Boom!");
            });

            CompletableFuture<Void> allOf = CompletableFuture.allOf(cf1, cf2);
            allOf.join();

            System.out.println("cf1.resultNow() = " + cf1.resultNow());
            System.out.println("cf2.resultNow() = " + cf2.resultNow());
        }
    }

    record Example4() {
        public static void main(String[] args) {
            var cf1 = CompletableFuture.supplyAsync(() -> process(1));
            var cf2 = CompletableFuture.supplyAsync(() -> {
//                process(2);
                throw new RuntimeException("Boom!");
            });

            CompletableFuture<Void> allOf = CompletableFuture.allOf(cf1, cf2);
            allOf.join();

            System.out.println("cf1.resultNow() = " + cf1.resultNow());
            System.out.println("cf2.resultNow() = " + cf2.resultNow());
        }
    }

    record Example5() {
        public static void main(String[] args) {
            var cf1 = CompletableFuture.supplyAsync(() -> process(1));
            var cf2 = CompletableFuture.supplyAsync(() -> {
                throw new RuntimeException("Boom!");
            });

            CompletableFuture<Object> anyOf = CompletableFuture.anyOf(cf1, cf2);
            anyOf.join();

            System.out.println("cf1.resultNow() = " + cf1.resultNow());
            System.out.println("cf2.resultNow() = " + cf2.resultNow());
        }
    }

    static <T> T process(T i) {
        try {
            System.out.println("Processing " + i + " on " + Thread.currentThread());
            Thread.sleep(ThreadLocalRandom.current().nextInt(5_000, 10_000));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return i;
    }
}
