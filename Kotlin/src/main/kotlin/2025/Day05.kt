package aoc.`2025`

import aoc.IOManager
import aoc.Range
import aoc.Runner
import aoc.Solver
import aoc.Tester

/**
 * Learned to use [joinToString], [sortedBy], and [fold]
 */
object Day05: Solver<Pair<List<Range>, List<Long>>>() {
    override fun parse(input: List<String>): Pair<List<Range>, List<Long>> {
        val (rangeLines, valueLines) = input
            .joinToString("\n")
            .split("\n\n")
            .map { it.lines() }

        val ranges = rangeLines.map { line ->
            val (start, end) = line.split("-").map { it.toLong()}
            Range(start, end)
        }

        val values = valueLines.map { it.toLong() }

        return Pair(ranges, values)
    }

    override fun part1(data: Pair<List<Range>, List<Long>>) =
        data.second.count { id ->
            data.first.any { range ->
                range.contains(id)
            }
    }

    override fun part2(data: Pair<List<Range>, List<Long>>): Number {
        val merged = data.first
            .sortedBy { it.start }
            .fold(mutableListOf<Range>()) { acc, r ->
                if (acc.isEmpty()) {
                    acc.add(r)
                } else {
                    val last = acc.last()
                    if (r.start <= last.end) {
                        acc[acc.lastIndex] = Range(last.start, maxOf(last.end, r.end))
                    } else {
                        acc += r
                    }
                }
                acc
            }
        return merged.sumOf { it.end - it.start + 1 }
    }
}

fun main() {
    val io = IOManager(2025, 5)
    val tester = Tester(Day05, io, 3, 14)
    val runner = Runner(Day05, io)
    tester.runTests()
    runner.run()
}