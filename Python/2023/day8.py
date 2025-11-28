from math import gcd
from Python import utils


def parse(input):
    instructions = ''
    mapping = {}
    first = True
    for line in input:
        if first:
            instructions = line.strip()
            first = False
        elif line == '\n':
            continue
        else:
            start = line[0:3]
            left = line[7:10]
            right = line[12:15]
            mapping.update({start: (left, right)})
    return mapping, instructions


def execute_all(mapping, instructions):
    # Returns a mapping of each position to its position after executing all instructions
    result = {}
    for position in mapping:
        new_pos = position
        for instruction in instructions:
            new_pos = execute(new_pos, mapping, instruction)
        result.update({position: new_pos})
    return result


def get_start_nodes(mapping):
    return list(filter(lambda x: x[2] == 'A', mapping))


def execute(position, mapping, step):
    index = 0 if step == 'L' else 1
    return mapping[position][index]


def solve(puzzle):
    mapping, instructions = puzzle[0], puzzle[1]
    current = get_start_nodes(mapping)
    # Get position after executing all instructions
    mapping_after_executes = execute_all(mapping, instructions)
    # create list of number of times to execute all instructions to end in 'Z'
    numbers = []
    for checking in current:
        steps = 0
        while checking[2] != 'Z':
            checking = mapping_after_executes[checking]
            steps += 1
        numbers.append(steps)

    # Result is lcm of number of times to execute all instructions
    numbers = list(map(lambda x: x*len(instructions), numbers))
    lcm = 1
    for i in numbers:
        lcm = lcm*i//gcd(lcm, i)
    return lcm


p = utils.Processor(parse, solve, 8)
p.execute()
