from Python.Processor import Processor
from Python.Map import Map
from Python.utils import parse_matrix
import os
DAY = os.path.basename(__file__)[3:-3]

PART = 2
TEST_RESULTS = [2028, 9021]


def parse1(file):
    directions = {'^': (-1, 0), '>': (0, 1), '<': (0, -1), 'v': (1, 0)}
    warehouse = []
    instructions = []
    moves = False
    for line in file:
        if line == '\n':
            moves = True
        elif not moves:
            warehouse.append(line.strip())
        else:
            instructions.extend(line.strip())
    return Map(parse_matrix(warehouse)), [directions[instr] for instr in instructions]


def parse2(file):
    new_warehouse = []
    warehouse, instructions = parse1(file)
    for i, row in enumerate(warehouse.get_lists()):
        new_warehouse_row = []
        for j, item in enumerate(row):
            if item == '@':
                new_warehouse_row.append('@')
                new_warehouse_row.append('.')
            elif item == 'O':
                new_warehouse_row.append('[')
                new_warehouse_row.append(']')
            else:
                new_warehouse_row.append(item)
                new_warehouse_row.append(item)
        new_warehouse.append(new_warehouse_row)
    return Map(new_warehouse), instructions


def part1(data):
    warehouse, instructions = data[0], data[1]
    guard = warehouse.index('@')
    warehouse[guard] = '.'
    for instr in instructions:
        guard, warehouse = move(guard, warehouse, instr)
    warehouse[guard] = '@'
    print(warehouse)
    return gps(warehouse)


def move(guard, warehouse, direction):
    new = guard[0] + direction[0], guard[1] + direction[1]
    item = warehouse[new]
    if item == '.':
        guard = new
        return guard, warehouse
    elif item == '#':
        return guard, warehouse
    else:
        new2 = new
        while item == 'O':
            new2 = new2[0] + direction[0], new2[1] + direction[1]
            item = warehouse[new2]
        if item == '#':
            return guard, warehouse
        else:
            warehouse[new2] = 'O'
            warehouse[new] = '.'
            return new, warehouse


def gps(warehouse):
    return sum(100 * i + j for i, row in enumerate(warehouse.get_lists())
               for j, item in enumerate(row) if item == 'O' or item == '[')


def part2(data):
    print(data[0])





processor = Processor(DAY, parse1, part1, part2, parse2)
processor.run_test(PART, TEST_RESULTS[PART - 1])
processor.execute(PART)
