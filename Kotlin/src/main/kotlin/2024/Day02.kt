package aoc.`2024`

import aoc.SolverOld
import kotlin.math.abs

object Day02: SolverOld<List<List<Int>>>(2024, 2) {
    override fun parse(input: List<String>): List<List<Int>> {
        return input.map { line -> line
            .split(" ")
            .map(String::toInt)
        }
    }

    override fun part1(data: List<List<Int>>): Int =
        data.count { safe(it) }

    override fun part2(data: List<List<Int>>): Int =
        data.count { report ->
            safe(report) || report.indices.any { i ->
                safe(report.filterIndexed { idx, _ -> idx != i })
            }
        }


    private fun safe(levels: List<Int>): Boolean {
        val diffs = levels.zipWithNext { a, b -> b - a }
        val increasing = diffs.first() > 0

        return diffs.all { diff ->
            val correctDirection = if (increasing) diff > 0 else diff < 0
            abs(diff) in 1..3 && correctDirection
        }
    }
}

fun main() = Day02.run()