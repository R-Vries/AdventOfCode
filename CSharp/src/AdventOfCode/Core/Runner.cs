namespace AdventOfCode.Core;

public sealed class Runner
{
    public void Run(int year, int day)
    {
        var solver = SolverRegistry.Get(year, day);

        var inputProvider = new InputProvider();

        var input = inputProvider.Load(year, day);

        Console.WriteLine($"Year {year} Day {day}");
        Console.WriteLine();

        Console.WriteLine($"Part 1: {solver.Part1(input)}");
        Console.WriteLine($"Part 2: {solver.Part2(input)}");
    }
}