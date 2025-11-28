from functools import cache
from Python import utils


# --------------------------------------- NOT MY SOLUTION ----------------------------------


def parse(input):
    result = []
    for line in input:
        row, nums = line.split()
        nums = tuple(int(num) for num in nums.split(','))
        result.append((row, nums))
    return result


@cache
def springs_finder(row, nums):
    next_part = nums[1:]
    springs = (f"{spr * '.'}{'#' * nums[0]}."
               for spr in range(len(row) - sum(nums) - len(next_part)))
    valid = (len(spr) for spr in springs
             if all(r in (c, '?') for r, c in zip(row, spr)))
    return sum(springs_finder(row[v:], next_part)
               for v in valid) if next_part else sum('#' not in row[v:]
                                                     for v in valid)


def solve(puzzle):
    return sum(springs_finder(r + '.', n) for r, n in puzzle)


def part2(puzzle):
    return solve((('?'.join([r] * 5), n * 5) for r, n in puzzle))


p = utils.Processor(parse, part2, 12)
p.execute()
