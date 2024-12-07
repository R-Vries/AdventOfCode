from Processor import Processor
from utils import parse_matrix
import os
DAY = os.path.basename(__file__)[3:-3]

PART = 2
TEST_RESULTS = [18, 9]


def parse(file):
    return parse_matrix(file)


def part1(data):
    result = 0
    for i, row in enumerate(data):
        for j, letter in enumerate(row):
            if letter == 'X':
                result += find_xmas(i, j, data)

    return result


def find_xmas(i, j, data):
    result = 0
    # top to bottom
    if i+3 < len(data) and check(i, j, (1, 0), data):
        result += 1
    # bottom to top
    if i-3 >= 0 and check(i, j, (-1, 0), data):
        result += 1
    # right to left
    if j + 3 < len(data[0]) and check(i, j, (0, 1), data):
        result += 1
    # left to right
    if j - 3 >= 0 and check(i, j, (0, -1), data):
        result += 1

    if i + 3 < len(data) and j + 3 < len(data[0]) and check(i, j, (1, 1), data):
        result += 1
    if i + 3 < len(data) and j - 3 >= 0 and check(i, j, (1, -1), data):
        result += 1
    if i - 3 >= 0 and j + 3 < len(data[0]) and check(i, j, (-1, 1), data):
        result += 1
    if i - 3 >= 0 and j - 3 >= 0 and check(i, j, (-1, -1), data):
        result += 1
    return result


def check(i, j, direction, data):
    # for going downwards for example: (1, 0)
    dy, dx = direction
    return data[i+1*dy][j+1*dx] == 'M' and data[i+2*dy][j+2*dx] == 'A' and data[i+3*dy][j+3*dx] == 'S'


def find_mas(i, j, data):
    corners = list(map(lambda point: data[point[0]][point[1]], find_corners((i, j), data)))
    return len(corners) == 4 and corners.count("M") == 2 and corners.count("S") == 2 \
        and (not corners[0] == corners[3]) and (not corners[1] == corners[2])


def find_corners(pos, data):
    directions = [
        (-1, -1),  (-1, 1), (1, -1),  (1, 1)
    ]

    corners = []
    rows, cols = len(data), len(data[0])

    for dr, dc in directions:
        new_row, new_col = pos[0] + dr, pos[1] + dc
        # Check if new indices are within bounds
        if 0 <= new_row < rows and 0 <= new_col < cols:
            corners.append((new_row, new_col))  # Store valid neighbors
    return corners


def part2(data):
    return sum(sum([1 for j, letter in enumerate(row) if letter == 'A' and find_mas(i, j, data)]) for i, row in enumerate(data))


processor = Processor(parse, part1, part2, DAY)
processor.run_test(PART, TEST_RESULTS[PART - 1])
processor.execute(PART)
