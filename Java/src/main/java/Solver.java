package main.java;

import java.util.List;

public abstract class Solver<T> {

    public abstract T parse(List<String> input);

    public abstract double part1(T data) throws UnsupportedOperationException;

    public abstract double part2(T data) throws UnsupportedOperationException;

    public double solvePart1(List<String> input) throws UnsupportedOperationException {
        return part1(parse(input));
    }

    public double solvePart2(List<String> input) throws UnsupportedOperationException {
        return part2(parse(input));
    }
}
