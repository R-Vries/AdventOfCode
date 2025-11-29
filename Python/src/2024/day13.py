from Python.Processor import Processor
import os
import re
import numpy as np
DAY = os.path.basename(__file__)[3:-3]

PART = 2
TEST_RESULTS = [480, 875318608908]


def parse(file, to_add):
    # (x_movement_a, x_movement_b, total_x, y_movement_a, y_movement_b, total_y)
    result = []
    x_movement_a, x_movement_b, total_x, y_movement_a, y_movement_b, total_y = 0, 0, 0, 0, 0, 0
    current = 1
    for line in file:
        numbers = list(map(int, re.findall(r'\d+', line)))
        if current == 1:
            x_movement_a = numbers[0]
            y_movement_a = numbers[1]
            current += 1
        elif current == 2:
            x_movement_b = numbers[0]
            y_movement_b = numbers[1]
            current += 1
        elif current == 3:
            total_x = numbers[0] + to_add
            total_y = numbers[1] + to_add
            current += 1
        else:
            result.append((x_movement_a, x_movement_b, y_movement_a, y_movement_b, total_x,  total_y))
            current = 1
    result.append((x_movement_a, x_movement_b, y_movement_a, y_movement_b, total_x, total_y))
    return result


def parse1(file):
    return parse(file, 0)


def parse2(file):
    return parse(file, 10000000000000)


def part1(data):
    cost = 0
    for system in data:
        A = np.matrix([[system[0], system[1]], [system[2], system[3]]])
        B = np.matrix([[system[4]], [system[5]]])
        A_inv = np.linalg.inv(A)
        X = A_inv * B
        A_press, B_press = X[0, 0], X[1, 0]
        if is_int(A_press) and is_int(B_press):
            cost += round(A_press) * 3 + round(B_press)
    return cost


def part2(data):
    return part1(data)


def is_int(n):
    # Use only absolute tolerance, bypassing relative tolerance checks
    return abs(n - round(n)) <= 1e-3


processor = Processor(DAY, parse1, part1, part2, parse2)
processor.run()
