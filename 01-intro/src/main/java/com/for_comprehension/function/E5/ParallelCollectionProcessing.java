package com.for_comprehension.function.E5;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.pivovarit.collectors.ParallelCollectors.parallel;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Gatherers.mapConcurrent;

class ParallelCollectionProcessing {

    record Example1() {
        // process all collection elements in ~10s
        // make sure that thread pool is properly shut down
        // make sure that pool is tuned properly (including thread names)
        public static void main(String[] args) {
            timed(() -> {
                List<Integer> integers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
                List<Integer> results = new ArrayList<>();

                try (var e = new ThreadPoolExecutor(integers.size(), integers.size(),
                  0L, TimeUnit.MILLISECONDS,
                  new LinkedBlockingQueue<>(), Thread.ofPlatform().name("parallel-collection-processing-", 0)
                  .factory())) {

                    List<Future<Integer>> futures = new ArrayList<>();
                    for (Integer integer : integers) {
                        Future<Integer> singleResult = e.submit(() -> process(integer));
                        futures.add(singleResult);
                    }

                    futures.forEach(future -> {
                        try {
                            results.add(future.get());
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    });
                }

                System.out.println("results = " + results);
            });
        }
    }

    record Example2() {
        // process all collection elements in ~10s
        // make sure that thread pool is properly shut down
        // make sure that pool is tuned properly (including thread names)
        public static void main(String[] args) {
            timed(() -> {
                List<Integer> integers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
                List<Integer> results = new ArrayList<>();

                try (var e = new ThreadPoolExecutor(integers.size(), integers.size(),
                  0L, TimeUnit.MILLISECONDS,
                  new LinkedBlockingQueue<>(), Thread.ofPlatform().name("parallel-collection-processing-", 0)
                  .factory())) {

                    integers.stream()
                      .map(integer -> e.submit(() -> process(integer)))
                      .toList()
                      .forEach(future -> {
                          try {
                              results.add(future.get());
                          } catch (Exception ex) {
                              throw new RuntimeException(ex);
                          }
                      });
                }

                System.out.println("results = " + results);
            });
        }
    }

    record Example3() {
        // process all collection elements in ~10s
        // make sure that thread pool is properly shut down
        // make sure that pool is tuned properly (including thread names)
        public static void main(String[] args) {
            // looks great but uses forkjoin pool...
            timed(() -> {
                var results = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).stream()
                  .parallel()
                  .map(i -> process(i))
                  .toList();

                System.out.println("results = " + results);
            });
        }
    }

    record Example4() {
        // process all collection elements in ~10s
        // make sure that thread pool is properly shut down
        // make sure that pool is tuned properly (including thread names)
        public static void main(String[] args) {
            timed(() -> {
                List<Integer> integers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

                try (var e = new ThreadPoolExecutor(integers.size(), integers.size(),
                  0L, TimeUnit.MILLISECONDS,
                  new LinkedBlockingQueue<>(), Thread.ofPlatform().name("parallel-collection-processing-", 0)
                  .factory())) {

                    var results = integers.stream()
                      .collect(parallel(i -> process(i), toList(), e, integers.size()))
                      .join();

                    System.out.println("results = " + results);
                }
            });
        }
    }

    record Example5() {
        // process all collection elements in ~10s
        // make sure that thread pool is properly shut down
        // make sure that pool is tuned properly (including thread names)
        public static void main(String[] args) {
            timed(() -> {
                var integers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

                var results = integers.stream()
                  .gather(mapConcurrent(integers.size(), i -> process(i)))
                  .toList();

                System.out.println("results = " + results);
            });
        }
    }

    record Example6() {
        public static void main(String[] args) {
            timed(() -> {
                List<Integer> integers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

                try (var e = new ThreadPoolExecutor(integers.size(), integers.size(),
                  0L, TimeUnit.MILLISECONDS,
                  new LinkedBlockingQueue<>(), Thread.ofPlatform().name("parallel-collection-processing-", 0)
                  .factory())) {

                    integers.stream()
                      .map(integer -> CompletableFuture.supplyAsync(() -> process(integer), e))
                      .collect(collectingAndThen(toList(), Example6::reduceToList))
                      .thenAccept(System.out::println);
                }
            });
        }

        static <T> CompletableFuture<List<T>> reduceToList(List<CompletableFuture<T>> futures) {
            // https://shipilev.net/blog/2016/arrays-wisdom-ancients/
            var result = CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new))
              .thenApply(_ -> futures)
              .thenApply(fs -> fs.stream().map(CompletableFuture::join).toList());

            for (CompletableFuture<T> future : futures) {
                future.whenComplete((_, exception) -> {
                    if (exception != null) {
                        result.completeExceptionally(exception);
                    }
                });
            }
            return result;
        }
    }

    static <T> T process(T i) {
        try {
            System.out.println("Processing " + i + " on " + Thread.currentThread());
            Thread.sleep(10_000);
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
