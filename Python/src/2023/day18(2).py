from Python.utils import Processor, move
from shapely import Polygon


def parse(input):
    return [(d, int(a), c[1:8]) for d, a, c in map(lambda x: x.split(' '), input)]


def solve(puzzle):
    i, j = 0, 0  # current location
    exterior_points = 0
    pts = set()
    pts.add((0, 0))
    xs = []
    ys = []
    for _, _, c in puzzle:
        a, d = retrieve(c)
        i, j = move(d, (i, j), a)
        exterior_points += a
        xs.append(i)
        ys.append(j)
    area = Polygon(list(zip(xs, ys))).area
    # Picks theorem: A = i + b/2 - 1, where i = interior points, b = boundary points
    interior_points = area + 1 - exterior_points // 2
    return int(interior_points + exterior_points)


def retrieve(color):
    directions = {0: 'R', 1: 'D', 2: 'L', 3: 'U'}
    return int(color[1:6], 16), directions[int(color[6])]


p = Processor(parse, solve, 18)
p.execute()
