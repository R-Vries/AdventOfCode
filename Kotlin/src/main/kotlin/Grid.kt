package aoc

/**
 * A 2D grid of mutable lists.
 *
 * The grid is constructed from the AoC input.
 * The grid can contain elements of any [T].
 * A constructor for a Grid<Char> already exists
 *
 * The grid provides convenient access basic iteration functions, working with [Coordinate]s and utilities such as
 * counting neighbors.
 *
 * ### Basic usage:
 * ```
 * val grid = Grid(input)      // Grid<Char>
 *
 * val value = grid[Coordinate(2, 3)]
 * ```
 *
 * ### Iteration:
 * ```
 * for (value in grid) { ... }                  // iterate values
 * for (coord in grid.coordinates()) { ... } // iterate coordinates
 * ```
 *
 * @param T the element type stored in the grid
 *
 * @property data the underlying 2D structure containing grid values
 * @property rows the number of rows in the grid
 * @property cols the number of columns in the grid
 */

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

    fun values(): Sequence<T> =
        sequence {
            for (i in 0 until rows) {
                for (j in 0 until cols) {
                    yield(data[i][j])
                }
            }
        }

    fun indexedValues(): Sequence<Pair<Coordinate, T>> =
        sequence {
            for (i in 0 until rows) {
                for (j in 0 until cols) {
                    yield(Coordinate(i, j) to data[i][j])
                }
            }
        }

    fun inBounds(i: Int, j: Int): Boolean =
        i in 0 until rows && j in 0 until cols

    fun inBounds(c: Coordinate): Boolean =
        inBounds(c.i, c.j)

    /** Returns all 8 surrounding neighbours of this [Coordinate]  */
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

/** Class representing a coordinate in a grid */
data class Coordinate(val i: Int, val j: Int)

/**
 * Constructs a Char Grid using the regular input.
 *
 * @param input the lines of the input file
 * @return a new [Grid] of [Char]s
 */
fun Grid(input: List<String>): Grid<Char> =
    Grid(input.map { line -> line.toMutableList() }.toMutableList())
