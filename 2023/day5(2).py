def create_map(line):
    numbers = list(map(int, line.split(' ')))
    dest_start = numbers[0]
    src_start = numbers[1]
    increase = src_start - dest_start
    return (dest_start, dest_start + numbers[2]), increase


def create_map_list(lines, i, stop):
    result = []
    while lines[i] != stop:
        result.append(create_map(lines[i]))
        i -= 1
    return result, i


def create_seeds(line):
    result = []
    numbers = list(map(int, line.split(': ')[1].split(' ')))
    i = 0
    while i < len(numbers):
        result.append(range(numbers[i], numbers[i] + numbers[i + 1]))
        i += 2
    return result


def parse(input):
    lines = []
    for line in input:
        if line != '\n':
            lines.append(line.strip())
    i = len(lines) - 1
    result = []     # [((dest_start, dest_end), increase)
    stops = ['seed-to-soil map:',
             'soil-to-fertilizer map:',
             'fertilizer-to-water map:',
             'water-to-light map:',
             'light-to-temperature map:',
             'temperature-to-humidity map:',
             'humidity-to-location map:']
    j = len(stops) - 1
    while i > 0:
        map_list, i = create_map_list(lines, i, stops[j])
        result.append(map_list)
        j -= 1
        i -= 1
    # seeds = list(map(int, lines[0].split(': ')[1].split(' ')))
    seeds = create_seeds(lines[0])
    return result, seeds


def get_src(location, mappings):
    for mapping in mappings:
        if mapping[0][0] <= location < mapping[0][1]:
            return location + mapping[1]
    return location


def get_seed(mappings, location):
    current = location
    for m in mappings:
        current = get_src(current, m)
    return current


f = open('inputs/input5.txt', 'r')
mappings, seeds = parse(f)
i = 0
done = False
while not done:
    seed = get_seed(mappings, i)
    for seed_range in seeds:
        if seed in seed_range:
            print("Lowest seed: " + str(i))
            done = True
            break
    i += 1
