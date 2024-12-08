from Processor import Processor
from utils import parse_matrix
from itertools import combinations
import os
DAY = os.path.basename(__file__)[3:-3]

PART = 2
TEST_RESULTS = [14, 34]


def parse(file):
    return parse_matrix(file)


def part1(data):
    # Group all coordinates of matching characters
    positions = {}
    for i, row in enumerate(data):
        for j, char in enumerate(row):
            if char != '.':
                positions.setdefault(char, []).append((i, j))
    antinodes = set()
    for points in positions.values():
        pairs = combinations(points, 2)
        for p1, p2 in pairs:
            p1x, p1y, p2x, p2y = p1[0], p1[1], p2[0], p2[1]
            di, dj = p1x - p2x, p1y - p2y
            upper_point = p1x + di, p1y + dj
            if in_map(upper_point, data):
                antinodes.add(upper_point)
            lower_point = p2x - di, p2y - dj
            if in_map(lower_point, data):
                antinodes.add(lower_point)
    return len(antinodes)


def in_map(point, data):
    row, col = point[0], point[1]
    return col in range(len(data[0])) and row in range(len(data))


def part2(data):
    # Group all coordinates of matching characters
    positions = {}
    for i, row in enumerate(data):
        for j, char in enumerate(row):
            if char != '.':
                positions.setdefault(char, []).append((i, j))
    antinodes = set()
    for points in positions.values():
        pairs = combinations(points, 2)
        for p1, p2 in pairs:
            antinodes.add(p1)
            antinodes.add(p2)
            p1x, p1y, p2x, p2y = p1[0], p1[1], p2[0], p2[1]
            di, dj = p1x - p2x, p1y - p2y
            upper_point = p1x + di, p1y + dj
            while in_map(upper_point, data):
                antinodes.add(upper_point)
                upper_point = upper_point[0] + di, upper_point[1] + dj
            lower_point = p2x - di, p2y - dj
            while in_map(lower_point, data):
                antinodes.add(lower_point)
                lower_point = lower_point[0] - di, lower_point[1] - dj
    return len(antinodes)


processor = Processor(parse, part1, part2, DAY)
processor.run_test(PART, TEST_RESULTS[PART - 1])
processor.execute(PART)
