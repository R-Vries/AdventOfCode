import time

from utils import Processor

next_direction = {('|', 'right'): ('up', 'down'), ('|', 'up'): ('up',), ('|', 'down'): ('down',),
                  ('|', 'left'): ('up', 'down'),
                  ('-', 'right'): ('right',), ('-', 'up'): ('left', 'right'), ('-', 'down'): ('left', 'right'),
                  ('-', 'left'): ('left',),
                  ('/', 'right'): ('up',), ('/', 'up'): ('right',), ('/', 'down'): ('left',), ('/', 'left'): ('down',),
                  ('\\', 'right'): ('down',), ('\\', 'up'): ('left',), ('\\', 'left'): ('up',), ('\\', 'down'): ('right',),
                  ('.', 'right'): ('right',), ('.', 'left'): ('left',), ('.', 'up'): ('up',), ('.', 'down'): ('down',)}

next_cell = {'right': lambda i: (i[0], i[1] + 1),
             'down': lambda i: (i[0] + 1, i[1]),
             'up': lambda i: (i[0] - 1, i[1]),
             'left': lambda i: (i[0], i[1] - 1)}


def parse(input):
    return list(map(lambda x: x.strip(), list(input)))


def beam(puzzle, start_row, start_col, incoming):
    first_direction = next_direction[(puzzle[start_row][start_col], incoming)]
    to_check = [((start_row, start_col), first_direction[0])]       # list of (index, direction) to check
    focused = []
    while len(to_check) > 0:
        current = to_check.pop(0)
        index, direction = current
        focused.append(current)
        cell = next_cell[direction](index)
        if 0 <= cell[0] < len(puzzle) and 0 <= cell[1] < len(puzzle[cell[0]]):
            new_directions = next_direction[(puzzle[cell[0]][cell[1]], direction)]
            for d in new_directions:
                next = (cell, d)
                if next not in focused and next not in to_check:
                    to_check.append(next)
    return len(set(x[0] for x in focused))


def solve(puzzle):
    return beam(puzzle, 0, 0, 'right')


def solve2(puzzle):
    start = time.time()
    directions = [[((i, 0), 'right') for i in range(len(puzzle))],
                  [((len(puzzle) - 1, i), 'up') for i in range(len(puzzle[0]))],
                  [((0, i), 'down') for i in range(len(puzzle))],
                  [((i, len(puzzle[0]) - 1), 'left') for i in range(len(puzzle[0]))]]
    maximum = max(beam(puzzle, i, j, direction) for path in directions for (i, j), direction in path)
    print('Elapsed time: ' + str(time.time() - start))
    return maximum


p = Processor(parse, solve2, 16)
p.execute()
