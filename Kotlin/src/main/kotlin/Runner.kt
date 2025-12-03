package aoc

import kotlin.time.measureTimedValue

/**
 * Executes a solver for a specific Advent of Code year/day.
 *
 * Responsibilities:
 * - reading the puzzle input via the IO manager
 * - invoking `parse`, `part1`, and `part2` on the solver
 * - timing both parts and printing a formatted summary
 * - forwarding timing results to the IO manager for persistence
 *
 * This class contains no puzzle logic; it only coordinates input, execution, and output.
 */
class Runner(
    val solver: Solver<*>,
    val io: IOManager
) {
    /**
     * Executes both parts of the puzzle for this day and prints a formatted summary.
     *
     * The method runs the solvers for part 1 and part 2, each of which prints its own
     * result and execution time. It then prints a summary block containing the total
     * time spent across both parts.
     *
     * Output format example:
     * ```
     * ########### DAY 01 ############
     * Part 1: 12345 (15 ms)
     * Part 2: 67890 (42 ms)
     * -------------------------------
     * Total elapsed time: 57 ms
     * ###############################
     * ```
     *
     * This method is intended as the main entry point for running a day's solution.
     */
    fun run() {
        val input = io.readInput()

        println("########### DAY ${"%02d".format(io.day)} ###########".toBold())

        val t1 = time { solver.solvePart1(input) }
        println("Part 1: ${t1.result} (${t1.ms} ms)".toBold())

        val t2 = time { solver.solvePart2(input) }
        println("Part 2: ${t2.result} (${t2.ms} ms)".toBold())

        println("------------------------".toBold())
        val total = t1.ms + t2.ms
        println("Total elapsed time: $total ms".toBold())
        println("##############################".toBold())

        io.updateTimes(t1.ms, t2.ms)
    }

    private inline fun <T> time(block: () -> T): Timed<T> {
        val (result, duration) = measureTimedValue(block)
        return Timed(result, duration.inWholeMilliseconds)
    }

    data class Timed<T>(val result: T, val ms: Long)
}
