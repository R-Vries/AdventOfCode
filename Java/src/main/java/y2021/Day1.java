package main.java.y2021;

import main.java.framework.Solver;

import java.util.List;

public class Day1 extends Solver<List<Integer>> {
    @Override
    public List<Integer> parse(List<String> input) {
        return input.stream()
                .map(Integer::parseInt)
                .toList();
    }

    @Override
    public double part1(List<Integer> data) {
        return (long) data.size();
    }

    @Override
    public double part2(List<Integer> data) {
        throw new UnsupportedOperationException();
    }
}


