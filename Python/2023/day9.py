from Python import utils


def parse(input):
    return [list(map(int, line.split(' '))) for line in input]


def solve(puzzle):
    return sum(map(solve_line2, puzzle))


def solve_line(sequence):
    last = sequence[len(sequence) - 1]
    if all(i == sequence[0] for i in sequence):
        return last
    return last + solve_line(differences(sequence))


def solve_line2(sequence):
    first = sequence[0]
    if all(i == first for i in sequence):
        return first
    return first - solve_line2(differences(sequence))


def differences(list):
    return [list[i] - list[i - 1] for i in range(1, len(list))]


p = utils.Processor(parse, solve, 9)
p.execute()
