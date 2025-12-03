package aoc.`2025`

import aoc.IOManager
import aoc.Runner
import aoc.Solver
import aoc.Tester

object Day03: Solver<List<List<Int>>>() {
    override fun parse(input: List<String>): List<List<Int>> =
        input.map { it.map { i -> i.digitToInt() } }


    override fun part1(data: List<List<Int>>): Number {
        var result = 0
        for (bank in data) {
            var max = bank.max()
            val maxIndex = bank.indexOf(max)
            var second: Int
            if (maxIndex == bank.lastIndex) {
                second = max
                max = bank.take(bank.lastIndex).max()

            } else {
                second = bank.drop(maxIndex + 1).max()
            }
            result += "$max$second".toInt()
        }
        return result
    }

    override fun part2(data: List<List<Int>>): Number {
        var result = 0L
        for (bank in data) {
            var joltage = ""
            var bankCopy = bank.toMutableList()
            repeat(12) {
                var max = bankCopy.max()
                var maxIndex = bankCopy.indexOf(max)
                while (maxIndex + 11 - it > bankCopy.lastIndex) {
                    max = bankCopy.filter { it < max }.max()
                    maxIndex = bankCopy.indexOf(max)
                }
                try {
                    bankCopy = bankCopy.drop(maxIndex + 1) as MutableList<Int>
                } catch (e: ClassCastException) {
                    //This is fine
                }
                joltage += max.toString()
            }
            result += joltage.toLong()
        }
        return result
    }

}

fun main() {
    val io = IOManager(2025, 3)
    val tester = Tester(Day03, io, 357, 3121910778619)
    val runner = Runner(Day03, io)
    tester.runTests()
    runner.run()
}