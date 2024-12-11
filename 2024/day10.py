from Processor import Processor
import os
from utils import in_map
DAY = os.path.basename(__file__)[3:-3]


def parse(file):
    topo_map, trail_heads = [], []
    for i, line in enumerate(file):
        i = int(i)
        topo_map.append([])
        for j, n in enumerate(line.strip()):
            j = int(j)
            n = int(n)
            if n == 0:
                trail_heads.append((i, j))
            topo_map[i].append(n)
    return topo_map, trail_heads


def part1(data):
    topo_map = data[0]
    start_points = data[1]
    paths = 0
    for start in start_points:
        queue = [(topo_map[start[0]][start[1]], start[0], start[1])]  # (value, i, j)
        visited = []
        while queue:
            val, i, j = queue.pop(0)
            if val == 9 and (i, j) not in visited:
                visited.append((i, j))
                paths += 1
            if (val, i, j) in queue:
                continue
            for di, dj in {(1, 0), (0, 1), (-1, 0), (0, -1)}:
                a, b = (i + di, j + dj)
                if in_map((a, b), topo_map) and topo_map[a][b] == val + 1:
                    queue.append((val + 1, a, b))
    return paths


def part2(data):
    topo_map = data[0]
    start_points = data[1]
    paths = 0
    for start in start_points:
        queue = [(topo_map[start[0]][start[1]], start[0], start[1])]  # (value, i, j)
        visited = []
        while queue:
            val, i, j = queue.pop(0)
            if val == 9:
                visited.append((i, j))
                paths += 1
            for di, dj in {(1, 0), (0, 1), (-1, 0), (0, -1)}:
                a, b = (i + di, j + dj)
                if in_map((a, b), topo_map) and topo_map[a][b] == val + 1:
                    queue.append((val + 1, a, b))
    return paths


processor = Processor(DAY, parse, part1, part2)
processor.run()
