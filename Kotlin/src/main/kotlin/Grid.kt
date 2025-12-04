package aoc

class Grid<T>(
    private val data: MutableList<MutableList<T>>
): Iterable<T> {
    val rows = data.size
    val cols = data[0].size

    operator fun get(i: Int, j: Int): T = data[i][j]
    operator fun set(i: Int, j: Int, value: T) {
        data[i][j] = value
    }

    operator fun get(c: Coordinate): T = data[c.i][c.j]
    operator fun set(c: Coordinate, value: T) {
        data[c.i][c.j] = value
    }

    override fun iterator(): Iterator<T> =
        sequence {
            for (row in data) {
                for (value in row) {
                    yield(value)
                }
            }
        }.iterator()

    fun coordinates(): Sequence<Coordinate> =
        sequence {
            for (i in 0 until rows)
                for (j in 0 until cols)
                    yield(Coordinate(i, j))
        }


    fun inBounds(i: Int, j: Int): Boolean =
        i in 0 until rows && j in 0 until cols

    fun inBounds(c: Coordinate): Boolean =
        inBounds(c.i, c.j)

    /** Returns all 8 neighbours  */
    fun getNeighbours(c: Coordinate): List<Coordinate> =
        listOf(
            Coordinate(c.i - 1, c.j - 1),
            Coordinate(c.i - 1, c.j),
            Coordinate(c.i - 1, c.j + 1),
            Coordinate(c.i, c.j - 1),
            Coordinate(c.i, c.j + 1),
            Coordinate(c.i + 1, c.j - 1),
            Coordinate(c.i + 1, c.j),
            Coordinate(c.i + 1, c.j + 1),
        ).filter { inBounds(it) }

    override fun toString(): String =
        data.joinToString("\n") { row -> row.joinToString("") }


}

data class Coordinate(val i: Int, val j: Int)

/**
 * Constructs a Char Grid using the regular input.
 *
 * @param input the lines of the input file
 * @return a new Grid<Char>
 */
fun Grid(input: List<String>): Grid<Char> {
    val data = input.map { line -> line.toMutableList() }.toMutableList()
    return Grid(data)
}

fun <T> Grid(input: List<String>, transform: (Char) -> T): Grid<T> {
    val data = input.map { line ->
        line.map(transform).toMutableList()
    }.toMutableList()

    return Grid(data)
}

