using AdventOfCode.Core;

namespace AdventOfCode.Years.Y2025;

public sealed class Day01 : Solver<List<Move>>
{
    public override List<Move> Parse(string input)
    {

        return input
            .Split('\n', StringSplitOptions.RemoveEmptyEntries)
            .Select(line => new Move(
                line[..1],
                int.Parse(line[1..])
            ))
            .ToList();
    }

    public override object Part1(List<Move> data)
    {
        int visitedZero = 0;
        int current = 50;

        foreach (var move in data)
        {
            if (current == 0)
            {
                visitedZero++;
            }

            current = move.Direction switch
            {
                "L" => Mod(current - move.Amount, 100),
                "R" => Mod(current + move.Amount, 100),
                _ => current
            };
        }

        return visitedZero;
    }

    public override object Part2(List<Move> data)
    {
        int visitedZero = 0;
        int current = 50;

        foreach (var move in data)
        {
            int change = move.Direction switch
            {
                "L" => -move.Amount,
                "R" => move.Amount,
                _ => 0
            };

            visitedZero += PassesZero(current, move);
            current = Mod(current + change, 100);
        }

        return visitedZero;
    }

    private static int PassesZero(int current, Move move)
    {
        int cur = current;
        int visitedZero = 0;

        int step = move.Direction == "L"
            ? -1
            : 1;

        for (int i = 0; i < move.Amount; i++)
        {
            cur = Mod(cur + step, 100);

            if (cur == 0)
            {
                visitedZero++;
            }
        }

        return visitedZero;
    }

    public static int Mod(int value, int modulus)
{
    return ((value % modulus) + modulus) % modulus;
}
}

public sealed record Move(
    string Direction,
    int Amount
);

