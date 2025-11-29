package org.example

abstract class Solver<T>(val year: Int, val day: Int) {
    private val dayString = formatDayString(day)

    abstract fun parse(input: List<String>): T
    abstract fun part1(data: T): Int
    abstract fun part2(data: T): Int

    fun runTest(part: Int, expected: Int) {
        val partSolver = getPartSolver(part)
        val actual = partSolver(parse(readTest(dayString, year)))
        check(actual == expected) {
            "Test failed for part $part ($year): expected $expected, got $actual"
        }
        println("Test passed for part $part")
    }

    fun solve(part: Int) {
        val result = getPartSolver(part)(parse(readInput(dayString, year)))
        println("Result for part $part: $result")
    }


    private fun getPartSolver(part: Int): (T) -> Int = when (part) {
        1 -> this::part1
        2 -> this::part2
        else -> error("Invalid part number ($part)")
    }

    private fun formatDayString(day: Int): String =
        if (day < 10) "day0$day" else "day$day"

}


