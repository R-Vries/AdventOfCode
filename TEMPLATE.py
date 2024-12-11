from Processor import Processor
import os
DAY = os.path.basename(__file__)[3:-3]

PART = 1
TEST_RESULTS = []


def parse(file):
    pass


def part1(data):
    pass


def part2(data):
    pass


processor = Processor(DAY, parse, part1, part2)
processor.run_test(PART, TEST_RESULTS[PART - 1])
processor.execute(PART)
