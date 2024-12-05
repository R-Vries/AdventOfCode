import typing
import requests
import time

URL = "https://adventofcode.com"


def get_cookie():
    try:
        # Read the content of the file
        with open("../cookie.txt", "r") as file:
            return file.read()
    except ValueError:
        raise FileNotFoundError("No cookie file supplied. Create a file called 'cookie.txt' which contains the cookie")


class Processor:
    def __init__(self, parser, part1, part2, day, year=2024):
        self.parser = parser
        self.part1 = part1
        self.part2 = part2
        self.day = day
        self.year = year
        self.cookie = get_cookie().strip()

    """Runs the test and prints the result"""
    def run_test(self, part: int, expected: int) -> None:
        test = self.get_test()
        print(f"Running test for part {part}...")
        start = time.time()
        result = self.get_solver(part)(self.parser(test))
        elapsed = round((time.time() - start) * 1000, 1)
        if result == expected:
            print(f'TEST SUCCESS ({elapsed} ms)')
        else:
            print('TEST FAILED - RESULT WAS: ' + str(result))
        return

    """Executes the parser and solver on the input file"""
    def execute(self, part: int = 2) -> None:
        print(f"Running solver for part {part}..")
        start = time.time()
        result = self.get_solver(part)(self.parser(self.get_input()))
        elapsed = round((time.time() - start) * 1000, 1)
        print(f'Output: {result} ({elapsed} ms)')

    def format_filename(self):
        return f"tests/test{str(self.day)}.txt"

    def get_test(self) -> typing.TextIO:
        filename = self.format_filename()
        try:
            # Attempt to open the file
            return open(filename)
        except FileNotFoundError:
            self.generate_test()
            return open(filename)

    def generate_test(self):
        print("Enter the test. Ctrl-D to save it.")
        with open(self.format_filename(), "w") as file:
            while True:
                try:
                    line = input()
                except EOFError:
                    break
                file.write(line + '\n')
        return

    def get_input(self):
        filename = f"inputs/input{str(self.day)}.txt"
        try:
            return open(filename)
        except FileNotFoundError:
            self.get_input_request()
            return open(filename)

    def get_input_request(self):
        f = open(f"inputs/input{str(self.day)}.txt", "w")
        response = requests.get(f"{URL}/{self.year}/day/{self.day}/input", cookies={"session": self.cookie})
        for line in response.text:
            f.write(line)
        return

    def get_solver(self, part):
        if part == 1:
            return self.part1
        elif part == 2:
            return self.part2
        else:
            raise Exception("Invalid part number. Use either 1 or 2")


def move(direction, point, amount=1):
    x, y = point
    if direction == 'R':
        return x, y + amount
    if direction == 'L':
        return x, y - amount
    if direction == 'U':
        return x - amount, y
    if direction == 'D':
        return x + amount, y
    raise Exception("Undefined direction")


def parse_matrix(input):
    return list(map(lambda x: list(x.strip()), list(input)))


def parse_numbers(input, separator=" "):
    return [list(map(int, line.split(separator))) for line in input]
