from Python.Processor import Processor
import os
DAY = os.path.basename(__file__)[3:-3]

PART = 2
TEST_RESULTS = [1928, 2858]


def parse(file):
    line = file.readlines()[0].strip()
    return parse_list([int(n) for n in line])


def parse_list(line):
    memory = []
    i = 0
    file_block = True
    for n in line:
        if file_block:
            memory.extend([i] * n)
            file_block = False
            i += 1
        else:
            memory.extend([-1] * n)
            file_block = True
    return memory


def part1(data):
    i = len(data) - 1
    empty_list = [j for j, x in enumerate(data) if x == -1]
    j = 0
    while j < len(empty_list) and i > empty_list[j]:
        if data[i] != -1:
            empty = empty_list[j]
            temp = data[empty]
            data[empty] = data[i]
            data[i] = temp
            j += 1
        i -= 1
    return checksum(data)


def parse2(file):
    # parse the original thing and swap based on that?
    line = file.readlines()[0].strip()
    return [int(x) for x in line]


def checksum(memory):
    return sum(x * i for i, x in enumerate(memory) if x != -1)


def part2(data):
    file_number = len(data) // 2
    file_numbers = [i for i in range(file_number + 1)]
    i = len(data) - 1
    while i >= 0:
        j = 1
        while j < i:
            if data[i] <= data[j]:
                free = data[j]
                size = data[i]
                file_number = file_numbers[i // 2]
                file_numbers.remove(file_number)
                file_numbers.insert(j // 2 + 1, file_number)
                data[j] = size
                data.insert(j, 0)
                data.insert(j + 2, free - size)

                data[i + 1] += size
                del data[i + 2]
                if i + 2 < len(data):
                    data[i + 1] += data[i + 2]
                    del data[i + 2]
                j += 2
                i += 2
                break
            j += 2
        i -= 2
    return checksum3(data, file_numbers)


def checksum3(data, file_numbers):
    result = 0
    index = 0
    file_index = 0
    for i, amount in enumerate(data):
        if i % 2 != 0:
            index += amount
        else:
            for j in range(amount):
                result += file_numbers[file_index] * index
                index += 1
            file_index += 1
    return result


processor = Processor(DAY, parse, part1, part2, parser2=parse2)
processor.run()
