package aoc.`2025`

import aoc.IOManager
import aoc.Runner
import aoc.Solver
import aoc.Tester

object Day04: Solver<List<MutableList<Char>>>() {
    override fun parse(input: List<String>): List<MutableList<Char>> {
        val result = mutableListOf<MutableList<Char>>()
        result.add(MutableList(input[0].length + 2) { '.' })
        for (line in input) {
            val newLine = line.toCharArray().toMutableList()
            newLine.add(0, '.')
            newLine.add('.')
            result.add(newLine)
        }
        result.add(MutableList(input[0].length + 2) { '.' })
        return result
    }

    override fun part1(data: List<MutableList<Char>>): Number {
        var result = 0
        for (i in 1 until data.size - 1) {
            for (j in 1 until data[0].size - 1) {
                if (data[i][j] == '.') continue
                var count = 0
                for (spot in listOf(data[i-1][j-1], data[i-1][j], data[i-1][j+1], data[i][j-1], data[i][j+1], data[i+1][j-1], data[i+1][j], data[i+1][j+1])) {
                    if (spot == '@') count++
                }
                if (count < 4) result += 1
            }
        }
        return result
    }

    override fun part2(data: List<MutableList<Char>>): Number {
        var result = 0
        var changed = true
        while (changed) {
            changed = false
            for (i in 1 until data.size - 1) {
                for (j in 1 until data[0].size - 1) {
                    if (data[i][j] == '.') continue
                    var count = 0
                    for (spot in listOf(data[i-1][j-1], data[i-1][j], data[i-1][j+1], data[i][j-1], data[i][j+1], data[i+1][j-1], data[i+1][j], data[i+1][j+1])) {
                        if (spot == '@') count++
                    }
                    if (count < 4) {
                        result += 1
                        data[i][j] = '.'
                        changed = true
                    }
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