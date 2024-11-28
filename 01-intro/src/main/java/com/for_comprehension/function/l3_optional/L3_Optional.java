package com.for_comprehension.function.l3_optional;

import java.util.Optional;

// https://www.youtube.com/watch?v=Ej0sss6cq14
class L3_Optional {

    public static void main(String[] args) {

    }

    record Example1() {
        public static void main(String[] args) {
            String s = findUserById(42)
              .orElseThrow(() -> new IllegalStateException("this should not happen"));
        }

        public static Optional<String> findUserById(int id) {
            return Optional.of("Adam");
        }
    }

    record Example2() {
        public static void main(String[] args) {
            String s = findUserById(42)
              .orElseThrow(() -> new IllegalStateException("this should not happen"));
        }

        public static Optional<String> findUserById(int id) {
            return Optional.of("Adam");
        }
    }

    record Example3() {
        public static void main(String[] args) {
            Optional<Optional<String>> s1_nested = findUserById(42)
              .map(s -> findUserById(12312));

            Optional<String> s1_flat = findUserById(42)
              .flatMap(s -> findUserById(12312));
        }

        public static Optional<String> findUserById(int id) {
            return Optional.of("Adam");
        }
    }

    record Example4() {
        public static void main(String[] args) {
            findUserById(42)
              .filter(s -> s.startsWith("B"))
              .map(s -> s.toUpperCase())
              .ifPresentOrElse(
                r -> System.out.println("Found " + r),
                () -> System.out.println("empty"));
        }

        public static Optional<String> findUserById(int id) {
            return Optional.of("Adam");
        }
    }

    record Example5() {
        public static void main(String[] args) {
            findUserById(1)
              .or(() -> findUserById(2))
              .or(() -> findUserById(3))
              .or(() -> findUserById(42))
              .ifPresent(System.out::println);
        }

        public static Optional<String> findUserById(int id) {
            return id == 42 ? Optional.of("Bartek") : Optional.empty();
        }
    }

    record Example6() {
        public static void main(String[] args) {
            String r1 = findUserById(42).get();// antipattern, use orElseThrow()
            String r2 = findUserById(42).orElseThrow();
            String r3 = findUserById(42).orElseThrow(() -> new RuntimeException("some custom exception"));

            System.out.println("findUserById(1).orElse(\"default\") = " + findUserById(1).orElse("default"));
            System.out.println("findUserById(1).orElseGet(() -> \"default\") = " + findUserById(42).orElseGet(() -> calculateDefault())); // lazy evaluation

            findUserById(42).ifPresentOrElse(
              r -> System.out.println("Found " + r),
              () -> System.out.println("empty"));

            findUserById(42).ifPresent(System.out::println);
        }

        public static String calculateDefault() {
            System.out.println("calculating default");
            try {
                Thread.sleep(10_000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "default";
        }

        public static Optional<String> findUserById(int id) {
            return id == 42 ? Optional.of("Bartek") : Optional.empty();
        }
    }

    record Example7() {
        public static void main(String[] args) {
            Optional<String> name = Optional.of("Bartek");

            helloWorldOptional(name);

            name.ifPresentOrElse(
              n -> helloWorld(n),
              () -> helloWorld());
        }

        public static void helloWorldOptional(Optional<String> name) {
            System.out.println("Hello " + name.orElse("World"));
        }

        public static void helloWorld(String name) {
            System.out.println("Hello " + name);
        }

        public static void helloWorld() {
            System.out.println("Hello World");
        }
    }
}

