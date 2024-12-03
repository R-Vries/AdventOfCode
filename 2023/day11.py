import utils


def parse(input):
    input = list(input)
    result = []
    current = 1
    # Find rows with now galaxy and assign numbers to galaxies
    for i, line in enumerate(input):
        has_galaxy = False
        line = line.strip()
        row = []
        for character in line:
            if character == '#':
                row.append(current)
                current += 1
                has_galaxy = True
            elif character == '.':
                row.append('.')
        result.append(row)
        if not has_galaxy:
            result[i] = ['-'] * len(line)
            # result.append(['-'] * len(line))
    # Find columns with no galaxy
    j = 0
    while j < len(result[0]):
        i = 0
        has_galaxy = False
        while i < len(result):
            if isinstance(result[i][j], int):
                has_galaxy = True
            i += 1
        if not has_galaxy:
            for row in result:
                row[j] = '|'
                # row.insert(j, '|')
            j += 1
        j += 1
    return result


def get_distance(i1, i2, matrix):
    result = 0
    # Vertical distance
    i = i1[0]
    while i < i2[0]:
        if matrix[i][i1[1]] == '-':
            result += 1000000
        else:
            result += 1
        i += 1
    # Horizontal distance
    direction = 1 if i1[1] < i2[1] else -1
    j = i1[1]
    while j != i2[1]:
        if matrix[i][j] == '|':
            result += 1000000
        else:
            result += 1
        j += direction
    return result


def solve(puzzle):
    numbers = {}        # {1: (0, 4), 2: (1, 12), ...}
    for i, row in enumerate(puzzle):
        for j, character in enumerate(row):
            if isinstance(character, int):
                numbers.update({character: (i, j)})
    paths = {}      # {(1, 2): 12, (1, 3): 35, ...}
    for number in numbers:
        for number2 in [n for n in numbers if n != number and not (n, number) in paths.keys()]:
            distance = get_distance(numbers[number], numbers[number2], puzzle)
            paths.update({(number, number2): distance})
    return sum(paths.values())


p = utils.Processor(parse, solve, 11)
p.execute()
