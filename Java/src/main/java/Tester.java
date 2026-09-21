package main.java;

import main.java.util.Color;

import java.io.IOException;
import java.util.List;

public class Tester {
    private final Solver<?> solver;
    private final IOManager io;
    private final Number test1Answer;
    private final Number test2Answer;

    public Tester(Solver<?> solver, IOManager io, Number test1Answer, Number test2Answer) {
        this.solver = solver;
        this.io = io;
        this.test1Answer = test1Answer;
        this.test2Answer = test2Answer;
    }

    public Tester(AppConfig config) {
        this.solver = SolverFactory.find(config.year(), config.day());
        this.io = new IOManager(config.year(), config.day());
        this.test1Answer = config.expectedPart1();
        this.test2Answer = config.expectedPart2();
    }

    public void run() {
        System.out.println("Running tests...");

        runTest(1, test1Answer);
        runTest(2, test2Answer);
    }

    private void runTest(int part, Number expected) {
        if (expected == null) {
            System.out.println(Color.yellow("Part " + part + " skipped (no expected answer provided)"));
            return;
        }

        List<String> input;
        try {
            input = io.readTest();
        } catch (IOException e) {
            System.err.println("Error reading test input for part " + part + ": " + e.getMessage());
            return;
        }

        try {
            Number actual = switch (part) {
                case 1 -> solver.solvePart1(input);
                case 2 -> solver.solvePart2(input);
                default -> throw new IllegalArgumentException("Invalid part: " + part);
            };

            if (actual.equals(expected)) {
                System.out.println(Color.green("Part " + part + " test: passed"));
            } else {
                System.out.println(Color.red("Part " + part + " test: FAILED (expected " + expected + ", got " + actual + ")"));
            }

        } catch (UnsupportedOperationException e) {
            System.out.println(Color.yellow("Part " + part + " not implemented, skipping..."));
        }
    }
}
