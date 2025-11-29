package org.example.`2024`

import org.example.Solver
import kotlin.math.abs

object Day01: Solver<Pair<List<Int>, List<Int>>>(2024, 1) {

    override fun parse(input: List<String>): Pair<List<Int>, List<Int>> {
        return input
            .map { line ->
                line.trim()
                    .split(Regex("\\s+"))
                    .map(String::toInt)
                    .let { (a, b) -> a to b }
            }
            .unzip()
    }

    override fun part1(data: Pair<List<Int>, List<Int>>): Int {
        val (l1, l2) = data
        val s1 = l1.sorted()
        val s2 = l2.sorted()

        return s1.zip(s2)
            .sumOf { (a, b) -> abs(a - b) }
    }

    override fun part2(data: Pair<List<Int>, List<Int>>): Int {
        val (l1, l2) = data
        return l1.sumOf { i -> i * l2.count { it == i } }
    }

}

fun main() {
    Day01.run()
}