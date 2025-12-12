package aoc.`2025`

import aoc.Coordinate
import aoc.Grid
import aoc.IOManager
import aoc.Runner
import aoc.Solver
import aoc.Tester
import aoc.pairs
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

/**
 * Learned to make use of [flatMap], [maxBy] and [windowed]
 */
object Day09: Solver<List<Coordinate>>() {
    override fun parse(input: List<String>): List<Coordinate> =
        input.map { line ->
            val split = line.split(",")
            Coordinate(split[0].toInt(), split[1].toInt())
        }

    override fun part1(data: List<Coordinate>): Long =
        pairs(data).maxOf { area(it.first, it.second) }


    override fun part2(data: List<Coordinate>): Number {
        TODO("Not yet implemented")
    }

    fun area(c1: Coordinate, c2: Coordinate): Long =
        (abs(c1.i - c2.i) + 1).toLong() * (abs(c1.j - c2.j) + 1).toLong()
}

fun main() {
    val io = IOManager(2025, 9)
    val tester = Tester(Day09, io, 50, 24)
    val runner = Runner(Day09, io)
    tester.runTests()
    runner.run()
}