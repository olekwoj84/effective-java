package com.for_comprehension.function.l2_monad;

import java.util.Optional;
import java.util.function.Function;

class L2_Monad {

    public static void main(String[] args) {

    }

    record Example1() {
        public static void main(String[] args) {
            int a = 4;

            Monad monad = new Monad(42);
            String apply = monad.apply(i -> i + "alskdaslkd");
        }

        static class Monad {
            private int value;

            // context

            Monad(int value) {
                this.value = value;
            }

            public <R> R apply(Function<Integer, R> function) {
                // do something with context

                return function.apply(value);
            }
        }
    }

    record Example2() {
        public static void main(String[] args) {
            Optional<String> result = Optional.ofNullable(42)
              .map(i -> i.toString())
              .map(str1 -> str1.toUpperCase())
              .map(str -> str + " cośtam");

            System.out.println("result.get() = " + result.orElseThrow());
        }
    }

    record Example3() {
        public static void main(String[] args) {
            Optional<String> r = Optional.ofNullable(42)
              .map(i -> i.toString())
              .map(str1 -> str1.toUpperCase())
              .map(str -> str + " cośtam");

            String result = r.orElse("default");

            System.out.println("result = " + result);
        }
    }
}
