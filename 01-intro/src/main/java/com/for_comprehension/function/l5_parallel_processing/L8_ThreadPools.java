package com.for_comprehension.function.l5_parallel_processing;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

class L8_ThreadPools {

    record Example1() {
        public static void main(String[] args) {
            int nThreads = 4;
            ExecutorService fixed = Executors.newFixedThreadPool(nThreads);
            timed(() -> {
                for (int i = 0; i < nThreads + 11; i++) {
                    int finalI = i;
                    fixed.submit(() -> process(finalI));
                }

                fixed.shutdown();
                try {
                    fixed.awaitTermination(1, TimeUnit.DAYS);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    record Example2() {
        public static void main(String[] args) {
            int n = 15;
            ExecutorService fixed = Executors.newCachedThreadPool();
            timed(() -> {
                List<CompletableFuture<Integer>> results = new ArrayList<>();
                for (int i = 0; i < n; i++) {
                    int finalI = i;
                    results.add(CompletableFuture.supplyAsync(() -> process(finalI), fixed));
                }

                CompletableFuture.allOf(results.toArray(CompletableFuture[]::new)).join();
            });
        }
    }

    record Example3() {
        public static void main(String[] args) {
            ExecutorService e = Executors.newFixedThreadPool(10, namedThreadFactory("mail-scheduler"));
            for (int i = 0; i < 1000; i++) {
                e.submit(() -> {});
            }
        }

        // use before JDK21
        private static ThreadFactory namedThreadFactory(String prefix) {
            return new ThreadFactory() {

                private final AtomicLong seq = new AtomicLong();

                @Override
                public Thread newThread(Runnable r) {
                    return new Thread(r, "%s-%d".formatted(prefix, seq.incrementAndGet()));
                }
            };
        }
    }

    // JDK21
    record Example4() {
        public static void main(String[] args) {
            ExecutorService e = Executors.newFixedThreadPool(10, Thread.ofPlatform().name("mail-scheduler-", 0).factory());
            for (int i = 0; i < 1000; i++) {
                e.submit(() -> {});
            }
        }
    }

    record Example5() {
        public static void main(String[] args) {
            ExecutorService executorService = Executors.newFixedThreadPool(4);
            for (int i = 0; i < 4; i++) {
                int finalI = i;
                executorService.submit(() -> {
                    if (finalI == 3) {
                        String name = Thread.currentThread().getName();
                        Thread.currentThread().setName("xxx");
                        try {
                            try {
                                Thread.currentThread().join();
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        } finally {
                            Thread.currentThread().setName(name);
                        }
                    }
                });
            }
        }
    }

    record Example6() {
        public static void main(String[] args) {
            ExecutorService e = new ThreadPoolExecutor(4, 4,
              0L, TimeUnit.MILLISECONDS,
              new LinkedBlockingQueue<>());
        }
    }

    record Example7() {
        public static void main(String[] args) {
            int nThreads = 4;
            int maxThread = 8;
            int queueSize = 10;
            ExecutorService fixed = new ThreadPoolExecutor(nThreads, maxThread,
              30L, TimeUnit.SECONDS,
              new LinkedBlockingQueue<>(queueSize));
            timed(() -> {
                for (int i = 0; i < nThreads + queueSize + 1; i++) {
                    int finalI = i;
                    fixed.submit(() -> process(finalI));
                }

                fixed.shutdown();
                try {
                    fixed.awaitTermination(1, TimeUnit.DAYS);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    record Example8() {
        public static void main(String[] args) {
            int nThreads = 4;
            int maxThread = 8;
            int queueSize = 10;
            ExecutorService fixed = new ThreadPoolExecutor(nThreads, maxThread,
              30L, TimeUnit.SECONDS,
              new LinkedBlockingQueue<>(queueSize),
              // throws rejected execution, default one
              new ThreadPoolExecutor.AbortPolicy());
            timed(() -> {
                for (int i = 0; i < maxThread + queueSize + 1; i++) {
                    int finalI = i;
                    fixed.submit(() -> process(finalI));
                }

                fixed.shutdown();
                try {
                    fixed.awaitTermination(1, TimeUnit.DAYS);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    record Example9() {
        public static void main(String[] args) {
            int nThreads = 4;
            int maxThread = 8;
            int queueSize = 10;
            ExecutorService fixed = new ThreadPoolExecutor(nThreads, maxThread,
              30L, TimeUnit.SECONDS,
              new LinkedBlockingQueue<>(queueSize),
              // simply drops the task
              new ThreadPoolExecutor.DiscardPolicy());
            timed(() -> {
                for (int i = 0; i < maxThread + queueSize + 1; i++) {
                    int finalI = i;
                    fixed.submit(() -> process(finalI));
                }

                fixed.shutdown();
                try {
                    fixed.awaitTermination(1, TimeUnit.DAYS);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    record Example10() {
        public static void main(String[] args) {
            int nThreads = 4;
            int maxThread = 8;
            int queueSize = 10;
            ExecutorService fixed = new ThreadPoolExecutor(nThreads, maxThread,
              30L, TimeUnit.SECONDS,
              new LinkedBlockingQueue<>(queueSize),
              // caller runs it :)
              new ThreadPoolExecutor.CallerRunsPolicy());
            timed(() -> {
                for (int i = 0; i < maxThread + queueSize + 1; i++) {
                    int finalI = i;
                    fixed.submit(() -> process(finalI));
                }

                fixed.shutdown();
                try {
                    fixed.awaitTermination(1, TimeUnit.DAYS);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    record Example11() {
        public static void main(String[] args) {
            int nThreads = 4;
            int maxThread = 8;
            int queueSize = 10;
            ExecutorService fixed = new ThreadPoolExecutor(nThreads, maxThread,
              30L, TimeUnit.SECONDS,
              new LinkedBlockingQueue<>(queueSize),
              // rejects the oldest task in the queue, extremely unfair but useful
              new ThreadPoolExecutor.DiscardOldestPolicy());
            timed(() -> {
                for (int i = 0; i < maxThread + queueSize + 1; i++) {
                    int finalI = i;
                    fixed.submit(() -> process(finalI));
                }

                fixed.shutdown();
                try {
                    fixed.awaitTermination(1, TimeUnit.DAYS);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    static <T> T process(T i) {
        try {
            System.out.println("processing: " + i + " on " + Thread.currentThread().getName());
            Thread.sleep(1_000);
        } catch (InterruptedException e) {
        }

        return i;
    }

    static void timed(Runnable run) {
        long start = System.currentTimeMillis();
        run.run();
        System.out.printf("Time taken: %dms%n", System.currentTimeMillis() - start);
    }
}
