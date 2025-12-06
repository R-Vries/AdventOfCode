package aoc.`2025`

import aoc.IOManager
import aoc.Runner
import aoc.Solver
import aoc.Tester

/**
 * Learned to make use of [digitToInt] (NOT [toInt]) for [Char]s. Also learned [drop]
 */
object Day03: Solver<List<List<Int>>>() {
    override fun parse(input: List<String>): List<List<Int>> =
        input.map { it.map { i -> i.digitToInt() } }

    override fun part1(data: List<List<Int>>): Number =
        data.sumOf { bank ->
            findHighest(bank, 2)
        }

    override fun part2(data: List<List<Int>>): Number =
        data.sumOf { bank ->
            findHighest(bank, 12)
        }

    private fun findHighest(bank: List<Int>, amount: Int): Long {
        val sb = StringBuilder()
        var bankCopy = bank
        repeat(amount) {
            var max = bankCopy.max()
            var maxIndex = bankCopy.indexOf(max)
            while (maxIndex + amount - 1 - it > bankCopy.lastIndex) {
                max = bankCopy.filter { it < max }.max()
                maxIndex = bankCopy.indexOf(max)
            }
            bankCopy = bankCopy.drop(maxIndex + 1)
            sb.append(max)
        }
        return sb.toString().toLong()
    }
}

fun main() {
    val io = IOManager(2025, 3)
    val tester = Tester(Day03, io, 357, 3121910778619)
    val runner = Runner(Day03, io)
    tester.runTests()
    runner.run()
}