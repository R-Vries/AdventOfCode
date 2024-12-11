from Processor import Processor
import os
DAY = os.path.basename(__file__)[3:-3]

PART = 2
TEST_RESULTS = [143, 123]


def parse(file):
    rules = {}
    pages = []
    first = True
    for line in file:
        if line == "\n":
            first = False
            continue
        if first:
            left, right = map(int, line.strip().split("|"))
            if left in rules:
                rules[left].append(right)
            else:
                rules.update({left: [right]})
        else:
            pages.append(list(map(int, line.strip().split(","))))
    return rules, pages


def part1(data):
    result = 0
    rules, pages = data
    for update in pages:
        if check_update(update, rules):
            result += update[len(update) // 2]
    return result


def check_update(update, rules):
    correct = True
    for i, page in enumerate(update):
        if page not in rules:
            continue
        after = rules[page]
        for j in range(0, i):
            if update[j] in after:
                correct = False
                break
    return correct


def part2(data):
    rules, pages = data
    result = 0
    for update in pages:
        if not check_update(update, rules):
            new_update = make_correct(update, rules)
            result += new_update[len(new_update) // 2]
    return result


def make_correct(update, rules):
    # For each page, rules[page] shows what must come after
    result = []
    for page in update:
        if page not in rules:
            continue
        after = rules[page]
        for i, page2 in enumerate(result):
            if page2 in after:
                result.insert(i, page)
                break
        if page not in result:
            result.append(page)
    return result


processor = Processor(DAY, parse, part1, part2)
processor.run()
