package org.example

import kotlin.io.path.Path
import kotlin.io.path.readLines

fun readInput(name: String, year: Int): List<String> =
    Path("src/main/resources/$year/inputs/$name.txt").readLines()

fun readTest(name: String, year: Int): List<String> =
    Path("src/main/resources/$year/tests/$name.txt").readLines()
