import utils
from itertools import combinations
from functools import cache


def parse(input):
    return [(list(line.split(' ')[0]), list(map(int, line.split(' ')[1].strip().split(',')))) for line in input]


# def parse2(input):
#     result = []
#     for line in input:
#         split = line.split(' ')
#         current = []
#         for i in range(0, 5):
#             current.append(split[0])
#             current.append('?')


def is_valid(row, numbers):
    actual_numbers = []
    count = 0
    for spring in row:
        if spring == '#':
            count += 1
        elif count > 0:
            actual_numbers.append(count)
            count = 0
        else:
            count = 0
    if count > 0:
        actual_numbers.append(count)
    return actual_numbers == numbers


def get_perms(row):
    total_intact = sum(row[1]) - row[0].count('#')
    indices = [i for i, x in enumerate(row[0]) if x == '?']
    pairs = list(combinations(indices, total_intact))
    result = []
    for pair in pairs:
        permutation = row[0].copy()
        for i in range(0, len(pair)):
            permutation[pair[i]] = '#'
        result.append([x if x != '?' else '.' for x in permutation])
    return result


def solve(puzzle):
    result = 0
    for row in puzzle:
        permutations = get_perms(row)
        for perm in permutations:
            if is_valid(perm, row[1]):
                result += 1
    return result


def double(row):
    # row = (['?', '.', '?', '#', ..., '.'], [1, 1, 3])
    new_spring = row[0]
    new_spring.append('?')
    new_spring += row[0]
    new_numbers = row[1] + row[1]
    return new_spring, new_numbers


def solve2(puzzle):
    result = 0
    for row in puzzle:
        solutions = 0
        permutations = get_perms(row)
        for perm in permutations:
            if is_valid(perm, row[1]):
                solutions += 1
        two = double(row)
        solutions2 = 0
        permutations = get_perms(two)
        for perm in permutations:
            if is_valid(perm, two[1]):
                solutions2 += 1
        factor = solutions2 / solutions
        intermediate = solutions * factor * factor * factor * factor
        print(intermediate)
        result += intermediate
    return result


@cache
def solve_row(row, numbers):
    result = 0
    permutations = get_perms(row)
    for perm in permutations:
        if is_valid(perm, row[1]):
            result += 1
    return result


p = utils.Processor(parse, solve2, 12)
test = utils.get_test(12)
# p.run_test(525152)
# print(solve_row(('.??..??...?##.?.??..??...?##.', [1, 1, 3, 1, 1, 3])))

numbers = [1, 1, 3]
print(numbers[1:])