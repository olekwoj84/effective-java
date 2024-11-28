package com.for_comprehension.function.E7;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.function.Function;
import java.util.stream.Collectors;

class ParallelStream {

    static <T, R> List<R> parallelSync(Collection<T> input, Function<T, R> task, ExecutorService executor) {
        return parallelAsync(input, task, executor).join();
    }

    static <T, R> CompletableFuture<List<R>> parallelAsync(Collection<T> input, Function<T, R> task, ExecutorService executor) {
        return input.stream()
          .map(t -> CompletableFuture.supplyAsync(() -> task.apply(t), executor))
          .collect(Collectors.collectingAndThen(Collectors.toList(), ParallelStream::reduceToList));
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

    private static <T> T todo() {
        throw new RuntimeException("todo");
    }
}
