package aoc

abstract class Solver<T> {
    /**
     * Parses the raw input lines into a structured data type used by this day's solver.
     *
     * @param input The lines of text read from the puzzle input file.
     * @return A parsed representation of the input, used as the data structure for both parts.
     */
    abstract fun parse(input: List<String>): T

    /**
     * Computes the solution to part 1 of the puzzle.
     *
     * @param data The parsed input returned by [parse].
     * @return The result for part 1.
     */
    abstract fun part1(data: T): Int

    /**
     * Computes the solution to part 2 of the puzzle.
     *
     * @param data The parsed input returned by [parse].
     * @return The result for part 2.
     */
    abstract fun part2(data: T): Int

    fun solvePart1(input: List<String>): Int =
        part1(parse(input))

    fun solvePart2(input: List<String>): Int =
        part2(parse(input))
}