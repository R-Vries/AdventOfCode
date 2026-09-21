# Advent of Code - Java Framework

This is a java-based framework for solving the Advent of Code problems.
It can be run directly from the IDE and does not use Maven or Gradle. 

Each puzzle implements the `Solver` interface. A `parse`, `part1` and `part2` implementation are needed.

## Running a solution
`Main` is used to run the puzzle on the full input. 
Provide the year and day as program arguments:
```2025 5```.

## Running tests
`TestMain` is used for testing puzzle examples separately from running the actual solution.
Besides the year and day, you can optionally provide the test answers for both parts: 
```2025 5 118 8092```.
The test input will be read from the console. 
Copy it from the AoC website and paste it into the console, ending with two empty lines.

## Fetching input
The framework supports automatically fetching the input from the AoC website.
The session cookie is required, see the instructions from the general README for more details.
