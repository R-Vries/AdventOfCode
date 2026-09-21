package main.java.framework;

import main.java.util.Timer;

import java.io.IOException;
import java.util.List;

public class Runner {
    private final Solver<?> solver;
    private final IOManager io;

    public Runner(Solver<?> solver, IOManager io) {
        this.solver = solver;
        this.io = io;
    }

    public Runner(AppConfig config) {
        this.solver = SolverFactory.find(config.year(), config.day());
        this.io = new IOManager(config.year(), config.day());
    }

    public void run() throws IOException, InterruptedException {
        List<String> input = io.readInput();

        System.out.printf("########### DAY %02d ###########%n", io.day);

        long total = 0;
        try {
            var t1 = Timer.time(() -> solver.solvePart1(input));
            total += t1.ms();
            System.out.printf("Part 1: %s (%d ms)%n", t1.result(), t1.ms());
        } catch (UnsupportedOperationException e) {
            System.out.println("Part 1: not implemented");
        }

        try {
            var t2 = Timer.time(() -> solver.solvePart2(input));
            total += t2.ms();
            System.out.printf("Part 2: %s (%d ms)%n", t2.result(), t2.ms());
        } catch (UnsupportedOperationException e) {
            System.out.println("Part 2: not implemented");
        }

        System.out.println("------------------------------");
        System.out.printf("Total elapsed time: %d ms%n", total);

        System.out.println("##############################");
    }
}
