def create_mapping(line):
    # ((source_start, source_end), increase)
    numbers = list(map(int, line.split(' ')))
    source_start = numbers[1]
    dest_start = numbers[0]
    amount = numbers[2]
    if source_start > dest_start:
        increase = -1 * (source_start - dest_start)
    else:
        increase = dest_start - source_start
    return (source_start, source_start + amount), increase


def create_seeds(line):
    result = []
    numbers = list(map(int, line.split(': ')[1].split(' ')))
    i = 0
    while i < len(numbers):
        result.append(range(numbers[i], numbers[i] + numbers[i + 1]))
        i += 2
    return result


def create_mapping_list(lines, i, stop):
    result = []
    while lines[i] != stop:
        result.append(create_mapping(lines[i]))
        i += 1
    return result, i


def parse(input):
    parts = []
    result = []  # Create this: [maps] -> map = [mappings] = [((98, 100), -48), ((50, 98), 2)]
    for line in input:
        if line != '\n':
            parts.append(line.strip())
    parts.append('stop')
    # parse seeds
    # seeds = list(map(int, parts[0].split(': ')[1].split(' ')))
    seeds = create_seeds(parts[0])
    stops = ['soil-to-fertilizer map:',
             'fertilizer-to-water map:',
             'water-to-light map:',
             'light-to-temperature map:',
             'temperature-to-humidity map:',
             'humidity-to-location map:',
             'stop']
    i = 2
    j = 0
    while i < len(parts):
        map_list, i = create_mapping_list(parts, i, stops[j])
        result.append(map_list)
        i += 1
        j += 1
    return result, seeds


def get_dest(mappings, source):
    for mapping in mappings:
        if mapping[0][0] <= source < mapping[0][1]:
            return source + mapping[1]
    return source


def get_location(mappings, seed):
    current = seed
    for m in mappings:
        current = get_dest(m, current)
    return current



f = open('inputs/input5.txt', 'r')
mappings, seeds = parse(f)
lowest = get_location(mappings, seeds[0][0])
# for seed in seeds:
#     loc = get_location(mappings, seed)
#     lowest = loc if loc < lowest else lowest
for r in seeds:
    for seed in r:
        loc = get_location(mappings, seed)
        lowest = loc if loc < lowest else lowest
    print("bingo!! ")
print(lowest)
