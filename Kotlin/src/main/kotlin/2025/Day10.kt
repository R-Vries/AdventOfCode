package aoc.`2025`

import aoc.IOManager
import aoc.Solver
import aoc.Tester

object Day10: Solver<List<Day10.Machine>>() {
    override fun parse(input: List<String>): List<Machine> =
        input.map { line ->
            val lights = line
                .substringAfter("[")
                .substringBefore("]")
                .toList()
            val buttons = line
                .split("(")
                .drop(1)
                .map { it.substringBefore(")") }
                .map { button -> button
                    .split(",")
                    .map { it.toInt() } }
            val joltages = line
                .substringAfter("{")
                .substringBefore("}")
                .split(',')
                .map { it.toInt() }
            Machine(lights, buttons, joltages)
        }

    override fun part1(data: List<Machine>): Number {
        TODO("Not yet implemented")
    }

    override fun part2(data: List<Machine>): Number {
        TODO("Not yet implemented")
    }

    data class Machine(val lights: List<Char>, val buttons: List<List<Int>>, val joltages: List<Int>)
}



fun main() {
    val io = IOManager(2025, 10)
    val tester = Tester(Day10, io, null, null)
    tester.printInput()
}