package aoc.`2025`

import aoc.Coordinate
import aoc.Grid
import aoc.IOManager
import aoc.Runner
import aoc.Solver
import aoc.Tester

object Day07: Solver<Grid<Char>>() {
    override fun parse(input: List<String>): Grid<Char> {
        return Grid(input)
    }

    override fun part1(data: Grid<Char>): Number {
        // find 'S'
        val start = data.coordinates().first { data[it] == 'S'}
        val beams = ArrayDeque(listOf(start))

        var split = 0
        while (beams.isNotEmpty()) {
            val beam = beams.removeFirst()
            val next = Coordinate(beam.i+1, beam.j)
            if (data.inBounds(next) && next !in beams) {
                if (data[next] == '^') {
                    beams.add(Coordinate(next.i, next.j+1))
                    beams.add(Coordinate(next.i, next.j-1))
                    split++
                } else {
                    beams.add(next)
                }
            }

        }
        return split
    }

    override fun part2(data: Grid<Char>): Number {
        val ways = Array(data.rows) { LongArray(data.cols) }

        for (i in 0 until data.rows) {
            for (j in 0 until data.cols) {
                val amount = ways[i][j]
                when (data[i, j]) {
                    'S' -> {
                        ways[i + 1][j] = 1
                    }
                    '.' -> {
                        if (data.inBounds(i + 1, j)) {
                            ways[i + 1][j] += amount
                        }
                    }
                    '^' -> {
                        if (data.inBounds(i + 1, j)) {
                            ways[i+1][j-1] += amount
                            ways[i+1][j+1] += amount
                        }
                    }
                }
            }
        }
        return ways.last().sum()
    }

}

fun main() {
    val io = IOManager(2025, 7)
    val tester = Tester(Day07, io, 21, 40)
    val runner = Runner(Day07, io)
    tester.runTests()
    runner.run()
}