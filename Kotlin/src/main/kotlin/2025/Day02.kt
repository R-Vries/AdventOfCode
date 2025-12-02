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

    override fun part1(data: List<Range>): Long =
        data.sumOf { r ->
            (r.start..r.end).filter { id ->
                val s = id.toString()
                s.length % 2 == 0 && s.take(s.length / 2) == s.drop(s.length / 2)
            }.sum()
        }

    override fun part2(data: List<Range>): Long =
        data.sumOf { r ->
            (r.start..r.end).filter {id ->
                val s = id.toString()
                (1..s.length / 2).any { size ->
                    s.chunked(size).let { chunks ->
                        chunks.all { it == chunks.first() }
                    }
                }
            }.sum()
        }

    data class Range(val start: Long, val end: Long)
}

fun main() {
    val io = IOManager(2025, 2)
    val tester = Tester(Day02, io, 1227775554L, 4174379265)
    val runner = Runner(Day02, io)
    tester.runTests()
    runner.run()
}