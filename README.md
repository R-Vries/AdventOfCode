# Advent of Code

This repository contains my solutions to Advent of Code, implemented in three different languages:

- Kotlin
- Java
- Python

Each language has its own self-contained project located in a separate directory. This is because I use Advent of Code to practice (new) programming languages. In 2025, I am working in Kotlin.

## Repository Structure
```
AdventOfCode/
├── README.md            # You're reading this
├── cookie.txt           # (Ignored by Git) shared session cookie for all projects
│
├── Kotlin/              # Kotlin Gradle project
│   ├── README.md
│   └── src/...
│
├── Java/                # Java project (raw src)
│   ├── README.md
│   └── src/...
│
└── Python/              # Python project
    ├── README.md
    └── src/...
```

### cookie.txt

All three projects use Advent of Code requests requiring a session cookie.
A single cookie.txt is stored at the repository root and read by each project.
This file is git-ignored and must be created manually.

## Language-Specific Projects
### Kotlin Project (/Kotlin)

A full Gradle-based Kotlin project with structured packages, utilities, and solver classes. Contains solutions mostly for 2025 puzzles.

Read more in the [Kotlin/README.md](Kotlin/README.md)

### Python Project (/Python)

A Python project containing solutions mostly for 2024 and 2023 puzzles.

Read more in [Python/README.md](Python/README.md)

### Java Project (/Java)

A Java-based implementation containing solutions moslty for 2022. *Warning*: I made this when I just started programming so the code is quite ugly.

Read more in the [Java/README.MD](Java/README.md)

## Session Cookie Setup

To enable automatic input downloads from the Advent of Code website:

- Create a file named cookie.txt in the project root.
- Paste your session cookie into the file

**Important**: Never commit your actual session cookie.
It grants full access to your AoC account.