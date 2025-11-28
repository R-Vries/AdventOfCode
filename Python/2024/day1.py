from Python.Processor import Processor
from collections import Counter
import os,sys
DAY = os.path.basename(__file__)[3:-3]
sys.path.append(os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

# Create 2 lists
def parse(file):
    l1, l2 = zip(*[(int(line.split("   ")[0]), int(line.split("   ")[1])) for line in file])
    return list(l1), list(l2)


def part1(data):
    l1, l2 = data
    result = 0
    while l1:
        n1 = min(l1)
        n2 = min(l2)
        l1.remove(n1)
        l2.remove(n2)
        difference = abs(n1 - n2)
        result += difference
    return result


def part2(data):
    l1, l2 = data
    result = 0
    occurrences = Counter(l2)
    for n in l1:
        result += n * occurrences[n]
    return result


p = Processor(DAY, parse, part1, part2, year=2024)
p.run()
