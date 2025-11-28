from Python.Processor import Processor
import os
import re
DAY = os.path.basename(__file__)[3:-3]

PART = 2
TEST_RESULTS = [12]
MAP_SIZE = (101, 103)


def parse(file):
    result = []
    for line in file:
        x, y, dx, dy = list(map(int, re.findall(r'-?\d+', line)))
        result.append(((x, y), (dx, dy)))
    return result


def part1(data):
    positions = [p[0] for p in data]
    velocities = [p[1] for p in data]
    for i in range(100):
        for j, pos in enumerate(positions):
            positions[j] = move(pos, velocities[j])
    # calculate quadrants
    q1, q2, q3, q4 = 0, 0, 0, 0
    for pos in positions:
        x_half, y_half = MAP_SIZE[0] // 2, MAP_SIZE[1] // 2
        if 0 <= pos[0] < x_half and 0 <= pos[1] < y_half:
            q1 += 1
        elif x_half + 1 <= pos[0] < MAP_SIZE[0] and 0 <= pos[1] < y_half:
            q2 += 1
        elif 0 <= pos[0] < x_half and y_half + 1 <= pos[1] < MAP_SIZE[1]:
            q3 += 1
        elif x_half + 1 <= pos[0] < MAP_SIZE[0] and y_half + 1 <= pos[1] < MAP_SIZE[1]:
            q4 += 1
    return q1 * q2 * q3 * q4


def move(pos, v):
    return (pos[0] + v[0]) % MAP_SIZE[0], (pos[1] + v[1]) % MAP_SIZE[1]


def part2(data):
    positions = [p[0] for p in data]
    velocities = [p[1] for p in data]
    filename = 'output14.txt'
    with open(filename, 'w') as f:
        for i in range(10000):
            for j, pos in enumerate(positions):
                positions[j] = move(pos, velocities[j])
            # Horizontal 46, 149, 252 (103)
            # Vertical 81, 182, 283
            if (i - 46) % 103 == 0 and (i - 81) % 101 == 0:
                print(f"Output written to {filename}")
                f.write(map_positions(positions))
                f.write(f'\n{i}{" " * 85} \n')
                return i + 1


def map_positions(positions):
    result = [['.' for x in range(MAP_SIZE[0])] for y in range(MAP_SIZE[1])]
    for x, y in positions:
        result[y][x] = '#'
    return "\n".join(''.join(p for p in sub) for sub in result)


processor = Processor(DAY, parse, part1, part2)
processor.run()
