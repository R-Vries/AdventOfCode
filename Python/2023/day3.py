from Python.Processor import Processor


def parse(input):
    matrix = []
    first = True
    for line in input:
        if first:
            cols = len(line)
            matrix.append(['.'] * (cols + 1))
            first = False
        row = []
        row.append('.')
        for slot in line:
            if slot != '\n':
                row.append(slot)
        row.append('.')
        matrix.append(row)
    matrix.append(['.'] * (cols + 1))
    return matrix


def connected(slot):
    return slot != '.' and not slot.isdigit()


def part1(matrix):
    i, j = 1, 1
    result = 0
    while i < len(matrix):
        row = matrix[i]
        current_num = ''
        current_connected = False
        j = 1
        while j < len(row):
            slot = matrix[i][j]
            if slot.isdigit():
                current_num += slot
                if connected(matrix[i - 1][j - 1]) or connected(matrix[i - 1][j]) or connected(matrix[i - 1][j + 1]) \
                        or connected(matrix[i][j - 1]) or connected(matrix[i][j + 1]) \
                        or connected(matrix[i + 1][j - 1]) or connected(matrix[i + 1][j]) or connected(
                    matrix[i + 1][j + 1]):
                    current_connected = True
            else:
                if current_num != '' and current_connected:
                    result += int(current_num)
                current_num = ''
                current_connected = False
            j += 1
        i += 1
    return result


def part2(matrix):
    copymap = [[0] * len(matrix[0]) for _ in range(len(matrix))]
    # part 2
    i = 1
    while i < len(matrix):
        row = matrix[i]
        current_num = ''
        j = 1
        while j < len(row):
            slot = matrix[i][j]
            if slot.isdigit():
                current_num += slot
            else:
                if current_num != '':
                    k = 1
                    for digit in current_num:
                        copymap[i][j - k] = int(current_num)
                        k += 1
                current_num = ''
            j += 1
        i += 1

    result = 0
    i = 1
    while i < len(matrix):
        row = matrix[i]
        j = 1
        while j < len(row):
            slot = matrix[i][j]
            if slot == '*':
                part_nums = []
                for k in range(-1, 2):
                    for l in range(-1, 2):
                        value = copymap[i + k][j + l]
                        if value != 0 and part_nums.count(value) == 0:
                            part_nums.append(copymap[i + k][j + l])
                if len(part_nums) == 2:
                    result += part_nums[0] * part_nums[1]
            j += 1
        i += 1
    return result


processor = Processor(3, parse, part1, part2, year=2023)
processor.run()
