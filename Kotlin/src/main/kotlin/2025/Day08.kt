package aoc.`2025`

import aoc.IOManager
import aoc.Runner
import aoc.Solver
import aoc.Tester
import kotlin.math.sqrt

object Day08: Solver<List<Day08.Coordinate3D>>() {
    override fun parse(input: List<String>): List<Coordinate3D> =
        input.map { line ->
            val split = line.split(",")
            Coordinate3D(split[0].toInt(), split[1].toInt(), split[2].toInt())
        }

    override fun part1(data: List<Coordinate3D>): Number {
        // create sorted list of pairs based on distance
        val pairs: MutableList<Pair<Coordinate3D, Coordinate3D>> = mutableListOf()
        for (i in 0..data.lastIndex) {
            val s = data[i]

            for (j in (i + 1)..data.lastIndex) {
                val p = Pair(s, data[j])
                pairs.add(p)
            }
        }
        pairs.sortBy { distance(it.first, it.second) }
        // create circuits
        val circuits = mutableSetOf<MutableSet<Coordinate3D>>()
        var connected = 0
        for (pair in pairs) {
            var done = false
            for (c in circuits) {
                if (pair.first in c && pair.second in c) {
                    done = true
                    break
                }
                if (pair.first in c || pair.second in c) {
                    c.add(pair.first)
                    c.add(pair.second)
                    done = true
                    connected++
                    break
                }
            }
            if (!done) {
                circuits.add(mutableSetOf(pair.first, pair.second))
                connected++
            }
            if (connected == 1000) {
                break
            }
        }
        // 94752000 & 17825 & 31752
        return circuits
            .sortedByDescending { it.size }
            .take(3)
            .map { it.size }
            .reduce { a, b -> a * b }
    }

    override fun part2(data: List<Coordinate3D>): Number {
        TODO("Not yet implemented")
    }

    data class Coordinate3D(val x: Int, val y: Int, val z: Int)

    fun distance(c1: Coordinate3D, c2: Coordinate3D): Int =
        square(c1.x - c2.x) + square(c1.y - c2.y) + square(c1.z - c2.z)

    fun square(n: Int) = n * n

}

fun main() {
    val io = IOManager(2025, 8)
    val tester = Tester(Day08, io, 40, null)
    val runner = Runner(Day08, io)
//    tester.runTests()
    runner.run()
}