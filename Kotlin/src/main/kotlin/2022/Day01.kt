package aoc.`2022`

import aoc.IOManager
import aoc.Runner
import aoc.Solver

object Day01: Solver<List<List<Int>>>() {

    override fun parse(input: List<String>): List<List<Int>> =
        input.fold(mutableListOf<MutableList<Int>>(mutableListOf())) { acc, line ->
            if (line.isEmpty()) {
                acc.add(mutableListOf())
            } else {
                acc.last().add(line.toInt())
            }
            acc
        }

    override fun part1(data: List<List<Int>>): Int =
        data.maxOf { it.sum() }

    override fun part2(data: List<List<Int>>): Int =
        data.map { it.sum() }
            .sortedDescending()
            .take(3)
            .sum()
}

fun main() {
    val io = IOManager(2022, 1)
    val runner = Runner<List<List<Int>>>(2022, 1, Day01, io)
    runner.run()
}
