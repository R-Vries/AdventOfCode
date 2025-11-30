package aoc
import java.io.File
import java.net.HttpURLConnection
import java.net.URI
import kotlin.io.path.Path
import kotlin.io.path.exists
import kotlin.io.path.readLines
import kotlin.io.path.writeText

class IOManager(val year: Int, val day: Int) {
    private val dayString = "%02d".format(day)

    /**
     * Get the lines from the input. Fetches the input from the website if the file doesn't exist yet
     *
     * @return a list containing each line in the input file as a string
     */
    fun readInput(): List<String> {
        val inputFile = Path("src/main/resources/$year/inputs/day$dayString.txt")
        if (!inputFile.exists()) {
            println("Input file not found. Downloading from AoC...")
            fetchInput()
        }
        return inputFile.readLines()
    }

    /** Get the lines in the test file */
    fun readTest(): List<String> {
        val inputFile = Path("src/main/resources/$year/tests/day$dayString.txt")
        if (!inputFile.exists()) {
            fetchTest()
        }
        return inputFile.readLines()
    }

    /** Write the elapsed times of both parts to the results markdown file */
    fun updateTimes(part1: Long, part2: Long) {
        val file = Path("results/$year.md")

        // Read all lines, or start with header if file does not yet exist
        val lines = if (file.exists()) file.readLines().toMutableList()
        else mutableListOf(
            "| Day | Part 1 (ms) | Part 2 (ms) | Total (ms) |",
            "|-----|-------------|-------------|------------|"
        )

        val dayString = day.toString()
        val total = part1 + part2

        val newRow = "| $dayString | $part1 | $part2 | $total |"

        val existingIndex = lines.indexOfFirst { it.startsWith("| $dayString |") }

        if (existingIndex != -1) {
            lines[existingIndex] = newRow
        } else {
            // Insert row in correct place (sorted by day)
            val insertIndex = lines
                .drop(2)                         // skip header
                .indexOfFirst {
                    val d = it.split("|")[1].trim().toInt()
                    day < d
                }
                .let { if (it == -1) lines.size else it + 2 }

            lines.add(insertIndex, newRow)
        }
        file.writeText(lines.joinToString("\n"))
    }

    /** Fetches the file from the Advent of Code website */
    private fun fetchInput() {
        val cookieFile = File("../cookie.txt")
        require(cookieFile.exists()) {
            "Missing cookie.txt in project root. Place your AoC session cookie inside."
        }

        val sessionCookie = cookieFile.readText().trim()
        require(sessionCookie.isNotEmpty()) { "cookie.txt is empty." }

        val url = URI("https://adventofcode.com/$year/day/$day/input").toURL()
        val connection = (url.openConnection() as HttpURLConnection).apply {
            requestMethod = "GET"
            setRequestProperty("Cookie", "session=$sessionCookie")
        }

        val response = connection.inputStream.bufferedReader().readText()

        val targetFile = File("src/main/resources/$year/inputs/day$dayString.txt")
        targetFile.parentFile.mkdirs()
        targetFile.writeText(response)

        println("Downloaded input to ${targetFile.path}")
    }

    /** Get the test input through the command line. Also saves it to a file */
    private fun fetchTest() {
        println("Paste test input below. End with two empty lines:".toYellow())

        val lines = mutableListOf<String>()
        var emptyCount = 0

        while (true) {
            val line = readlnOrNull() ?: break

            if (line.isEmpty()) {
                emptyCount++
                if (emptyCount >= 2) break
            } else {
                emptyCount = 0
            }

            lines += line
        }

        val targetFile = File("src/main/resources/$year/tests/day$dayString.txt")
        targetFile.parentFile.mkdirs()

        targetFile.writeText(lines.joinToString("\n"))

        println("Input saved to ${targetFile.path}".toYellow())
    }
}