package aoc.`2025`

import aoc.IOManager
import aoc.Runner
import aoc.Solver
import aoc.Tester

/**
 * Learned to make use of [forEachIndexed], [reduce], [takeIf] and [toRegex]
 */
object Day06: Solver<List<Day06.Calculation>>() {
    override fun parse(input: List<String>): List<Calculation> {
        // finding columns with only spaces
        val spaceIndices = input[0].trim().indices
            .filter { i -> input.all { line -> line[i] == ' '} }
        // add the correct amount of column lists
        val cols = input.first().trim().split("\\s+".toRegex()).size
        val result = MutableList(cols) { Calculation(mutableListOf(), null, 0)}

        // fill column lists with numbers
        input.dropLast(1).forEach { line ->
            splitAtIndices(line, spaceIndices).forEachIndexed { i, number ->
                result[i].numbers.add(number)
            }
        }
        // add instructions
        val instructions = input.last().trim().split("\\s+".toRegex())
        instructions.forEachIndexed { i, operator ->
            if (operator == "+") {
                result[i].operator = { a: Long, b: Long -> a + b }
                result[i].default = 0
            } else {
                result[i].operator = { a: Long, b: Long -> a * b }
                result[i].default = 1
            }
        }
        return result
    }

    override fun part1(data: List<Calculation>): Number =
        data.sumOf { calc ->
            calc.numbers.map { it.trim().toLong() }.reduce { a, b -> calc.operator!!(a, b) }
        }

    override fun part2(data: List<Calculation>): Number =
        data.sumOf { calc ->
            (0..calc.numbers[0].lastIndex)
                .map { i ->
                    calc.numbers
                        .map { n -> n[i].takeIf {it != ' '}}
                        .joinToString("")
                        .toLong()
                }
                .fold(calc.default, calc.operator!!)
        }

    data class Calculation(
        val numbers: MutableList<String>,
        var operator: ((Long, Long) -> Long)?,
        var default: Long
    )

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
}

fun main() {
    val io = IOManager(2025, 6)
    val tester = Tester(Day06, io, 4277556, 3263827)
    val runner = Runner(Day06, io)
    tester.runTests()
    runner.run()
}