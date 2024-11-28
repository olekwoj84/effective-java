package com.for_comprehension.function.l1_function;

import java.util.function.Predicate;
import java.util.function.Supplier;

class L1_Function {

    // Function<T, R>
    // Predicate<T>             -> Function<T, Boolean>
    // Consumer<T>              -> Function<T, Void>
    // Supplier<T>              -> Function<Void, T>
    // Callable<T>              -> Function<Void, T>
    // Runnable                 -> Function<Void, Void>
    // UnaryOperator<T>         -> Function<T, T>
    // BiFunction<T, U, R>
    // BiPredicate<T, U>        -> BiFunction<T, U, Boolean>
    // BinaryOperator<T>        -> BiFunction<T, T, T>
    public static void main(String[] args) {
        Supplier<Integer> supplier = () -> {
            try {
                Thread.sleep(10_000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            return 42;
        };

        System.out.println("supplier.get() = " + supplier.get());
    }

    record Example1() {
        // supplier achieves lazy semantics
        public static void main(String[] args) {
            Supplier<Integer> supplier = () -> {
                try {
                    Thread.sleep(10_000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                return 42;
            };

            // nothing happens unless .get() called
        }
    }

    record Example2() {
        public static void main(String[] args) {
            Runnable action = () -> {};
        }
    }

    record Example3() {
        public static void main(String[] args) {
            Predicate<String> predicate = s -> s.length() > 5;
            Predicate<String> a = predicate.and(s -> s.startsWith("a"));
        }
    }

    record Example4() {
        public static void main(String[] args) {
            EffectivelyFunctionalInterfaceFunction<String, String> f = s -> s;
        }

        // is not annotated but effectively functional
        interface EffectivelyFunctionalInterfaceFunction<T, R> {
            R apply(T t);
        }
    }
}

