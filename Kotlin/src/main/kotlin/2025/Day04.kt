package aoc.`2025`

import aoc.Grid
import aoc.IOManager
import aoc.Runner
import aoc.Solver
import aoc.Tester

object Day04: Solver<Grid<Char>>() {
    override fun parse(input: List<String>): Grid<Char> = Grid(input)

    override fun part1(data: Grid<Char>): Int =
        data.coordinates().count {coordinate ->
            data[coordinate] != '.' &&
            data.getNeighbours(coordinate)
                .count { data[it] == '@' } < 4
        }

    override fun part2(data: Grid<Char>): Int {
        var result = 0
        var changed = true
        while (changed) {
            changed = false
            for (coordinate in data.coordinates()) {
                if (data[coordinate] == '.') continue
                val count = data.getNeighbours(coordinate).count { data[it] == '@' }
                if (count < 4) {
                    result += 1
                    data[coordinate] = '.'
                    changed = true
                }
            }
        }
        return result
    }
}

fun main() {
    val io = IOManager(2025, 4)
    val tester = Tester(Day04, io, 13, 43)
    val runner = Runner(Day04, io)
    tester.runTests()
    runner.run()
}