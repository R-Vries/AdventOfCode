import functools

def parse(input):
    result = {}
    for line in input:
        parts = line.split(' ')
        result.update({parts[0]: int(parts[1])})
    return result


scores = {'J': 0, '2': 1, '3': 2, '4': 3, '5': 4, '6': 5, '7': 6, '8': 7, '9': 8, 'T': 9, 'Q': 10, 'K': 11, 'A': 12}
cards = ['2', '3', '4', '5', '6', '7', '8', '9', 'T', 'Q', 'K', 'A']    # Jack not included


def has_three(hand):
    return hand.count(hand[0]) == 3 or hand.count(hand[1]) == 3 or hand.count(hand[2]) == 3


def has_two(hand):
    if hand.count(hand[0]) == 2:
        return True, hand[0]
    if hand.count(hand[1]) == 2:
        return True, hand[1]
    if hand.count(hand[2]) == 2:
        return True, hand[2]
    if len(hand) > 3 and hand.count(hand[3]) == 2:
        return True, hand[3]
    return False, -1


def create_hand(hand, new_card, index):
    string_list = list(hand)
    string_list[index] = new_card
    return "".join(string_list)

def get_best_type(hand):
    jacks = [i for i in range(len(hand)) if hand[i] == 'J']
    best_type = 0
    if len(jacks) == 0:
        return get_type(hand)
    if len(jacks) == 1:
        for card in cards:
            type = get_type(create_hand(hand, card, jacks[0]))
            if type > best_type:
                best_type = type
    elif len(jacks) == 2:
        for card1 in cards:
            new_hand = create_hand(hand, card1, jacks[0])
            for card2 in cards:
                type = get_type(create_hand(new_hand, card2, jacks[1]))
                if type > best_type:
                    best_type = type
    elif len(jacks) == 3:
        for card1 in cards:
            new_hand = create_hand(hand, card1, jacks[0])
            for card2 in cards:
                new_hand = create_hand(new_hand, card2, jacks[1])
                for card3 in cards:
                    type = get_type(create_hand(new_hand, card3, jacks[2]))
                    if type > best_type:
                        best_type = type
    else:
        best_type = 6
    return best_type


def get_type(hand):
    if hand.count(hand[0]) == 5:
        return 6
    if hand.count(hand[0]) == 4 or hand.count(hand[1]) == 4:
        return 5
    if has_three(hand) and has_two(hand)[0] and len(set(hand)) == 2:
        return 4
    if hand.count(hand[0]) == 3 or hand.count(hand[1]) == 3 or hand.count(hand[2]) == 3:
        return 3
    if has_two(hand)[0]:
        pair = has_two(hand)[1]
        if has_two(hand.replace(pair, ''))[0]:
            return 2
        else:
            return 1
    return 0


def compare(hand1, hand2):
    # compare both hands
    if get_best_type(hand1) > get_best_type(hand2):
        return 1
    if get_best_type(hand2) > get_best_type(hand1):
        return -1
    i = 0
    while i < len(hand1):
        if scores[hand1[i]] > scores[hand2[i]]:
            return 1
        if scores[hand2[i]] > scores[hand1[i]]:
            return -1
        i += 1
    return 0


f = open('inputs/input7.txt')
hands = parse(f)
types = {}
ranking = []
for hand in hands:
    types.update({hand: get_type(hand)})
    ranking.append(hand)

ranking.sort(key=functools.cmp_to_key(compare))
result = 0
i = 0
for hand in ranking:
    result += (i + 1) * hands[hand]
    i += 1
print(result)

