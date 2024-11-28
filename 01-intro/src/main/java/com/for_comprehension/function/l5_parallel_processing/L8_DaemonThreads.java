package com.for_comprehension.function.l5_parallel_processing;

class L8_DaemonThreads {

    record Example1() {
        public static void main(String[] args) {

            Thread thread = new Thread(() -> {
                try {
                    Thread.sleep(1000000000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            thread.setDaemon(true);
            thread.start();

            System.out.println("finishing");
        }
    }
}
