package aoc

/**
 * Runs tests for a given solver using its test input and optional expected answers.
 *
 * The tester:
 * - loads the test input via [IOManager]
 * - parses it using [Solver.parse]
 * - runs part 1 and part 2
 * - compares results with expected answers when available
 * - skips a part if no expected answer is provided
 *
 * [printInput] parses and prints the test input for debugging.
 *
 * @param solver The [Solver] used by this [Tester]
 * @param io The [IOManager] used by this [Tester]
 * @param test1Answer The answer to the first test
 * @param test2Answer The answer to the second test
 */

class Tester(
    private val solver: Solver<*>,
    private val io: IOManager,
    private val test1Answer: Number?,
    private val test2Answer: Number?
) {

    /**
     * Run both tests for this puzzle. Skips tests if no expected answer is provided
     */
    fun runTests() {
        println("Running tests...".toBold())

        runTest(1, test1Answer)
        runTest(2, test2Answer)
    }

    /**
     * Prints the outcome of the [Solver]s [Solver.parse] method.
     */
    fun printInput() {
        val input = io.readTest()
        val parsed = solver.parse(input)
        println("Parsed data: $parsed".toBold())
    }

    private fun runTest(part: Int, expected: Number?) {
        if (expected == null) {
            println("Part $part test: skipped (no expected answer provided)".toYellow())
            return
        }

        val input = io.readTest()
        val actual = when (part) {
            1 -> solver.solvePart1(input)
            2 -> solver.solvePart2(input)
            else -> error("Invalid part: $part")
        }

        if (actual == expected) {
            println("Part $part test: passed".toGreen())
        } else {
            println("Part $part test: FAILED (expected $expected, got $actual)".toRed())
        }
    }
}
