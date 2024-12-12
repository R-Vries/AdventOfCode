from Processor import Processor
from utils import parse_matrix, in_map
import os

DAY = os.path.basename(__file__)[3:-3]


def parse(file):
    return parse_matrix(file)


def part1(data):
    return sum(perimeter(plot) * len(plot) for plot in get_plots(data))


def get_plots(data):
    visited = set()  # Keep track of all points that are already in a region
    plots = []
    for i, row in enumerate(data):
        for j, letter in enumerate(row):
            if (i, j) not in visited:
                queue = [(letter, (i, j))]
                plot = set()
                while queue:
                    current, point = queue.pop()
                    plot.add(point)
                    visited.add(point)
                    for di, dj in {(1, 0), (0, 1), (-1, 0), (0, -1)}:
                        a, b = (point[0] + di, point[1] + dj)
                        if in_map((a, b), data) and data[a][b] == letter and (a, b) not in visited:
                            queue.append((letter, (a, b)))
                plots.append(plot)
    return plots


def perimeter(points):
    edges = 0
    for (i, j) in points:
        for di, dj in {(1, 0), (0, 1), (-1, 0), (0, -1)}:
            if (i + di, j + dj) not in points:
                edges += 1
    return edges


def sides(points):
    corners = 0
    for p in points:
        U = ((p[0], p[1] - 1) in points)
        R = ((p[0] + 1, p[1]) in points)
        D = ((p[0], p[1] + 1) in points)
        L = ((p[0] - 1, p[1]) in points)
        UR = ((p[0] + 1, p[1] - 1) in points)  # diagonal Up Right
        DR = ((p[0] + 1, p[1] + 1) in points)
        UL = ((p[0] - 1, p[1] - 1) in points)
        DL = ((p[0] - 1, p[1] + 1) in points)

        if not U and not R and not D and not L:
            corners += 4

        if U and not R and not D and not L:
            corners += 2
        if R and not D and not L and not U:
            corners += 2
        if D and not L and not U and not R:
            corners += 2
        if L and not U and not R and not D:
            corners += 2

        if D and R and not U and not L:
            corners += 1
        if D and L and not U and not R:
            corners += 1
        if U and R and not D and not L:
            corners += 1
        if U and L and not D and not R:
            corners += 1

        if R and U and not UR:
            corners += 1
        if R and D and not DR:
            corners += 1
        if L and U and not UL:
            corners += 1
        if L and D and not DL:
            corners += 1
    return corners


def construct_grid(points):
    # Find the bounds of the grid
    min_x = min(x for x, y in points)
    max_x = max(x for x, y in points)
    min_y = min(y for x, y in points)
    max_y = max(y for x, y in points)

    # Construct the grid
    grid = ['.' for x in range(min_x, max_x + 1) for y in range(min_y, max_y + 1)]
    return grid


def part2(data):
    return sum(sides(plot) * len(plot) for plot in get_plots(data))


processor = Processor(DAY, parse, part1, part2)
processor.run()
