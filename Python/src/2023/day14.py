from Python import utils
import numpy as np


def pretty_print(puzzle):
    for row in puzzle:
        print(''.join(row))


def move_row(row, row_above):
    changed = False
    for i in range(0, len(row)):
        if row_above[i] == '.' and row[i] == 'O':
            row_above[i] = 'O'
            row[i] = '.'
            changed = True
    return row, row_above, changed


def roll(puzzle, direction):
    if direction == 'west':
        def back(p):
            return np.array(p).T.tolist()
        puzzle = np.array(puzzle).T.tolist()
    elif direction == 'east':
        def back(p):
            return np.rot90(np.array(p), k=3).tolist()
        puzzle = np.rot90(np.array(puzzle)).tolist()
    elif direction == 'south':
        def back(p):
            return np.rot90(np.array(p), k=2).tolist()
        puzzle = np.rot90(np.array(puzzle), k=2).tolist()
    else:
        def back(p):
            return p
    changed = True
    while changed:
        changed = False
        for i, row in enumerate(puzzle):
            changed_row = False
            if i != 0:
                puzzle[i], puzzle[i - 1], changed_row = move_row(row, puzzle[i - 1])
            if changed_row:
                changed = True
    return back(puzzle)


def cycle(puzzle):
    for direction in ['north', 'west', 'south', 'east']:
        puzzle = roll(puzzle, direction)
    return puzzle


def solve(puzzle):
    original = puzzle
    i = 0
    mapping = {}
    while True:
        if str(puzzle) in mapping:
            period = i - mapping[str(puzzle)]
            break
        mapping.update({str(puzzle): i})
        puzzle = cycle(puzzle)
        i += 1
    puzzle = original
    number = i - period - 1
    for i in range(0, number):
        puzzle = cycle(puzzle)
    remainder = (1000000000 - number) % period
    for i in range(0, remainder):
        puzzle = cycle(puzzle)
    return sum(r.count('O') * (len(puzzle) - i) for i, r in enumerate(puzzle))


p = utils.Processor(utils.parse_matrix, solve, 14)
parsed = utils.parse_matrix(p.get_test())
p.execute()
