package com.for_comprehension.function.l5_parallel_processing;

class L8_ThreadingBasics {

    record Example1() {
        public static void main(String[] args) {
            Thread t = new Thread(() -> {
                System.out.println("Hello World from " + Thread.currentThread().getName());
            });

//            t.run();
            t.start();
        }
    }

    record Example2() {
        public static void main(String[] args) {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                int finalI = i;
                new Thread(() -> {
                    System.out.println("i = " + finalI);
                    try {
                        Thread.sleep(1000000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }).start();
            }
        }
    }

    // https://4comprehension.com/how-to-stop-a-java-thread-without-using-thread-stop/
    record Example3() {
        public static void main(String[] args) throws InterruptedException {
            Thread t = new Thread(() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        System.out.println("Thread.currentThread().isInterrupted() = " + Thread.currentThread()
                          .isInterrupted());
                        System.out.println("interrupted, stopping!");
                        break;
                    }
                    System.out.println("Hello World!");
                }
            });

            t.start();

            Thread.sleep(10000);

//            t.stop();
            t.interrupt();
        }

        public static void cluelessInterrupt() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        }
    }

    record Example5() {
        public static void main(String[] args) throws InterruptedException {
            Thread.currentThread().interrupt();
            System.out.println("Hi!");
            Thread.sleep(Integer.MAX_VALUE);
        }
    }
}
