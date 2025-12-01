package aoc.`2025`

import aoc.Solver

object Day01: Solver<List<Move>>(2025, 1, 3, 16) {
    override fun parse(input: List<String>): List<Move> =
        input.map { line -> Move(line.take(1), line.substring(1).toInt()) }

    override fun part1(data: List<Move>): Int {
        var visitedZero = 0
        var current = 50
        data.forEach { move ->
            if (current == 0) visitedZero++
            current = when (move.direction) {
                "L" -> Math.floorMod(current - move.amount, 100)
                "R" -> Math.floorMod(current + move.amount, 100)
                else -> current
            }
        }
        return visitedZero
    }

    override fun part2(data: List<Move>): Int {
        var visitedZero = 0
        var current = 50
        for (move in data) {
            val change = when (move.direction) {
                "L" -> -move.amount
                "R" ->  move.amount
                else -> 0
            }
            visitedZero += passesZero(current, move)
            current = Math.floorMod(current + change, 100)
        }
        return visitedZero
    }

    fun passesZero(current: Int, move: Move): Int {
        var cur = current
        var visitedZero = 0
        val step = if (move.direction == "L") -1 else 1

        repeat(move.amount) {
            cur = Math.floorMod(cur + step, 100)
            if (cur == 0) visitedZero++
        }
        return visitedZero
    }
}

data class Move(val direction: String, val amount: Int)

fun main() {
    Day01.runTests()
    Day01.run()
}