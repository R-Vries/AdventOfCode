from utils import Processor, move
from shapely import Polygon


def parse(input):
    return [line.strip() for line in input]


# https://stackoverflow.com/questions/5775352/python-return-2-ints-for-index-in-2d-lists-given-item
def index_2d(myList, v):
    for i, j in enumerate(myList):
        if v in j:
            return i, j.index(v)


def get_direction(index, previous):
    directions = {(-1, 0): 'U', (0, 1): 'R', (1, 0): 'D', (0, -1): 'L'}
    difference = (previous[0] - index[0], previous[1] - index[1])
    return directions[difference]


def next_pipe(index, previous, matrix):  # index: (i1, i2), previous = (i1, i2)
    pipe = matrix[index[0]][index[1]]
    previous_direction = get_direction(index, previous)

    options = {'|': ('U', 'D'), '-': ('L', 'R'), 'L': ('R', 'U'), 'J': ('U', 'L'), '7': ('L', 'D'), 'F': ('R', 'D')}
    new_direction = options[pipe][0] if previous_direction != options[pipe][0] else options[pipe][1]
    return move(new_direction, index)


def solve(puzzle):
    s = index_2d(puzzle, 'S')
    # try directions:
    if puzzle[s[0] - 1][s[1]] in ['|', 'F', '7']:
        current = move('U', s)
    elif puzzle[s[0]][s[1] + 1] in ['-', 'J', '7']:
        current = move('R', s)
    elif puzzle[s[0] + 1][s[1]] in ['|', 'J', 'L']:
        current = move('D', s)
    else:
        current = move('L', s)

    exterior_points = 1
    previous = s
    pts = []
    while puzzle[current[0]][current[1]] != 'S':
        pts.append(previous)
        exterior_points += 1
        temp = current
        current = next_pipe(current, previous, puzzle)
        previous = temp
    pts.append(previous)
    area = Polygon(pts).area
    # Pick's theorem
    interior_points = int(area + 1 - exterior_points // 2)
    return interior_points


p = Processor(parse, solve, 10)
p.execute()
