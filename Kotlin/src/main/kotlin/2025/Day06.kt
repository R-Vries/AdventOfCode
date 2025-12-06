package aoc.`2025`

import aoc.IOManager
import aoc.Runner
import aoc.Solver
import aoc.Tester

object Day06: Solver<List<Day06.Calculation>>() {
    override fun parse(input: List<String>): List<Calculation> {
        // finding columns with only spaces
        val spaceIndices = findSpaces(input)
        val result = mutableListOf<Calculation>()
        for (col in input.first().trim().split("\\s+".toRegex())) {
            result.add(Calculation(mutableListOf(), ""))
        }
        for (line in input) {
            if (line.startsWith('*') || line.startsWith('+')) {
                val instructions = line.trim().split("\\s+".toRegex())
                for (i in 0..instructions.lastIndex) {
                    result[i].operator = instructions[i]
                }
            } else {
                val numbers = splitAtIndices(line, spaceIndices)
                for (i in 0..numbers.lastIndex) {
                    result[i].numbers.add(numbers[i])
                }
            }

        }
        return result
    }

    override fun part1(data: List<Calculation>): Number =
        data.sumOf { calc ->
            if (calc.operator == "*") calc.numbers.map { it.trim().toLong() }
                .reduce(Long::times)
            else calc.numbers.sumOf { it.trim().toLong() }
        }

    override fun part2(data: List<Calculation>): Number =
        data.sumOf { calc ->
            if (calc.operator == "*") {
                var result = 1L
                // reading numbers vertically
                for (i in 0..calc.numbers[0].lastIndex) {
                    var verticalNumber = ""
                    for (n in calc.numbers) {
                        if (n[i] == ' ') continue
                        else verticalNumber += n[i]
                    }
                    result *= verticalNumber.toLong()
                }
                result
            } else {
                var result = 0L
                // reading numbers vertically
                for (i in 0..calc.numbers[0].lastIndex) {
                    var verticalNumber = ""
                    for (n in calc.numbers) {
                        if (n[i] == ' ') continue
                        else verticalNumber += n[i]
                    }
                    result += verticalNumber.toLong()
                }
                result
            }
        }

    data class Calculation(val numbers: MutableList<String>, var operator: String)

    fun splitAtIndices(s: String, indices: List<Int>): List<String> {
        val result = mutableListOf<String>()
        var previous = 0

        for (i in indices) {
            result += s.substring(previous, i)
            previous = i + 1
        }

        result += s.substring(previous)
        return result
    }

    private fun findSpaces(input: List<String>): MutableList<Int> {
        val spaceIndices = mutableListOf<Int>()
        for (i in 0..input[0].trim().lastIndex) {
            if (input[0][i] == ' ') {
                spaceIndices.add(i)
            }
        }
        val result = spaceIndices.toMutableList()
        for (line in input.drop(1)) {
            for (i in spaceIndices) {
                if (line[i] != ' ') {
                    result.remove(i)
                }
            }
        }
        return result
    }
}

fun main() {
    val io = IOManager(2025, 6)
    val tester = Tester(Day06, io, 4277556, 3263827)
    val runner = Runner(Day06, io)
    tester.runTests()
    runner.run()
}