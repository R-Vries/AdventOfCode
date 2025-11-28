from Python.utils import Processor
import heapq


def parse(input):
    return {(i, j): int(c) for i, r in enumerate(list(input)) for j, c in enumerate(r.strip())}


def solve(puzzle):
    queue = [(0, 0, 0, 0, 0)]      # queue: [(heat, i, j, previous_x, previous_y)]
    seen = set()
    end = max(puzzle)
    while queue:
        heat, x, y, px, py = heapq.heappop(queue)
        if (x, y) == end:
            return heat
        if (x, y, px, py) in seen:
            continue
        seen.add((x, y, px, py))
        # Only make turns, don't go forward or back
        for dx, dy in {(1, 0), (0, 1), (-1, 0), (0, -1)} - {(px, py), (-px, -py)}:
            a, b, h = x, y, heat
            # Move 4-10 steps in each direction
            for i in range(1, 11):
                a, b = a + dx, b + dy
                if (a, b) in puzzle:
                    h += puzzle[a, b]
                    if i >= 4:
                        heapq.heappush(queue, (h, a, b, dx, dy))


p = Processor(parse, solve, 17)
p.execute()
