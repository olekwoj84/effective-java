package com.for_comprehension.function.l4_stream;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Gatherer;
import java.util.stream.Gatherers;
import java.util.stream.Stream;

// https://github.com/pivovarit/more-gatherers
class L7_CustomGatherers {

    record Example() {
        public static void main(String[] args) {
            Stream.of("a", "b", "cc", "dd")
              .gather(distinctBy(s -> s.length()))
              .forEach(System.out::println);
        }

        public static <T, P> Gatherer<T, ?, T> distinctBy(Function<T, P> propertyExtractor) {
            return Gatherer.ofSequential(() -> new HashSet<>(), Gatherer.Integrator.ofGreedy((state, element, downstream) -> {
                if (state.add(propertyExtractor.apply(element))) {
                    downstream.push(element);
                }
                return true;
            }));
        }
    }

    record Example1() {
        public static void main(String[] args) {
            Stream.of("a", "b", "cc", "dd")
              .gather(Gatherers.fold(() -> "", String::concat))
              .forEach(System.out::println);
        }
    }

    record Example2() {
        public static void main(String[] args) {
            Stream.of("a", "b", "cc", "dd")
              .gather(Gatherers.scan(() -> "", String::concat))
              .peek(System.out::println);
        }
    }

    record Example3() {
        public static void main(String[] args) {
            Stream.of("a", "b", "cc", "dd")
              .gather(Gatherers.windowFixed(2))
              .forEach(System.out::println);
        }
    }


    record Example4() {
        public static void main(String[] args) {
            Stream.of("a", "b", "cc", "dd")
              .gather(Gatherers.windowSliding(2))
              .forEach(System.out::println);
        }
    }

    record Example5() {
        public static void main(String[] args) {
            List<String> strings = List.of("a", "b", "c", "d");

            strings.stream()
              .gather(zipWithIndex((t, idx) -> t + " " + idx))
              .forEach(System.out::println);
        }

        public static <T> Gatherer<T, ?, Map.Entry<T, Long>> zipWithIndex() {
            return zipWithIndex(Map::entry);
        }

        public static <T, R> Gatherer<T, ?, R> zipWithIndex(BiFunction<T, Long, R> biFunction) {
            return Gatherer.ofSequential(() -> new AtomicLong(), Gatherer.Integrator.ofGreedy((state, element, downstream) -> {
                downstream.push(biFunction.apply(element, state.getAndIncrement()));
                return true;
            }));
        }
    }

}
