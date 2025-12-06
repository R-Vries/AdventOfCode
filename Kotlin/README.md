# Advent of Code - Kotlin Framework

This is a Kotlin-based framework for solving Advent of Code puzzles. 
It provides utilities for input management, testing, and timing.

## Features

- Solver abstraction: Define puzzle solutions via parse(), part1(), part2().
- Tester: Run tests with expected answers, or skip tests when unknown.
- IOManager: Handles fetching puzzle input, reading test data, and writing results.
- Runner: Executes solvers with timing and prints formatted summaries.
- Timing: Measure execution times for parts and log to a results markdown file.

## Usage
### 1. Create a solver
```kotlin
class Day01 : Solver<Int> {
    override fun parse(input: List<String>) = input.sumOf { it.toInt() }
    override fun part1(data: Int) = data + 1
    override fun part2(data: Int) = data * 2
}
```
### 2. Run tests
```kotlin
val tester = Tester(2024, 1, Day01(), test1Answer = 42, test2Answer = 84)
tester.runAllTests()   // skips tests if answers not provided
```

### 3. Run puzzle

```kotlin
val runner = Runner(2024, 1, Day01(), IOManager(2024, 1))
runner.run()  // prints Part 1, Part 2, timing, and updates results file
```