from Processor import Processor
from utils import parse_matrix, move, in_map
import os
DAY = os.path.basename(__file__)[3:-3]

PART = 2
TEST_RESULTS = [41, 6]

NEXT_DIR = {'U': 'R', 'R': 'D', 'D': 'L', 'L': 'U'}
OPPOSITE_DIR = {'U': 'D', 'D': 'U', 'L': 'R', 'R': 'L'}


def parse(file):
    return parse_matrix(file)


def part1(data):
    return len(set(find_path(data)))


def find_path(data):
    current = get_start(data)
    visited = []  # list of visited coordinates (i, j)
    direction = 'U'
    while in_map(current, data):
        while data[current[0]][current[1]] != '#':
            visited.append(current)
            current = move(direction, current)
            if not in_map(current, data):
                return visited
        current = move(OPPOSITE_DIR[direction], current)
        direction = NEXT_DIR[direction]


def get_start(data):
    for i, sublist in enumerate(data):
        if '^' in sublist:
            return i, sublist.index('^')


def part2(data):
    loop_points = 0
    on_path = list(dict.fromkeys(find_path(data)))
    start = get_start(data)
    on_path.remove(start)
    for i, j in on_path:
        valid = True
        visited = {}
        current = start
        direction = 'U'
        while valid:
            while data[current[0]][current[1]] != '#' and current != (i, j):
                current = move(direction, current)
                if not in_map(current, data):
                    valid = False
                    break
            if current in visited:
                if direction in visited[current]:
                    loop_points += 1
                    break
                else:
                    visited[current].add(direction)
            else:
                visited.update({current: set(direction)})
            current = move(OPPOSITE_DIR[direction], current)
            direction = NEXT_DIR[direction]
    return loop_points


processor = Processor(DAY, parse, part1, part2)
processor.run_test(PART, TEST_RESULTS[PART - 1])
# processor.execute(PART)
processor.run()
