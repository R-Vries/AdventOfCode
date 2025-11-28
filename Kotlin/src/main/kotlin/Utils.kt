package org.example

import kotlin.io.path.Path
import kotlin.io.path.readLines

fun readInput(name: String): List<String> = Path("src/main/resources/inputs/$name.txt").readLines()