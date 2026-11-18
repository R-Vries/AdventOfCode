namespace AdventOfCode.Core;

public abstract class Solver<T> : ISolver
{
    public abstract T Parse(string input);
    public abstract object Part1(T input);
    public abstract object Part2(T input); 

    object ISolver.Part1(String input)
        => Part1(Parse(input));

    object ISolver.Part2(string input)
        => Part2(Parse(input)); 
}