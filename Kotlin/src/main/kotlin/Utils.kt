package aoc

data class Range(var start: Long, var end: Long) {
    fun contains(value: Long): Boolean =
        value in start..end
}