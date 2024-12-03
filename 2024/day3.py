import os
import re
from functools import reduce

from utils import Processor

DAY = os.path.basename(__file__)[3:-3]


def parse(input):
    return [row for row in input]


def part1(input):
    input = [re.findall(r"mul\(\d+,\d+\)", line) for line in input]
    result = sum(sum(reduce(lambda x, y: x*y, map(int, re.findall(r"\d+", instr))) for instr in row) for row in input)
    return result


def part2(input):
    input = [re.findall(r"mul\([0-9]+,[0-9]+\)|do\(\)|don't\(\)", line) for line in input]
    do = True
    result = 0
    for row in input:
        for instr in row:
            if instr.startswith("mul") and do:
                numbers = map(int, re.findall(r"\d+", instr))
                result += reduce(lambda x, y: x*y, numbers)
            elif instr == "don't()":
                do = False
            elif instr == "do()":
                do = True

    return result


processor = Processor(parse, part1, part2, DAY)
processor.run_test(1, 161)
processor.execute(2)
