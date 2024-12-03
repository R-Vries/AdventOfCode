from utils import Processor, parse_numbers
import os
DAY = os.path.basename(__file__)[3:-3]


def parse(file):
    return parse_numbers(file)


def part1(data):
    return sum(1 for report in data if check(report))


def part2(data):
    result = 0
    for report in data:
        if check(report):
            result += 1
        else:
            for i in range(0, len(report)):
                if check(report[:i] + report[i+1:]):
                    result += 1
                    break
    return result


def check(report):
    increasing = report[0] - report[1] < 0
    for i in range(1, len(report)):
        if unsafe(report[i - 1], report[i], increasing):
            return False
    return True


def unsafe(left, right, increasing):
    return (increasing and right < left) or (not 1 <= abs(right - left) <= 3) or (not increasing and right > left)


processor = Processor(parse, part1, part2, DAY)
processor.run_test(2, 4)
processor.execute(2)
