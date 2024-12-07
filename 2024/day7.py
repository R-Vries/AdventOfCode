from Processor import Processor
import os
DAY = os.path.basename(__file__)[3:-3]

PART = 2
TEST_RESULTS = [3749, 11387]


def parse(file):
    answers = []
    numbers = []
    for line in file:
        parts = line.split(": ")
        answers.append(int(parts[0]))
        numbers.append(list(map(int, parts[1].split(' '))))
    return zip(answers, numbers)


def part1(data):
    return sum(answer for answer, numbers in data if correct(answer, numbers, False))


def correct(answer, numbers, concat):
    return answer in calculate(numbers[-1], numbers, [], concat)


def calculate(current, numbers, results, concat):
    if len(numbers) == 1:
        return [current]
    else:
        values = calculate(numbers[-2], numbers[:-1], results, concat)
        added = list(map(lambda x: x + current, values))
        multiplied = list(map(lambda x: x * current, values))
        if concat:
            concatenated = list(map(lambda x: int(str(x) + str(current)), values))
            return added + multiplied + concatenated
        else:
            return added + multiplied


def part2(data):
    return sum(answer for answer, numbers in data if correct(answer, numbers, True))


processor = Processor(parse, part1, part2, DAY)
processor.run_test(PART, TEST_RESULTS[PART - 1])
processor.execute(PART)
