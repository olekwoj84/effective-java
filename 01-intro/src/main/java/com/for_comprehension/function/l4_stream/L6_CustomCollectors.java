package com.for_comprehension.function.l4_stream;

import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Stream;

class L6_CustomCollectors {

    record Example1() {
        public static void main(String[] args) {
            LinkedList<String> ll = Stream.of("a", "b", "c").collect(toLinkedList());
            System.out.println("ll = " + ll + " " + ll.getClass());
        }

        public static <T> Collector<T, ?, LinkedList<T>> toLinkedList() {
            return new Collector<T, LinkedList<T>, LinkedList<T>>() {
                @Override
                public Supplier<LinkedList<T>> supplier() {
                    return LinkedList::new;
                }

                @Override
                public BiConsumer<LinkedList<T>, T> accumulator() {
                    return LinkedList::add;
                }

                @Override
                public BinaryOperator<LinkedList<T>> combiner() {
                    return (l1, l2) -> {
                        l1.addAll(l2);
                        return l1;
                    };
                }

                @Override
                public Function<LinkedList<T>, LinkedList<T>> finisher() {
                    return Function.identity();
                }

                @Override
                public Set<Characteristics> characteristics() {
                    return Set.of(Characteristics.IDENTITY_FINISH);
                }
            };
        }

        public static <T> Collector<T, ?, LinkedList<T>> toLinkedList2() {
            return Collector.of(
              LinkedList::new,
              LinkedList::add,
              (l1, l2) -> {
                  l1.addAll(l2);
                  return l1;
              },
              Function.identity(),
              Collector.Characteristics.IDENTITY_FINISH
            );
        }
    }

    record Example2() {
        public static void main(String[] args) {
            String result = Stream.of(Map.of("a", "b"), Map.of("c", "d")).collect(toStringJoining(","));

            System.out.println("result = " + result);
        }

        public static class Foo {
            private final String a = "foo";
        }

        public static <T> Collector<T, ?, String> toStringJoining() {
            return toStringJoining("");
        }

        public static <T> Collector<T, ?, String> toStringJoining(String delimiter) {
            return new Collector<T, StringBuilder, String>() {
                @Override
                public Supplier<StringBuilder> supplier() {
                    return StringBuilder::new;
                }

                @Override
                public BiConsumer<StringBuilder, T> accumulator() {
                    return (sb, t) -> {
                        if (!sb.isEmpty()) {
                            sb.append(delimiter);
                        }
                        sb.append(t);
                    };
                }

                @Override
                public BinaryOperator<StringBuilder> combiner() {
                    return (sb1, sb2) -> {
                        if (sb1.isEmpty()) {
                            return sb2;
                        }
                        return sb1.append(delimiter).append(sb2);
                    };
                }

                @Override
                public Function<StringBuilder, String> finisher() {
                    return StringBuilder::toString;
                }

                @Override
                public Set<Characteristics> characteristics() {
                    return Set.of();
                }
            };
        }

        public static <T> Collector<T, ?, String> toStringJoining2(String delimiter) {
            return Collector.of(
              StringBuilder::new,
              (sb, t) -> {
                  if (!sb.isEmpty()) {
                      sb.append(delimiter);
                  }
                  sb.append(t);
              },
              (sb1, sb2) -> {
                  if (sb1.isEmpty()) {
                      return sb2;
                  }
                  return sb1.append(delimiter).append(sb2);
              },
              StringBuilder::toString
            );
        }
    }
}
