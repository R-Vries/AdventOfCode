from Python.utils import Processor


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

    def sum(self):
        return len(self.x) * len(self.m) * len(self.a) * len(self.s)


puzzle = {}         # will be the mapping of rules


def parse(input):
    flows = {}
    lines = list(input)
    enter = lines.index('\n')
    workflows = lines[0:enter]
    for flow in workflows:  # flow: 'px(a<2006:qkq,m>2090:A,rfg}
        name, rest = flow.split('{')
        rules = [tuple(rule.split(':')) if ':' in rule else (rule,) for rule in rest[0:-2].split(',')]
        flows.update({name: rules})
    global puzzle
    puzzle = flows
    return flows


def split_at(part, value, category):
    part1 = part.copy()
    part2 = part.copy()
    part1.set(category, range(part.get_val(category).start, value - 1))
    part2.set(category, range(value - 1, part.get_val(category).stop))
    return part1, part2


def evaluate(part, flow, rule_number=0):
    rule = flow[rule_number]
    if len(rule) == 1:
        if rule[0] == 'A':
            return part.sum()
        elif rule[0] == 'R':
            return 0
        else:
            return evaluate(part, puzzle[rule[0]])
    else:
        no, yes = split_at(part, int(rule[0][2:]), rule[0][0])
        no_res = evaluate(no, flow, rule_number + 1)
        if rule[1] == 'A':
            return yes.sum() + no_res
        elif rule[1] != 'R':
            return evaluate(yes, puzzle[rule[1]]) + no_res
        else:
            return no_res


def solve(puzzle):
    return evaluate(Part(range(0, 4000), range(0, 4000), range(0, 4000), range(0, 4000)), puzzle['in'])


p = Processor(parse, solve, 19)
p.run_test(167409079868000)
