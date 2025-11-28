import os, sys

# Ensures that the Processor can be used
sys.path.append(os.path.dirname(os.path.dirname(os.path.abspath(__file__))))
from Processor import Processor

DAY = os.path.basename(__file__)[3:-3]  # Looks at the filename to keep track of which day it is

# Use these variables for running the tests
PART = 1           # PART = 1 is part 1, PART = 2 is part 2
TEST_RESULTS = []  # Store the correct results for the tests here; you can find them on the website


# Write code in this function to convert the contents of the input file
# into a convenient data structure and return it
def parse(file):
    pass


# This function receives the data structure from the parse function.
# Use the data structure to obtain the answer to part 1 of the puzzle and return this answer
def part1(data):
    pass


# This function receives the data structure from the parse function.
# Use the data structure to obtain the answer to part 2 of the puzzle and return this answer
def part2(data):
    pass


processor = Processor(DAY, parse, part1, part2)   # Automatically creates a processor with the correct variables
processor.run_test(PART, TEST_RESULTS[PART - 1])  # Run the test for part PART
processor.execute(PART)                           # Execute PART on the full input
processor.run()                                   # Use run() only when both part() functions have been filled in
