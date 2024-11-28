package com.for_comprehension.function.other.exceptions;

import java.util.Optional;

// https://4comprehension.com/exception-drop-stacktraces/
// http://normanmaurer.me/blog/2013/11/09/The-hidden-performance-costs-of-instantiating-Throwables/
class PerformanceOptimizedExceptionThrowing {

    public static UserNotFoundException INSTANCE = new UserNotFoundException("this should not happen");

    public static void main(String[] args) {
        String s = findUserById(42)
          .orElseThrow(() -> INSTANCE);
    }

    static Optional<String> findUserById(int id) {
        return Optional.empty();
    }

    static class UserNotFoundException extends RuntimeException {
        public UserNotFoundException(String message) {
            super(message);
        }

        @Override
        public Throwable fillInStackTrace() {
            return null;
        }
    }
}
