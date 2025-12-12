package aoc

data class Range(var start: Long, var end: Long) {
    fun contains(value: Long): Boolean =
        value in start..end
}

/** Creates a list of all possible pairs in the list */
fun <T> pairs(list: List<T>): List<Pair<T, T>> =
    list.indices.flatMap { i ->
        (i + 1 until list.size).map { j ->
            list[i] to list[j]
        }
    }