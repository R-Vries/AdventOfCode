package main.java.util;

import java.util.function.Supplier;

public class Timer {

    public record Result<T>(T result, long ms) {}

    public static <T> Result<T> time(Supplier<T> action) {
        long start = System.nanoTime();

        T result = action.get();

        long elapsed = System.nanoTime() - start;

        return new Result<>(result, elapsed / 1_000_000);
    }
}
