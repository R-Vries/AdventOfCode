from utils import Processor


directions = {'R': lambda x, y: (x, y + 1),
              'L': lambda x, y: (x, y - 1),
              'U': lambda x, y: (x - 1, y),
              'D': lambda x, y: (x + 1, y)}


def parse(input):
    return [(d, int(a), c[1:8]) for d, a, c in map(lambda x: x.split(' '), input)]


def solve(puzzle):
    result = 0
    ground = [['*'] + (['.'] * 1000) + ['*'] for _ in range(1000)]
    ground.insert(0, ['*'] * 1002)
    ground.append(['*'] * 1002)
    i, j = 1, 1
    for d, a, c in puzzle:
        for x in range(a):
            ground[i][j] = '#'
            i, j = directions[d](i, j)
            result += 1
    for row in ground:
        print(row)
    for i, row in enumerate(ground):
        for j, cell in enumerate(row):
            if is_inside((i, j), ground):
                result += 1
    return result


def solve2(puzzle):
    SIZE = 360
    result = 0
    points = []
    i, j = 0, 0
    for d, a, c in puzzle:
        for x in range(a):
            points.append((i, j))
            i, j = directions[d](i, j)
    # Find minimum and shift points
    lowestx = min(points)[0]
    lowesty = min(x[1] for x in points)
    print(f'Lowest x: {lowestx}, lowest y: {lowesty}')
    points = list(map(lambda x: (x[0] + abs(lowestx), x[1] + abs(lowesty)), points))
    print(f'max: {max(points)}, {max(x[1] for x in points)}')
    ground = [['*'] + (['.'] * SIZE) + ['*'] for _ in range(SIZE)]
    ground.insert(0, ['*'] * (SIZE + 2))
    ground.append(['*'] * (SIZE + 2))
    for x, y in points:
        ground[x][y] = '#'
        result += 1
    for row in ground:
        print(row)
    for i, row in enumerate(ground):
        for j, cell in enumerate(row):
            if is_inside((i, j), ground):
                result += 1
    return result


def is_inside(point, grid):
    inside = True
    i, j = point
    if grid[i][j] != '.':
        return False
    for direction in ['U', 'D', 'L', 'R']:
        check_i = i
        check_j = j
        while grid[check_i][check_j] == '.':
            check_i, check_j = directions[direction](check_i, check_j)
        if grid[check_i][check_j] == '*':
            inside = False
            break
    return inside


p = Processor(parse, solve2, 18)
p.execute()
