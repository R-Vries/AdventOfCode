from Python.Processor import Processor
import sys, os

# sys.path.append(os.path.dirname(os.path.dirname(os.path.abspath(__file__))))


def parse(input):
    return list(input)


def solve(puzzle):
    return sum((int(numbers[0] + numbers[-1]) for line in puzzle for numbers in [transform(line)]))


def transform(string):
    numbers = []
    i = 0
    while i < len(string):
        c = string[i]
        if c.isdigit():
            numbers.append(c)
        elif c == 'o' and string[i + 1:i + 3] == 'ne':
            numbers.append('1')
        elif c == 'e' and string[i + 1:i + 5] == 'ight':
            numbers.append('8')
        elif c == 't':
            if string[i + 1:i + 5] == 'hree':
                numbers.append('3')
            elif string[i + 1:i + 3] == 'wo':
                numbers.append('2')
        elif c == 'f':
            if string[i + 1:i + 4] == 'our':
                numbers.append('4')
            elif string[i + 1:i + 4] == 'ive':
                numbers.append('5')
        elif c == 's':
            if string[i + 1:i + 3] == 'ix':
                numbers.append('6')
            elif string[i + 1:i + 5] == 'even':
                numbers.append('7')
        elif string[i:i + 4] == 'nine':
            numbers.append('9')
        i += 1
    return numbers


p = Processor(1, parse, solve, year=2023)
p.run()
