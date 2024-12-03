import utils
from functools import reduce


def parse(input):
    return list(input)[0].split(',')


def HASH(string):
    return reduce(lambda x, c: ((x + ord(c)) * 17) % 256, string, 0)


def check_lens(lenses, new_lens):
    for i, lens in enumerate(lenses):
        if lens[0] == new_lens[0]:
            lenses[i] = new_lens
            return
    lenses.append(new_lens)


def remove_lens(lenses, label):
    for i, (l, _) in enumerate(lenses):
        if l == label:
            lenses.pop(i)
    return lenses


def solve(puzzle):
    boxes = {}  # {hash: [(rn, 1), (cm 2)]}
    for step in puzzle:
        symbol = '=' if step.count('=') > 0 else '-'
        label = step.split(symbol)[0]
        box = HASH(label)
        if box not in boxes:
            boxes.update({box: []})
        if symbol == '=':
            focal_length = step.split(symbol)[1]
            check_lens(boxes[box], (label, focal_length))
        else:
            boxes[box] = remove_lens(boxes[box], label)
    return sum((1 + int(box)) * (i + 1) * int(lens[1]) for box in boxes for i, lens in enumerate(boxes[box]))


p = utils.Processor(parse, solve, 15)
p.run_test(145)
p.execute()
