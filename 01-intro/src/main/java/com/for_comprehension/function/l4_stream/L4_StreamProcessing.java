package com.for_comprehension.function.l4_stream;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.Comparator.naturalOrder;

class L4_StreamProcessing {

    record Example1_Processing() {
        public static void main(String[] args) {
            users()
              .map(s -> s.toUpperCase())
              .flatMap(s -> users()) // https://4comprehension.com/java-stream-api-was-broken-before-jdk10/
              .filter(i -> i.length() > 3)
              .forEach(System.out::println);
        }
    }

    record Example2_Processing() {
        public static void main(String[] args) {
            users()
              .sorted(comparing(String::length)
                .thenComparing(naturalOrder()))
              .forEach(System.out::println);
        }
    }

    record Example3_Processing() {
        public static void main(String[] args) {
            users()
              .distinct()
              .forEach(System.out::println);
        }
    }

    record Example4_Processing() {
        public static void main(String[] args) {
            users()
              .limit(2)
              .forEach(System.out::println);
        }
    }

    record Example5_Processing() {
        public static void main(String[] args) {
            users()
              .skip(2)
              .forEach(System.out::println);
        }
    }

    record Example6_Processing() {
        public static void main(String[] args) {
            users()
              .takeWhile(s -> s.length() > 3)
              .forEach(System.out::println);
        }
    }

    record Example7_Processing() {
        public static void main(String[] args) {
            users()
              .dropWhile(s -> s.length() > 3)
              .forEach(System.out::println);
        }
    }

    record Example8_Processing() {
        public static void main(String[] args) {
            users()
              .mapMulti((s, sink) -> {
                  if (s.length() > 3) {
                      sink.accept(s);
                  }
              })
              .forEach(System.out::println);
        }
    }

    record Example9_Processing() {
        public static void main(String[] args) {
            users()
              .mapToInt(s -> s.length())
              .boxed()
              .forEach(System.out::println);
        }
    }

    record Example10_Processing() {
        public static void main(String[] args) {
            var result = users()
              .peek(System.out::println)
              .toList();
        }
    }

    record Example11_Processing() {
        public static void main(String[] args) {
            long count = users()
              .peek(i -> System.out.printf("sending email to: %s%n", i))
              .count();
            System.out.println("count = " + count);

            AtomicInteger counter = new AtomicInteger();
            users()
              .forEach(i ->{
                    System.out.printf("sending email to: %s%n", i);
                    counter.getAndIncrement();
              });
        }
    }

    public static Stream<String> users() {
        return Stream.of("Adam", "Adam", "John", "Eve", "Jane", "Alice", "Bob");
    }
}
