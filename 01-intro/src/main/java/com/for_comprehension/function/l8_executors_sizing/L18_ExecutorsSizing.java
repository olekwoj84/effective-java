package com.for_comprehension.function.l8_executors_sizing;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class L18_ExecutorsSizing {

    public static void main(String[] args) {
        ExecutorService e = Executors.newVirtualThreadPerTaskExecutor();
    }
}
