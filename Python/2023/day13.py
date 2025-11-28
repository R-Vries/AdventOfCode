from Python import utils
from itertools import groupby
import numpy as np


def parse(input):
    lines = list(input)
    sections = (list(g) for _, g in groupby(lines, key='\n'.__ne__))
    return [list(map(lambda x: x.strip(), a)) for a in list(sections) if len(a) > 1]


def check_rows(puzzle):
    # first find two connecting duplicate rows
    pairs = []
    for i, row1 in enumerate(puzzle):
        if i + 1 < len(puzzle) and row1 == puzzle[i + 1]:
            pairs.append((i, i + 1))
    if len(pairs) == 0:
        return -1
    else:   # multiple pairs, only one option
        for pair in pairs:
            i = 1
            correct = True
            while pair[0] - i >= 0 and pair[1] + i < len(puzzle):
                if puzzle[pair[0] - i] != puzzle[pair[1] + i]:
                    correct = False
                    break
                i += 1
            if correct:
                return 100 * (pair[0] + 1)
    return -1


def check_rows2(puzzle):
    pairs = {}      # {(row1, row2): made_change}
    for i, row in enumerate(puzzle):
        if i + 1 < len(puzzle):
            if row == puzzle[i + 1]:
                pairs.update({(i, i + 1): False})
            elif one_difference(row, puzzle[i + 1]):
                pairs.update({(i, i + 1): True})
    if len(pairs) == 0:
        return -1
    else:
        result = -1
        for pair in pairs:
            i = 1
            made_change = pairs[pair]
            correct = True
            while pair[0] - i >= 0 and pair[1] + i < len(puzzle):
                r1, r2 = puzzle[pair[0] - i], puzzle[pair[1] + i]
                if r1 != r2:
                    if not made_change and one_difference(r1, r2):
                        made_change = True
                    else:
                        correct = False
                        break
                i += 1
            if correct:
                original = check_rows(puzzle)
                amount = 100 * (pair[0] + 1)
                result = amount if amount != original else result
        return result


def one_difference(row1, row2):
    return sum(list(x != y for x, y in zip(row1, row2))) == 1


def check_cols(puzzle):
    transpose_puzzle = np.array(list(map(list, puzzle))).T
    transpose_puzzle = list(map(lambda x: ''.join(x), transpose_puzzle))
    result = check_rows2(transpose_puzzle)
    return result // 100 if result > 0 else -1


def solve(puzzle):
    return sum(max(check_rows2(pattern), check_cols(pattern)) for pattern in puzzle)


p = utils.Processor(parse, solve, 13)
p.execute()
