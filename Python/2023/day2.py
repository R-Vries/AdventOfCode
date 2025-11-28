from Python.Processor import Processor
import sys, os

sys.path.append(os.path.dirname(os.path.dirname(os.path.abspath(__file__))))


# Game 1: 7 red, 8 blue; 6 blue, 6 red, 2 green; 2 red, 6 green, 8 blue; 9 green, 2 red, 4 blue; 6 blue, 4 green
def parse(input):
    rounds = []
    for line in input:
        number = int(line.split(': ')[0].split(' ')[1])
        line = line.split(': ')[1]
        games = line.split('; ')
        game_list = []
        for game in games:      # game: 7 red, 8 blue
            values = [0, 0, 0]
            colors = game.split(', ')
            for color in colors:        # color: 7 red
                pair = color.split(' ')
                pair[1] = pair[1].strip()
                amount = pair[0]
                if pair[1] == 'red':
                    values[0] = int(amount)
                elif pair[1] == 'green':
                    values[1] = int(amount)
                else:
                    values[2] = int(amount)
            game_list.append(values)
        rounds.append((number, game_list))
    return rounds


def possible(games, red, green, blue):
    result = True
    for game in games:
        if game[0] > red or game[1] > green or game[2] > blue:
            result = False
    return result


def minimum(games):
    min_set = [0, 0, 0]
    result = 1
    for game in games:
        if game[0] > min_set[0]:
            min_set[0] = game[0]
        if game[1] > min_set[1]:
            min_set[1] = game[1]
        if game[2] > min_set[2]:
            min_set[2] = game[2]
    # games: [[1, 0, 2], [3, 4, 12], [0, 1, 8]]
    for num in min_set:
        result *= num
    return result


def solve(puzzle):
    return sum(minimum(round[1]) for round in puzzle)


p = Processor(2, parse, solve, year=2023)
p.run()
