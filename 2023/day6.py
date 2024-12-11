from Processor import Processor


def parse(input):
    times = []
    distances = []
    # create {time : distance}
    for line in input:
        string = line.split(': ')[1].strip()
        n = 5 if line.split(': ')[0] == 'Time' else 3
        numbers = [int(i) for i in string.split()][:n]
        if line.split(': ')[0] == 'Time':
            times = numbers
        else:
            numbers.append(1210)
            distances = numbers
    result = {}
    i = 0
    print(times)
    print(distances)
    while i < len(times):
        result.update({times[i]: distances[i]})
        i += 1
    return result


def parse2(input):
    numbers = [int(line.strip().split(':')[1].replace(' ', '')) for line in input]
    return numbers[0], numbers[1]


def calc_distance(time_held, total_time):
    return time_held * (total_time - time_held)


def solve(puzzle):
    time, distance = puzzle
    return sum(1 for time_held in range(1, time) if calc_distance(time_held, time) > distance)


p = Processor(6, parse, solve)
p.run()
