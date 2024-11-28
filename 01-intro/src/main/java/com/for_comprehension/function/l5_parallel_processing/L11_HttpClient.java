package com.for_comprehension.function.l5_parallel_processing;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.Executors;

class L11_HttpClient {

    record Example() {
        public static void main(String[] args) throws IOException, InterruptedException {
            HttpClient client = HttpClient.newBuilder()
              // if we don't do this, os threads will be used
              .executor(Executors.newVirtualThreadPerTaskExecutor())
              .build();
            HttpRequest request = HttpRequest.newBuilder()
              .uri(URI.create("http://www.google.com"))
              .build();
            try {
                System.out.println(client.send(request, rs -> {
                    System.out.println("Thread.currentThread() = " + Thread.currentThread());
                    return HttpResponse.BodyHandlers.ofString().apply(rs);
                }).statusCode());
                System.out.println("Thread.currentThread() = " + Thread.currentThread());

            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
