package aoc

import kotlin.time.measureTimedValue

class Runner<T>(
    val year: Int,
    val day: Int,
    val solver: Solver<T>,
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
     * Running solver for part 1...
     * The result was 12345 (15 ms)
     * Running solver for part 2...
     * The result was 67890 (42 ms)
     * -------------------------------
     * Total elapsed time: 57 ms
     * ###############################
     * ```
     *
     * This method is intended as the main entry point for running a day's solution.
     */
    fun run() {
        val input = io.readInput()

        println("######## DAY ${"%02d".format(day)} ########")

        val data = solver.parse(input)

        val t1 = time { solver.part1(data) }
        println("Part 1: ${t1.result} (${t1.ms} ms)")

        val t2 = time { solver.part2(data) }
        println("Part 2: ${t2.result} (${t2.ms} ms)")

        io.updateTimes(t1.ms, t2.ms)
    }

//    /**
//     * Executes the solver for the given puzzle part using the full input,
//     * printing the result and measuring execution time.
//     *
//     * The method selects the appropriate part function, parses the input,
//     * executes the solver, prints both the result and execution duration,
//     * and returns the elapsed time in milliseconds.
//     *
//     * @param part The puzzle part to solve (1 or 2).
//     * @return The execution time in milliseconds.
//     */
//    fun solve(part: Int): Long {
//        println("Running solver for part $part...".toBold())
//        val (result, elapsed) = measureTimedValue {
//            getPartSolver(part)(solver.parse(input))
//        }.let {
//                (value, duration) -> value to duration.inWholeMilliseconds
//        }
//        println("The result was $result ($elapsed ms)".toBold())
//        return elapsed
//    }
//
//    /** Find the right solver for the provided part */
//    private fun getPartSolver(part: Int): (*) -> Int = when (part) {
//        1 -> solver::part1
//        2 -> solver::part2
//        else -> error("Invalid part number ($part)")
//    }

    private inline fun <T> time(block: () -> T): Timed<T> {
        val (result, duration) = measureTimedValue(block)
        return Timed(result, duration.inWholeMilliseconds)
    }
}
data class Timed<T>(val result: T, val ms: Long)