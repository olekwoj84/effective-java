package com.for_comprehension.stream.domain.util;

public class AssertUtil {
    public static <T> boolean isLambda(T e) {
        return e.getClass().getSimpleName().contains("$$Lambda");
    }
}
