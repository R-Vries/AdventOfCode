package aoc.`2025`

import aoc.IOManager
import aoc.Runner
import aoc.Solver
import aoc.Tester
import aoc.pairs

object Day08: Solver<List<Day08.Coordinate3D>>() {
    override fun parse(input: List<String>): List<Coordinate3D> =
        input.map { line ->
            val split = line.split(",")
            Coordinate3D(split[0].toLong(), split[1].toLong(), split[2].toLong())
        }

    override fun part1(data: List<Coordinate3D>): Number {
        // create sorted list of pairs based on distance
        val pairs = pairs(data).sortedBy { distance(it.first, it.second) }
        // create circuits
        val circuits = data.map { setOf(it) }.toMutableSet()
        pairs.take(10).forEach { pair ->
            mergeSets(circuits, pair)
        }
        return circuits
            .sortedByDescending { it.size }
            .take(3)
            .map { it.size }
            .reduce { a, b -> a * b }
    }

    override fun part2(data: List<Coordinate3D>): Number {
        // create sorted list of pairs based on distance
        val pairs: MutableList<Pair<Coordinate3D, Coordinate3D>> = createPairs(data)
        pairs.sortBy { distance(it.first, it.second) }
        // create circuits
        val circuits = data.map { setOf(it) }.toMutableSet()
        pairs.forEach { pair ->
            mergeSets(circuits, pair)
            if (circuits.size == 1) {
                return pair.first.x * pair.second.x
            }
        }
        return -1
    }

    private fun mergeSets(
        circuits: MutableSet<Set<Coordinate3D>>,
        pair: Pair<Coordinate3D, Coordinate3D>
    ) {
        val set1 = circuits.first { it.contains(pair.first) }
        val set2 = circuits.first { it.contains(pair.second) }
        if (set1 != set2) {
            circuits.remove(set1)
            circuits.remove(set2)
            circuits.add(set1.union(set2))
        }
    }

    private fun createPairs(data: List<Coordinate3D>): MutableList<Pair<Coordinate3D, Coordinate3D>> {
        val pairs: MutableList<Pair<Coordinate3D, Coordinate3D>> = mutableListOf()
        for (i in 0..data.lastIndex) {
            val s = data[i]

            for (j in (i + 1)..data.lastIndex) {
                val p = Pair(s, data[j])
                pairs.add(p)
            }
        }
        return pairs
    }

    data class Coordinate3D(val x: Long, val y: Long, val z: Long)

    fun distance(c1: Coordinate3D, c2: Coordinate3D): Long =
        square(c1.x - c2.x) + square(c1.y - c2.y) + square(c1.z - c2.z)

    fun square(n: Long) = n * n

}

fun main() {
    val io = IOManager(2025, 8)
    val tester = Tester(Day08, io, 40, 25272)
    val runner = Runner(Day08, io)
    tester.runTests()
    runner.run()
}