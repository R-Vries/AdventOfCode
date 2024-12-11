from Processor import Processor
from utils import parse_matrix, move
import os
DAY = os.path.basename(__file__)[3:-3]

PART = 2
TEST_RESULTS = [41, 6]

NEXT_DIR = {'U': 'R', 'R': 'D', 'D': 'L', 'L': 'U'}
OPPOSITE_DIR = {'U': 'D', 'D': 'U', 'L': 'R', 'R': 'L'}


def parse(file):
    return parse_matrix(file)


def part1(data):
    current = get_start(data)
    visited = set()  # set of visited coordinates (i, j)
    visited.add(current)
    direction = 'U'
    while in_field(data, current):
        while data[current[0]][current[1]] != '#':
            visited.add(current)
            current = move(direction, current)
            if not in_field(data, current):
                return len(visited)
        current = move(OPPOSITE_DIR[direction], current)
        direction = NEXT_DIR[direction]


def get_start(data):
    for i, sublist in enumerate(data):
        if '^' in sublist:
            return i, sublist.index('^')


def in_field(data, pos):
    i, j = pos
    return 0 <= i < len(data) and 0 <= j < len(data[0])


def part2(data):
    # Detect loop by keeping track of list of visited positions with direction? Same pos/dir means loop?
    loop_points = 0

    for i, row in enumerate(data):
        for j, obstacle in enumerate(row):
            valid = True
            visited = {}
            current = get_start(data)
            direction = 'U'
            while valid:
                while data[current[0]][current[1]] != '#' and current != (i, j):
                    if current in visited:
                        if direction in visited[current]:
                            loop_points += 1
                            valid = False
                            break
                        else:
                            visited[current].add(direction)
                    else:
                        visited.update({current: set(direction)})
                    current = move(direction, current)
                    if not in_field(data, current):
                        valid = False
                        break
                current = move(OPPOSITE_DIR[direction], current)
                direction = NEXT_DIR[direction]
    return loop_points


processor = Processor(DAY, parse, part1, part2)
processor.run()
