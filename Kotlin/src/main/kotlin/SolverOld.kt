package aoc
import java.io.File
import java.net.HttpURLConnection
import java.net.URI
import kotlin.io.path.Path
import kotlin.io.path.exists
import kotlin.io.path.readLines
import kotlin.io.path.writeText
import kotlin.time.measureTimedValue

/**
 * Class for solving Advent of Code puzzles.
 *
 * Provides a template for solving the puzzles, using the methods [parse], [part1] and [part2].
 * Contains other methods for running the test input or running the actual input.
 * Also measures elapsed time in milliseconds and displays it
 *
 * @param T the datastructure that will be used for solving this puzzle
 * @property year the year event in which this puzzle takes place
 * @property day the day that this solver will solve
 * @constructor creates a solver for the given [year] and [day]
 */
abstract class SolverOld<T>(
    val year: Int,
    val day: Int,
    val test1Answer: Int?,
    val test2Answer: Int?
) {
    constructor(year: Int, day: Int) : this(year, day, null, null)

    /** Day as string, possibly padded with a 0 */
    private val dayString = formatDayString(day)
    /** Read the input file once at initialization */
    private val input: List<String> = readInput()

    /** Read the test file once at initialization */
    private val test: List<String> = readTest()

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
        println("############ DAY $dayString ###########".toBold())
        val part1Time = solve(1)
        val part2Time = solve(2)
        val total = part1Time + part2Time
        println(("-------------------------------\n" +
                "Total elapsed time: $total ms\n" +
                "###############################").toBold())
        updateTimes(part1Time, part2Time)
    }

    /**
     * Runs a test for the given puzzle part using the test input.
     *
     * This method parses the predefined test input for the current day and year,
     * executes the solution for the requested part, and compares the result to the
     * expected value. If the result does not match, the test fails with an error.
     *
     * Expects a test input file in `src/main/resources/<year>/tests`
     * named using the pattern `dayXX`, for example:
     *
     * `src/main/resources/2024/tests/day01.txt`
     *
     * @param part The puzzle part to test (1 or 2).
     * @param expected The expected output for the test input.
     * @throws IllegalStateException If the actual result does not match the expected value.
     */
    fun runTest(part: Int, expected: Int) {
        val partSolver = getPartSolver(part)
        val actual = partSolver(parse(test))
        check(actual == expected) {
            "Test failed for part $part ($year): expected $expected, got $actual"
        }

        println("Test passed for part $part".toGreen())
    }

    /** Runs both tests. Assumes the test answers are known
     *
     * @throws IllegalStateException when the test answers are not known to the class
     */
    fun runTests() {
        val t1 = test1Answer ?: error("No test answers provided for day $day part 1.")
        val t2 = test2Answer ?: error("No test answers provided for day $day part 2.")

        println("Running stored tests for day $day...")
        runTest(1, t1)
        runTest(2, t2)
    }

    /**
     * Executes the solver for the given puzzle part using the full input,
     * printing the result and measuring execution time.
     *
     * The method selects the appropriate part function, parses the input,
     * executes the solver, prints both the result and execution duration,
     * and returns the elapsed time in milliseconds.
     *
     * @param part The puzzle part to solve (1 or 2).
     * @return The execution time in milliseconds.
     */
    fun solve(part: Int): Long {
        println("Running solver for part $part...".toBold())
        val (result, elapsed) = measureTimedValue {
            getPartSolver(part)(parse(input))
        }.let {
            (value, duration) -> value to duration.inWholeMilliseconds
        }
        println("The result was $result ($elapsed ms)".toBold())
        return elapsed
    }

    /** Find the right solver for the provided part */
    private fun getPartSolver(part: Int): (T) -> Int = when (part) {
        1 -> this::part1
        2 -> this::part2
        else -> error("Invalid part number ($part)")
    }

    /** Formats the day to make it 2 numbers*/
    private fun formatDayString(day: Int): String =
        "%02d".format(day)

    /** Get the lines from the input. Fetches the input from the website if the file doesn't exist yet */
    private fun readInput(): List<String> {
        val inputFile = Path("src/main/resources/$year/inputs/day$dayString.txt")
        if (!inputFile.exists()) {
            println("Input file not found. Downloading from AoC...")
            fetchInput()
        }
        return inputFile.readLines()
    }

    /** Get the lines in the test file */
    private fun readTest(): List<String> {
        val inputFile = Path("src/main/resources/$year/tests/day$dayString.txt")
        if (!inputFile.exists()) {
            fetchTest()
        }
        return inputFile.readLines()
    }

    /** Fetches the file from the Advent of Code website */
    private fun fetchInput() {
        val cookieFile = File("../cookie.txt")
        require(cookieFile.exists()) {
            "Missing cookie.txt in project root. Place your AoC session cookie inside."
        }

        val sessionCookie = cookieFile.readText().trim()
        require(sessionCookie.isNotEmpty()) { "cookie.txt is empty." }

        val url = URI("https://adventofcode.com/$year/day/$day/input").toURL()
        val connection = (url.openConnection() as HttpURLConnection).apply {
            requestMethod = "GET"
            setRequestProperty("Cookie", "session=$sessionCookie")
        }

        val response = connection.inputStream.bufferedReader().readText()

        val targetFile = File("src/main/resources/$year/inputs/day$dayString.txt")
        targetFile.parentFile.mkdirs()
        targetFile.writeText(response)

        println("Downloaded input to ${targetFile.path}")
    }

    /** Get the test input through the command line. Also saves it to a file */
    private fun fetchTest() {
        println("Paste test input below. End with two empty lines:".toYellow())

        val lines = mutableListOf<String>()
        var emptyCount = 0

        while (true) {
            val line = readlnOrNull() ?: break

            if (line.isEmpty()) {
                emptyCount++
                if (emptyCount >= 2) break
            } else {
                emptyCount = 0
            }

            lines += line
        }

        val targetFile = File("src/main/resources/$year/tests/day$dayString.txt")
        targetFile.parentFile.mkdirs()

        targetFile.writeText(lines.joinToString("\n"))

        println("Input saved to ${targetFile.path}".toYellow())
    }


    /** Write the elapsed times of both parts to the results markdown file */
    private fun updateTimes(part1: Long, part2: Long) {
        val file = Path("results/$year.md")

        // Read all lines, or start with header if file does not yet exist
        val lines = if (file.exists()) file.readLines().toMutableList()
        else mutableListOf(
            "| Day | Part 1 (ms) | Part 2 (ms) | Total (ms) |",
            "|-----|-------------|-------------|------------|"
        )

        val dayString = day.toString()
        val total = part1 + part2

        val newRow = "| $dayString | $part1 | $part2 | $total |"

        val existingIndex = lines.indexOfFirst { it.startsWith("| $dayString |") }

        if (existingIndex != -1) {
            lines[existingIndex] = newRow
        } else {
            // Insert row in correct place (sorted by day)
            val insertIndex = lines
                .drop(2)                         // skip header
                .indexOfFirst {
                    val d = it.split("|")[1].trim().toInt()
                    day < d
                }
                .let { if (it == -1) lines.size else it + 2 }

            lines.add(insertIndex, newRow)
        }
        file.writeText(lines.joinToString("\n"))
    }
}