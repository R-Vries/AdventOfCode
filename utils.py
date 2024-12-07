def move(direction, point, amount=1):
    x, y = point
    if direction == 'R':
        return x, y + amount
    if direction == 'L':
        return x, y - amount
    if direction == 'U':
        return x - amount, y
    if direction == 'D':
        return x + amount, y
    raise Exception("Undefined direction")


def parse_matrix(input):
    return list(map(lambda x: list(x.strip()), list(input)))


def parse_numbers(input, separator=" "):
    return [list(map(int, line.split(separator))) for line in input]
