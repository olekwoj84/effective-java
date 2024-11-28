package com.for_comprehension.function.l4_stream;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class L4_StreamFundamentals {

    record Example1_LazyVsTerminal() {
        public static void main(String[] args) {
            Stream.of(1,2,3,4)
              .map(i -> {
                  System.out.println(i);
                  return i;
              }); // lacks terminal operation!
        }
    }

    record Example2_StreamOnceOff() {
        public static void main(String[] args) {
            Stream<Integer> integerStream = Stream.of(1);

            integerStream.forEach(System.out::println);
            integerStream.forEach(System.out::println);
        }
    }

    record Example3_CreatingStreams() {
        public static void main(String[] args) {
            Stream<Integer> s1 = Stream.of(1, 2, 3);
            Stream<Integer> s2 = Stream.iterate(0, i -> i + 1);
            Stream<Integer> s3 = Stream.generate(() -> 42);
            Stream.Builder<Integer> s4 = Stream.builder();
            Stream<Integer> s5 = Stream.concat(s1, s2);
            Stream<Object> s6 = Stream.ofNullable(null);

            IntStream s7 = IntStream.range(0, 100);
            IntStream s8 = IntStream.rangeClosed(0, 100);
        }
    }
}
