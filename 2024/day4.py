from utils import Processor, parse_matrix
import os
DAY = os.path.basename(__file__)[3:-3]


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


# def find_neighbors(pos, data):
#     # Define the 8 possible directions
#     directions = [
#         (-1, -1), (-1, 0), (-1, 1),
#         (0, -1),            (0, 1),
#         (1, -1), (1, 0), (1, 1)
#     ]
#
#     neighbors = []
#     rows, cols = len(data), len(data[0])
#
#     for dr, dc in directions:
#         new_row, new_col = pos[0] + dr, pos[1] + dc
#         # Check if new indices are within bounds
#         if 0 <= new_row < rows and 0 <= new_col < cols:
#             neighbors.append((new_row, new_col))  # Store valid neighbors
#
#     return neighbors


def part2(data):
    pass


processor = Processor(parse, part1, part2, DAY)
processor.run_test(1, 18)
processor.execute(1)
