using AdventOfCode.Core;

int currentYear = DateTime.Now.Year;
int currentDay = DateTime.Now.Day;

int year;
int day;

if (args.Length >= 2)
{
    year = int.Parse(args[0]);
    day = int.Parse(args[1]);
}
else
{
    Console.Write($"Year [{currentYear}]: ");
    var yearInput = Console.ReadLine();

    Console.Write($"Day [{currentDay}]: ");
    var dayInput = Console.ReadLine();

    year = string.IsNullOrWhiteSpace(yearInput)
        ? currentYear
        : int.Parse(yearInput);

    day = string.IsNullOrWhiteSpace(dayInput)
        ? currentDay
        : int.Parse(dayInput);
}

var runner = new Runner();
runner.Run(year, day);
