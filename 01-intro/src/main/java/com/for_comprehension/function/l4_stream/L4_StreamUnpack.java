package com.for_comprehension.function.l4_stream;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;

class L4_StreamUnpack {

    record Example1_Unpack() {
        public static void main(String[] args) {
            users().forEach(System.out::println);
        }
    }

    record Example2_Unpack() {
        public static void main(String[] args) {
            long count = users().count();
        }
    }

    record Example3_Unpack() {
        public static void main(String[] args) {
            Optional<String> r1 = users().reduce((s1, s2) -> s1 + s2);
            String r2 = users().reduce("", (s1, s2) -> s1 + s2);
        }
    }

    record Example4_Unpack() {
        public static void main(String[] args) {
            System.out.println("users().anyMatch(s -> s.startsWith(\"A\")) = " + users().anyMatch(s -> s.startsWith("A")));
            System.out.println("users().allMatch(s -> s.startsWith(\"A\")) = " + users().allMatch(s -> s.startsWith("A")));
            System.out.println("users().noneMatch(s -> s.startsWith(\"A\")) = " + users().noneMatch(s -> s.startsWith("A")));

            System.out.println("Stream.of().anyMatch(a -> true) = " + Stream.of().anyMatch(a -> true));
            System.out.println("Stream.of().allMatch(a -> true) = " + Stream.of().allMatch(a -> true));
            System.out.println("Stream.of().noneMatch(a -> true) = " + Stream.of().noneMatch(a -> true));
        }
    }

    record Example5_Unpack() {
        public static void main(String[] args) {
            Optional<String> first = users().findFirst();
            Optional<String> any = users().findAny();
        }
    }

    record Example6_Unpack() {
        public static void main(String[] args) {
            System.out.println("users().max(comparing(String::length)) = " + users().max(comparing(String::length)));
            System.out.println("users().min(comparing(String::length)) = " + users().min(comparing(String::length)));
        }
    }

    record Example7_Unpack() {
        public static void main(String[] args) {
            Object[] array = users().toArray();
            String[] strings = users().toArray(String[]::new);
            List<String> list = users().toList();
            List<String> collect = users().collect(Collectors.toList());
        }
    }

    public static Stream<String> users() {
        return Stream.of("Adam", "Adam", "John", "Eve", "Jane", "Alice", "Bob");
    }
}
