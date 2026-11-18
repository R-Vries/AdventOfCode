using AdventOfCode.Years.Y2025;

namespace AdventOfCode.Core;

public static class SolverRegistry
{
    public static ISolver Get(int year, int day)
    {
        return (year, day) switch
        {
            (2025, 1) => new Day01(),
            _ => throw new NotImplementedException()
        };
    }
}