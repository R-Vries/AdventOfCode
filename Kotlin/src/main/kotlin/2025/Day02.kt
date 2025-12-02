package aoc.`2025`

import aoc.IOManager
import aoc.Runner
import aoc.Solver
import aoc.Tester

object Day02: Solver<List<Day02.Range>>() {
    override fun parse(input: List<String>): List<Range> =
        // input is one line
        input.first()
            .split(',')
            .map { range ->
                range.split('-').let { (a, b) ->
                    Range(a.toLong(), b.toLong())
                }
            }


    override fun part1(data: List<Range>): Long {
        var result = 0L
        for (r in data) {
            for (id in r.start..r.end) {
                val idString = id.toString()
                if (idString.length % 2 != 0) continue

                val middle = idString.length / 2
                if (idString.take(middle) == idString.substring(middle)) {
                    result += id
                }
            }
        }
        return result
    }

    override fun part2(data: List<Range>): Long {
        var result = 0L
        for (r in data) {
            for (id in r.start..r.end) {
                val idString = id.toString()

                for (i in 1..idString.length / 2) {
                    val sublists = idString.chunked(i)
                    if (sublists.all { it == sublists.first() }) {
                        result += id
                        break
                    }
                }
            }
        }
        return result
    }

    data class Range(val start: Long, val end: Long)
}

fun main() {
    val io = IOManager(2025, 2)
    val tester = Tester(Day02, io, 1227775554, 4174379265)
    val runner = Runner(Day02, io)
    tester.runTests()
    runner.run()
}