from Processor import Processor
import os
import math
from utils import parse_numbers
from collections import defaultdict
DAY = os.path.basename(__file__)[3:-3]


def parse(file):
    numbers = parse_numbers(file)[0]
    return {number: 1 for number in numbers}


def part1(data):
    return simulate(data, 25)


def simulate(data, times):
    saved = {}  # {number: [numbers]}
    for i in range(0, times):
        data = blink(data, saved)
    return sum(amount for amount in data.values())


def blink(numbers, saved):
    result = defaultdict(int)
    for number in numbers:
        amount = numbers[number]
        if number in saved:
            calculated = saved[number]
            for n in calculated:
                result[n] += amount
        elif number == 0:
            result[1] += amount
            saved.update({number: [1]})
        elif math.floor(1 + math.log(number, 10)) % 2 == 0:
            s = str(number)
            half = len(s) // 2
            left = int(s[:half])
            right = int(s[half:])
            result[left] += amount
            result[right] += amount
            saved.update({number: [left, right]})
        else:
            r = number * 2024
            result[r] += amount
            saved.update({number: [r]})
    return result


def part2(data):
    return simulate(data, 75)


processor = Processor(DAY, parse, part1, part2)
processor.run()
