package aoc.`2022`

import aoc.Solver

object Day01: Solver<List<List<Int>>>(2022, 1) {
    override fun parse(input: List<String>): List<List<Int>> {
        var currentElf = mutableListOf<Int>()
        val result = mutableListOf<List<Int>>()
        for (line in input) {
            if (line.isNotEmpty()) {
                currentElf.add(line.toInt())
            } else {
                result.add(currentElf)
                currentElf = mutableListOf()
            }
        }
        result.add(currentElf)
        return result
    }

    override fun part1(data: List<List<Int>>): Int =
        data.maxOf { it.sum() }


    override fun part2(data: List<List<Int>>): Int {
        val sorted = data.map { it.sum() }.sortedDescending()
        return sorted[0] + sorted[1] + sorted[2]
    }

}

fun main() {
    Day01.run()
}