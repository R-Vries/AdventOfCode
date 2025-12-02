package aoc

/**
 * Base class for all Advent of Code puzzle solvers.
 *
 * Each solver defines:
 * - how to parse the raw input ([parse])
 * - how to solve part 1 ([part1])
 * - how to solve part 2 ([part2])
 */

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
    abstract fun part1(data: T): Number

    /**
     * Computes the solution to part 2 of the puzzle.
     *
     * @param data The parsed input returned by [parse].
     * @return The result for part 2.
     */
    abstract fun part2(data: T): Number

    /**
     * Parses the given input and returns the solution for part 1.
     *
     * @param input Raw puzzle input lines.
     * @return The computed answer for part 1.
     */
    fun solvePart1(input: List<String>): Number =
        part1(parse(input))

    /**
     * Parses the given input and returns the solution for part 2.
     *
     * @param input Raw puzzle input lines.
     * @return The computed answer for part 2.
     */
    fun solvePart2(input: List<String>): Number =
        part2(parse(input))
}