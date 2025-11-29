package aoc

const val reset = "\u001b[0m"

fun String.toGreen(): String  {
    val green = "\u001b[32m"
    return green + this + reset
}

fun String.toBold(): String {
    val bold = "\u001b[1;97m"
    return bold + this + reset
}

fun String.toYellow(): String {
    val yellow = "\u001b[33m"
    return yellow + this + reset
}