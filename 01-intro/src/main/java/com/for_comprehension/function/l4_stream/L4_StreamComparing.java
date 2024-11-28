package com.for_comprehension.function.l4_stream;

import java.util.Comparator;
import java.util.function.Function;
import java.util.stream.Stream;

class L4_StreamComparing {

    record Example1() {
        public static void main(String[] args) {
            System.out.println("by name:");
            users().sorted(byName()).forEach(System.out::println);

            System.out.println("by age:");
            users().sorted(byAge()).forEach(System.out::println);

            System.out.println("by age, then by salary:");
            users().sorted(byAge().thenComparing(bySalary())).forEach(System.out::println);
        }

        private static Comparator<User> byName() {
            return Comparator.comparing(u -> u.name);
        }

        private static Function<User, Integer> bySalary() {
            return u -> u.salary;
        }

        private static Comparator<User> byAge() {
            return Comparator.comparing(u -> u.age);
        }

        record User(String name, int age, int salary) {
        }

        public static Stream<User> users() {
            return Stream.of(
              new User("Alice", 40, 1000),
              new User("Bob", 35, 2000),
              new User("Charlie", 30, 3000),
              new User("Dave", 35, 4000),
              new User("Eve", 23, 5000)
            );
        }
    }

    record Example2() {
        public static void main(String[] args) {
            Stream.of(2,3,1).sorted().forEach(System.out::println);
        }
    }
}
