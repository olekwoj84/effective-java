package com.for_comprehension.function.other.exceptions;

import java.util.Arrays;

class StacktraceDropExample {

    public static void main(String[] args) {
        NullPointerException previous = null;

        String foo = null;
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            try {
                foo.toUpperCase();
            } catch (NullPointerException e) {
//                e.printStackTrace();
                if (e.getStackTrace().length == 0) {
                    System.out.printf("Stacktrace dropped at iteration %d%n", i);
                    if (previous != null) {
                        System.out.printf("Last stacktrace: %s%n",
                          Arrays.toString(previous.getStackTrace()));
                    }
                    System.out.printf("New stacktrace: %s%n",
                      Arrays.toString(e.getStackTrace()));
                    return;
                }
                previous = e;
            }
        }
    }
}
