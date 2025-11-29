package org.example

import kotlin.time.measureTimedValue

abstract class Solver<T>(val year: Int, val day: Int) {
    private val dayString = formatDayString(day)
    private val input = readInput(dayString, year)

    abstract fun parse(input: List<String>): T
    abstract fun part1(data: T): Int
    abstract fun part2(data: T): Int

    fun run() {
        println("############ DAY $day ############")
        val part1Time = solve(1)
        val part2Time = solve(2)
        val total = part1Time + part2Time
        println("-------------------------------")
        println("Total elapsed time: $total ms ")
        println("###############################")

    }

    fun runTest(part: Int, expected: Int) {
        val partSolver = getPartSolver(part)
        val actual = partSolver(parse(readTest(dayString, year)))
        check(actual == expected) {
            "Test failed for part $part ($year): expected $expected, got $actual"
        }
        println("Test passed for part $part")
    }

    fun solve(part: Int): Long {
        println("Running solver for part $part...")
        val (result, elapsed) = measureTimedValue {
            getPartSolver(part)(parse(input))
        }.let {
            (value, duration) -> value to duration.inWholeMilliseconds
        }
        println("The result was $result ($elapsed ms)")
        return elapsed
    }



    private fun getPartSolver(part: Int): (T) -> Int = when (part) {
        1 -> this::part1
        2 -> this::part2
        else -> error("Invalid part number ($part)")
    }

    private fun formatDayString(day: Int): String =
        if (day < 10) "day0$day" else "day$day"

}


