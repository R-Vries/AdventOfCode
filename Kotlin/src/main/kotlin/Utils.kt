package org.example

fun String.toGreen(): String  {
    val green = "\u001b[32m"
    val reset = "\u001b[0m"
    return green + this + reset
}