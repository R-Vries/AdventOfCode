package aoc.`2025`

import aoc.IOManager
import aoc.Range
import aoc.Runner
import aoc.Solver
import aoc.Tester

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

    override fun part1(data: Pair<List<Range>, List<Long>>): Number {
        return data.second.count { id ->
            data.first.any { range ->
                range.contains(id)
            }
        }
    }

    override fun part2(data: Pair<List<Range>, List<Long>>): Number {
        val ranges = data.first.sortedBy { it.start }
        val mergedRanges = mutableListOf(ranges[0])
        for (r in ranges.drop(1)) {
            val currentMax = mergedRanges.last().end
            if (currentMax >= r.start) {
                // overlap
                mergedRanges[mergedRanges.size - 1] = Range(mergedRanges.last().start, maxOf(r.end, currentMax))
            } else {
                // no overlap
                mergedRanges.add(r)
            }
        }
        return mergedRanges.sumOf { it.end - it.start + 1}
    }
}

fun main() {
    val io = IOManager(2025, 5)
    val tester = Tester(Day05, io, 3, 14)
    val runner = Runner(Day05, io)
    tester.runTests()
    runner.run()
}