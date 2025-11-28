from Python.Processor import Processor
from Python.utils import parse_matrix, print_map
import os
import numpy as np
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
            warehouse.append(line)
        else:
            instructions.extend(line.strip())
    return parse_matrix(warehouse), [directions[instr] for instr in instructions]


def parse2(file):
    new_warehouse = []
    warehouse, instructions = parse1(file)
    for i, row in enumerate(warehouse):
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
    return new_warehouse, instructions


def part1(data):
    warehouse, instructions = data[0], data[1]
    guard = get_start(warehouse)
    warehouse[guard[0]][guard[1]] = '.'
    for instr in instructions:
        guard, warehouse = move(guard, warehouse, instr)
    warehouse[guard[0]][guard[1]] = '@'
    print_map(warehouse)
    return gps(warehouse)


def move(guard, warehouse, direction):
    new = tuple(np.add(guard, direction))
    item = warehouse[new[0]][new[1]]
    if item == '.':
        guard = new
        return guard, warehouse
    elif item == '#':
        return guard, warehouse
    else:
        new2 = new
        while item == 'O':
            new2 = tuple(np.add(new2, direction))
            item = warehouse[new2[0]][new2[1]]
        if item == '#':
            return guard, warehouse
        else:
            warehouse[new2[0]][new2[1]] = 'O'
            warehouse[new[0]][new[1]] = '.'
            return new, warehouse


def move2(guard, warehouse, direction):
    new = tuple(np.add(guard, direction))
    item = warehouse[new[0]][new[1]]
    if item == '.':
        guard = new
        return guard, warehouse
    elif item == '#':
        return guard, warehouse
    elif item == '[':
        if check_box(new, warehouse, direction):
            guard = new
        return guard, warehouse
    elif item == ']':
        if check_box((new[0], new[1] - 1), warehouse, direction):
            guard = new
        return guard, warehouse
    else:
        print('ERROROROROR')
        return guard, warehouse


def check_box(left, warehouse, direction):
    right = left[0], left[1] + 1

    if direction == (0, 1) or direction == (0, -1):   # left or right
        # next box
        next_box_left = left[0], left[1] + 2 * direction[1]
        next_box_right = right[0], right[1] + 2 * direction[1]
        item_left = get(next_box_left, warehouse)
        item_right = get(next_box_right, warehouse)
        next_left = left[0], left[1] + direction[1]
        next_right = right[0], right[1] + direction[1]
        if item_right == '.':
            move_box(left, right, next_left, next_right, warehouse)
            return True
        elif item_left == '#' or item_right == '#':
            return False
        elif check_box(next_box_left, warehouse, direction):
            move_box(left, right, next_left, next_right, warehouse)
            return True
        else:
            return False
    else:                                             # up or down
        next_box_left = left[0] + direction[0], left[1]
        next_box_right = right[0] + direction[0], right[1]
        item_left = get(next_box_left, warehouse)
        item_right = get(next_box_right, warehouse)
        if item_right == '.' and item_left == '.':
            move_box(left, right, next_box_left, next_box_right, warehouse)
            return True
        elif item_left == '#' or item_right == '#':
            return False
        elif item_left == '[' and item_right == ']':
            if check_box(next_box_left, warehouse, direction):
                move_box(left, right, next_box_left, next_box_right, warehouse)
                return True
            return False
        elif item_left == ']' and item_right == '[':
            if check_box((next_box_left[0], next_box_left[1] - 1), warehouse, direction) and check_box(next_box_right, warehouse, direction):
                move_box(left, right, next_box_left, next_box_right, warehouse)
                return True
            return False
        elif item_left == ']':
            if check_box((next_box_left[0], next_box_left[1] - 1), warehouse, direction):
                move_box(left, right, next_box_left, next_box_right, warehouse)
                return True
            return False
        elif item_right == '[':
            if check_box(next_box_right, warehouse, direction):
                move_box(left, right, next_box_left, next_box_right, warehouse)
                return True
        return False


def get(coordinate, warehouse):
    return warehouse[coordinate[0]][coordinate[1]]


def move_box(old_left, old_right, new_left, new_right, warehouse):
    warehouse[old_left[0]][old_left[1]] = '.'
    warehouse[old_right[0]][old_right[1]] = '.'
    warehouse[new_left[0]][new_left[1]] = '['
    warehouse[new_right[0]][new_right[1]] = ']'


def gps(warehouse):
    return sum(100 * i + j for i, row in enumerate(warehouse)
               for j, item in enumerate(row) if item == 'O' or item == '[')


def get_start(data):
    for i, sublist in enumerate(data):
        if '@' in sublist:
            return i, sublist.index('@')


def part2(data):
    warehouse, instructions = data
    guard = get_start(warehouse)
    for instr in instructions:
        gi, gj = guard
        guard, warehouse = move2(guard, warehouse, instr)
        warehouse[gi][gj] = '.'
        warehouse[guard[0]][guard[1]] = '@'
        print_map(warehouse)
    return gps(warehouse)


"""
########
#......#
#......#
#......#
#......#
#.@O...#
#..OO..#
#...O..#
########

>>^>v
"""


processor = Processor(DAY, parse1, part1, part2, parse2)
processor.run_test(PART, TEST_RESULTS[PART - 1])
# processor.execute(PART)
