from Python.utils import Processor
import re


class Part:
    def __init__(self, x, m, a, s):
        self.x = x
        self.m = m
        self.a = a
        self.s = s

    def __str__(self):
        return f'Part(x={self.x}, m={self.m}, a={self.a}, s={self.s})'

    def get_val(self, string):
        part_mapping = {'x': self.x, 'm': self.m, 'a': self.a, 's': self.s}
        return part_mapping[string]

    def sum(self):
        return self.x + self.m + self.a + self.s

    def get_all(self):
        return self.x, self.m, self.a, self.s

    def set(self, category, value):
        if category == 'x':
            self.x = value
        elif category == 'm':
            self.m = value
        elif category == 'a':
            self.a = value
        else:
            self.s = value

    def copy(self):
        return Part(self.x, self.m, self.a, self.s)

    def sum_range(self):
        return len(self.x) * len(self.m) * len(self.a) * len(self.s)


def split_at(part, value, category):
    part1 = part.copy()
    part2 = part.copy()
    part1.set(category, range(part.get_val(category).start, value - 1))
    part2.set(category, range(value - 1, part.get_val(category).stop))
    return part1, part2


def parse(input):
    flows = {}
    lines = list(input)
    parts_objects = []
    enter = lines.index('\n')
    workflows, parts = lines[0:enter], lines[enter + 1:]
    for p in parts:
        x, m, a, s = re.findall(r'\d+', p)
        parts_objects.append(Part(int(x), int(m), int(a), int(s)))
    for flow in workflows:  # flow: 'px(a<2006:qkq,m>2090:A,rfg}
        name = flow.split('{')[0]
        rules = []
        rule_strings = flow.split('{')[1][0:-2].split(',')
        for r in rule_strings:
            split = r.split(':')
            if len(split) == 1:
                rules.append((r,))
            else:
                rules.append((split[0], split[1]))
        flows.update({name: rules})
    return flows, parts_objects


def get_op(symbol):
    if symbol == '>':
        return lambda x, y: x > y
    else:
        return lambda x, y: x < y


def evaluate(rule, part):  # Rule: ('s<1351', 'px') or ('qqz'), part = part obj (e.g. part.x = 456)
    if len(rule) == 1:  # final rule
        return rule[0]
    cat = rule[0][0]
    symbol = rule[0][1]
    result = get_op(symbol)(part.get_val(cat), int(rule[0][2:]))
    return rule[1] if result else False


def solve(puzzle):
    result = 0
    flows, parts = puzzle
    for part in parts:
        current = 'in'
        done = False
        while not done:
            f = flows[current]
            for rule in f:
                res = evaluate(rule, part)
                if res == 'A':
                    result += part.sum()
                    done = True
                    break
                elif res == 'R':
                    done = True
                    break
                elif res:       # new flow
                    current = res
                    break
    return result


def get_new_states(puzzle):
    result = {}
    for name in puzzle:
        result.setdefault(name, [])
    return result


def solve2(puzzle):
    accepted = []
    result = 0
    puzzle = puzzle[0]
    states = {'in': [Part(range(0, 4000), range(0, 4000), range(0, 4000), range(0, 4000))]}
    while not len(states) == 0:
        new_states = get_new_states(puzzle)
        for state in states:
            for part in states[state]:
                states[state].remove(part)
                for rule in puzzle[state]:
                    if len(rule) == 1:
                        if rule[0] == 'A':
                            accepted.append(part)
                            result += part.sum_range()
                        elif rule[0] == 'R':
                            break
                        else:
                            new_states[rule[0]].append(part)
                    else:
                        cond, next = rule
                        cat = cond[0]
                        symbol = cond[1]
                        number = int(cond[2:])
                        p1, p2 = evaluate2(part, number, cat, symbol)   # p1: condition does not hold. p2 it does
                        if next == 'A':
                            accepted.append(p2)
                            result += p2.sum_range()
                        elif next != 'R':
                            new_states[next].append(p2)
                        part = p1
        states = {s: l for (s, l) in new_states.items() if len(l) != 0}
    # print(sum(map(lambda x: x.sum_range(), accepted)))
    # for a in accepted:
    #     print(a)
    return result


def evaluate2(part, number, category, symbol):
    p1, p2 = split_at(part, number, category)
    if symbol == '>':
        return p1, p2
    else:
        return p2, p1


p = Processor(parse, solve2, 19)
p.run_test(167409079868000)

poep = Part(range(0, 4000), range(0, 4000), range(0, 4000), range(0, 4000))
i1, i2 = split_at(poep, 1501, 's')
print(poep.sum_range() == i1.sum_range() + i2.sum_range())
