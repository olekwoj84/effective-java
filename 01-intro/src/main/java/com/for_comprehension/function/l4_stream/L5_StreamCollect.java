package com.for_comprehension.function.l4_stream;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.filtering;
import static java.util.stream.Collectors.flatMapping;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.reducing;
import static java.util.stream.Collectors.summingDouble;
import static java.util.stream.Collectors.toCollection;

class L5_StreamCollect {

    record Example1() {
        public static void main(String[] args) {
            List<String> c1 = Stream.of("Adam", null).collect(Collectors.toList());
            System.out.println("c1 = " + c1);
            c1.clear();

            // does not support null values!
            List<String> c2 = Stream.of("Adam").collect(Collectors.toUnmodifiableList());
//            c2.clear() -> java.lang.UnsupportedOperationException
            System.out.println("c2 = " + c2);

            // returns unmodifiable list
            List<String> c3 = Stream.of("Adam", null).toList();
            System.out.println("c3 = " + c3);
            // c3.clear();
        }
    }

    record Example2() {
        public static void main(String[] args) {
            LinkedList<String> llist = Stream.of("Adam", null)
              .collect(toCollection(() -> new LinkedList<>()));
            Set<String> s = Stream.of("Adam", "Eve").collect(Collectors.toSet());

            LinkedHashMap<String, Integer> m = Stream.of("Adam", "Eve", "Adam")
              .collect(Collectors.toMap(e -> e, e -> e.length(), (v1, v2) -> v1, LinkedHashMap::new));
            System.out.println("m = " + m);
        }
    }

    record Example3() {
        public static void main(String[] args) {
            String c1 = Stream.of("Adam", "Eve", "Adam").collect(Collectors.joining());
            String c2 = Stream.of("Adam", "Eve", "Adam").collect(Collectors.joining(","));
            String c3 = Stream.of("Adam", "Eve", "Adam").collect(Collectors.joining(",", "(", ")"));
            System.out.printf("c1 = %s%n", c1);
            System.out.printf("c2 = %s%n", c2);
            System.out.printf("c3 = %s%n", c3);
        }
    }

    record Example4() {
        // https://4comprehension.com/the-ultimate-guide-to-the-java-stream-api-groupingby-collector/
        public static void main(String[] args) {
            Map<Integer, List<String>> g1 = strings().collect(groupingBy(s -> s.length()));
            Map<Integer, Set<String>> g2 = strings().collect(groupingBy(s -> s.length(), Collectors.toSet()));
            Map<Integer, Set<String>> g3 = strings().collect(groupingBy(s -> s.length(), mapping(String::toUpperCase, Collectors.toSet())));

            System.out.println(g1);
            System.out.println(g2);
            System.out.println(g3);
        }

        private static Stream<String> strings() {
            return Stream.of("a", "bb", "ccc", "dd", "dd", "ff", "gggg", "h");
        }
    }

    record Example5() {
        public static void main(String[] args) {
            // imperative
            Map<Integer, String> result = new HashMap<>();

            for (String s : strings()) {
                int length = s.length();

                // Skip strings with zero length
                if (length == 0) {
                    continue;
                }

                // Convert to uppercase
                String upperCase = s.toUpperCase();

                // Check for duplicates in the current group
                if (!result.containsKey(length)) {
                    result.put(length, "");
                }
                String currentValue = result.get(length);

                // Append to the result if not already added
                boolean alreadyExists = false;
                for (int i = 0; i < currentValue.length(); i += upperCase.length()) {
                    if (currentValue.substring(i, Math.min(i + upperCase.length(), currentValue.length())).equals(upperCase)) {
                        alreadyExists = true;
                        break;
                    }
                }

                if (!alreadyExists) {
                    result.put(length, currentValue + upperCase);
                }
            }

            // declarative
            Map<Integer, String> result2 = strings().stream()
              .collect(
                groupingBy(String::length,
                  mapping(toStringList(),
                    flatMapping(s -> s.stream().distinct(),
                      filtering(s -> s.length() > 0,
                        mapping(String::toUpperCase,
                          reducing("", (s, s2) -> s + s2)))))));
        }

        private static <U, T> Function<String, List<String>> toStringList() {
            return s -> List.of(s);
        }

        private static List<String> strings() {
            return List.of("a", "bb", "ccc", "dd", "dd", "ff", "gggg", "h");
        }
    }

    record Example6() {
        public static void main(String[] args) {
            List<String> unmodifiableLinkedList = Stream.of("a", "b").collect(Collectors.collectingAndThen(toCollection(LinkedList::new), Collections::unmodifiableList));

            double average = Stream.of(1d, 2d, 3d).collect(Collectors.teeing(summingDouble(i -> i), counting(), (sum, count) -> sum / count));
            System.out.println("average = " + average);
        }
    }
}
