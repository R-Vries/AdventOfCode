import requests
import time
import os

URL = "https://adventofcode.com"

"""
Class that holds all the functions for running the tests, the solver and the parser.
It is able to automatically fetch the input from the AoC website,
allows the user to input the test copied from the AoC website,
and writes results to a markdown file per year.
"""


class Processor:
    def __init__(self, day, parser, part1, part2=None, parser2=None, year=2025):
        self.parser = parser
        self.parser2 = parser if parser2 is None else parser2
        self.part1 = part1 if part2 is not None else None
        self.part2 = part1 if part2 is None else part2
        self.day = day
        self.year = year
        self.cookie = get_cookie().strip()

    def run(self):
        """
        Runs both solvers and tracks the execution time.
        Writes the results to the results.md file for this year
        """
        print('############## DAY', self.day, '##############')
        part1_time = 0
        if self.part1 is not None:
            part1_time += self.execute(1)
        else:
            print('No solver for part 1, skipping...')
        if self.part2 is None:
            raise Exception("Part2() not implemented")
        part2_time = self.execute(2)
        total = part1_time + part2_time
        print('-----------------------------------')
        total = round(total, 1)
        print('Total elapsed time:', total, 'ms')
        print('###################################')
        self._write(part1_time, part2_time, total)

    def run_test(self, part: int, expected: int) -> None:
        """Runs the test and prints the result"""
        test = self._get_test()
        if not test:
            print("Skipping test...")
            return
        print(f"Running test for part {part}...")
        start = time.time()
        result = self._get_solver(part)(self._get_parser(part)(test))
        elapsed = round((time.time() - start) * 1000, 1)
        if result == expected:
            print(f'TEST SUCCESS ({elapsed} ms)')
        else:
            print(f'TEST FAILED - RESULT WAS: {result}, EXPECTED: {expected}')
        return

    def execute(self, part: int = 2) -> float:
        """Executes the parser and solver on the input file"""
        print(f"Running solver for part {part}...")
        start = time.time()
        result = self._get_solver(part)(self._get_parser(part)(self._get_input()))
        elapsed = round((time.time() - start) * 1000, 1)
        print(f'Result: {result} ({elapsed} ms)')
        return elapsed

    #####################################################################################
    #                                 INTERNAL FUNCTIONS                                #
    #####################################################################################

    def _format_filename(self) -> str:
        """Formats the filename according to this processors's year and day"""
        return f"{self.year}/tests/test{self.day}.txt"

    def _get_test(self):
        """Gets the test for this day. Asks for it as input if it doesn't exist yet"""
        filename = self._format_filename()
        try:
            # Attempt to open the file
            return open(filename)
        except FileNotFoundError:
            entered = self._generate_test()
            if entered:
                return open(filename, 'r')
            else:
                os.remove(self._format_filename())
                return False

    def _generate_test(self):
        """Generates a test file after asking the user to past the test in the command line"""
        print("Enter the copied test input from the website.",
              "Press enter, ctrl+z and enter again to save.")
        contains_lines = False
        lines = []

        try:
            file = open(self._format_filename(), 'w')
        except FileNotFoundError:
            os.makedirs(f'{self.year}/tests', exist_ok=True)
            file = open(self._format_filename(), 'w')

        # Read all lines
        while True:
            try:
                line = input()
                contains_lines = True
                lines.append(line)
            except EOFError:
                break

        # Strip al empty lines at the end
        while lines and lines[-1].strip() == "":
            lines.pop()

        # Delete a potential '\x1a' from the last line
        if lines:
            lines[-1] = lines[-1].replace("\x1a", "").rstrip()

        # Write to the file with a newline at the end
        file.write("\n".join(lines))
        file.close()

        return contains_lines

    def _get_input(self):
        """
        Tries to get the puzzle input from the input directory,
        and fetches the input from the website if it doesn't exist yet
        """
        filename = f"{self.year}/inputs/input{self.day}.txt"
        try:
            return open(filename)
        except FileNotFoundError:
            self._get_input_request()
            return open(filename, 'r')

    def _get_input_request(self):
        """Requests the input from the website using the session cookie"""
        inputs_dir = f"{self.year}/inputs/input{self.day}.txt"
        try:
            f = open(inputs_dir, "w")
        except FileNotFoundError:
            os.makedirs(f'{self.year}/inputs')
            f = open(inputs_dir, "w")
        response = requests.get(f"{URL}/{self.year}/day/{self.day}/input", cookies={"session": self.cookie})
        for line in response.text:
            f.write(line)
        return

    def _get_solver(self, part):
        """Determines what solver to use depending on the 'part' argument."""
        if part == 1:
            return self.part1
        elif part == 2:
            return self.part2
        else:
            raise Exception("Invalid part number. Use either 1 or 2")

    def _get_parser(self, part):
        """Determines what parser to use depending on the 'part' argument."""
        if part == 1:
            return self.parser
        elif part == 2:
            return self.parser2
        else:
            raise Exception("Invalid part number. Use either 1 or 2")

    def _write(self, part1, part2, total):
        """Writes the results to a markdown file"""
        results_dir = str(self.year) + '/results.md'
        try:
            with open(results_dir, 'r', encoding='utf-8') as file:
                data = file.readlines()
            row = f'| {self.day} | {part1} | {part2} | {total} |\n'
            data[int(self.day) + 1] = row
        except FileNotFoundError:
            with open(results_dir, 'w', encoding='utf-8') as file:
                top_row = '| Day | Part 1 | Part 2 | Totaal |\n|-----|--------|--------|--------|\n'
                row = f'| {self.day} | {part1} | {part2} | {total} |\n'
                data = [top_row, row]
        except IndexError:
            data.append(row)
        with open(results_dir, 'w', encoding='utf-8') as file:
            file.writelines(data)


def get_cookie():
    """Get the session cookie from cookie.txt and raise an error if it doesn't exist"""
    cookie_path = os.path.abspath(os.path.join(os.path.dirname(__file__), "..", "cookie.txt"))
    try:
        with open(cookie_path, "r") as file:
            cookie = file.read()
            if cookie == '':
                raise ValueError(
                    "cookie.txt is empty - paste the Advent of Code Session cookie here. "
                    "More is explained in README.md")
            else:
                return cookie
    except FileNotFoundError:

        raise FileNotFoundError(f"No file named 'cookie.txt' exists in {cookie_path}. "
                                "Create the file in that directory and paste the cookie. "
                                "An explanation about getting the cookie can be found in README.md")
