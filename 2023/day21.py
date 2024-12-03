from utils import Processor, parse_matrix, move


def solve(puzzle):
    # Find S
    s = next((i, j) for i, row in enumerate(puzzle) for j, value in enumerate(row) if value == 'S')
    zeroes = {s}
    directions = ['U', 'D', 'L', 'R']
    for i in range(64):
        new_zeroes = set()
        for zero in zeroes:
            for direction in directions:
                x, y = move(direction, zero)
                if x < len(puzzle) and y < len(puzzle[0]) and puzzle[x][y] != '#':
                    new_zeroes.add((x, y))
        zeroes = new_zeroes
    return len(zeroes)


p = Processor(parse_matrix, solve, 21)

p.execute()
