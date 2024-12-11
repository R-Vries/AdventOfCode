import requests
import time
import os

URL = "https://adventofcode.com"


def get_cookie():
    try:
        with open("../cookie.txt", "r") as file:
            return file.read()
    except FileNotFoundError:
        raise FileNotFoundError("No cookie file supplied. Create a file called 'cookie.txt' which contains the cookie")


class Processor:
    def __init__(self, day, parser, part1, part2=None, parser2=None, year=2024):
        self.parser = parser
        self.parser2 = parser if parser2 is None else parser2
        self.part1 = part1 if part2 is not None else None
        self.part2 = part1 if part2 is None else part2
        self.day = day
        self.year = year
        self.cookie = get_cookie().strip()

    """Runs the test and prints the result"""
    def run_test(self, part: int, expected: int) -> None:
        test = self.get_test()
        if not test:
            print("Skipping test...")
            return
        print(f"Running test for part {part}...")
        start = time.time()
        result = self.get_solver(part)(self.get_parser(part)(test))
        elapsed = round((time.time() - start) * 1000, 1)
        if result == expected:
            print(f'TEST SUCCESS ({elapsed} ms)')
        else:
            print(f'TEST FAILED - RESULT WAS: {result}, EXPECTED: {expected}')
        return

    """Executes the parser and solver on the input file"""
    def execute(self, part: int = 2) -> float:
        print(f"Running solver for part {part}...")
        start = time.time()
        result = self.get_solver(part)(self.get_parser(part)(self.get_input()))
        elapsed = round((time.time() - start) * 1000, 1)
        print(f'Output: {result} ({elapsed} ms)')
        return elapsed

    def format_filename(self) -> str:
        return f"tests/test{self.day}.txt"

    def get_test(self):
        filename = self.format_filename()
        try:
            # Attempt to open the file
            return open(filename)
        except FileNotFoundError:
            entered = self.generate_test()
            if entered:
                return open(filename, 'r')
            else:
                os.remove(self.format_filename())
                return False

    def generate_test(self):
        print("Enter the test. Press Ctrl-D to save it.")
        contains_lines = False
        try:
            file = open(self.format_filename(), 'w')
        except FileNotFoundError:
            os.makedirs('tests')
            file = open(self.format_filename(), 'w')
        while True:
            try:
                line = input()
                contains_lines = True
            except EOFError:
                break
            file.write(line + '\n')
        file.close()
        return contains_lines

    def get_input(self):
        filename = f"inputs/input{self.day}.txt"
        try:
            return open(filename)
        except FileNotFoundError:
            self.get_input_request()
            return open(filename, 'r')

    def get_input_request(self):
        try:
            f = open(f"inputs/input{str(self.day)}.txt", "w")
        except FileNotFoundError:
            os.makedirs('inputs')
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

    def get_parser(self, part):
        if part == 1:
            return self.parser
        elif part == 2:
            return self.parser2
        else:
            raise Exception("Invalid part number. Use either 1 or 2")

    def run(self):
        print('############## DAY', self.day, '##############')
        part1_time = 0
        if self.part1 is not None:
            part1_time += self.execute(1)
        else:
            print('No solver for part 1, skipping...')
        part2_time = self.execute(2)
        total = part1_time + part2_time
        print('-----------------------------------')
        total = round(total, 1)
        print('Total elapsed time:', total, 'ms')
        print('###################################')
        self.write(part1_time, part2_time, total)

    def write(self, part1, part2, total):
        with open('results.md', 'r') as file:
            data = file.readlines()
        row = f'| {self.day} | {part1} | {part2} | {total} |\n'
        try:
            data[int(self.day) + 1] = row
        except IndexError:
            data.append(row)
        with open('results.md', 'w') as file:
            file.writelines(data)
