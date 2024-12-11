from Processor import Processor


def parse(input):
    result = []
    for line in input:
        split = line.split(': ')[1].split(' | ')
        result.append((1, (filter(split[0].split(' ')), filter(split[1].split(' ')))))
    return result


def filter(list):
    result = []
    for item in list:
        if item != '':
            result.append(item.strip())
    return result


def solve(cards):
    i = 0
    total = 0
    while i < len(cards):  # cards: [(1, ([47, 31, 12], [14, 31, 6])), (1, ([], []))]
        card = cards[i]  # card: (1, ([], []))
        amount = int(card[0])
        total += amount
        numbers = card[1]  # numbers: ([], [])
        winning = 0
        for my_number in numbers[1]:
            if numbers[0].count(my_number) > 0:
                winning += 1
        j = i + 1
        for k in range(winning):
            cards[j] = (cards[j][0] + amount, cards[j][1])  # increase number of copies
            j += 1
        i += 1
    return total


p = Processor(4, parse, solve, year=2023)
p.run()
