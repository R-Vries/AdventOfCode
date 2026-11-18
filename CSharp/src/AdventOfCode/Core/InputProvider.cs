using System.IO;

namespace AdventOfCode.Core;

public sealed class InputProvider
{
    public string Load(int year, int day)
    {
        string path = GetInputPath(year, day);

        if (!File.Exists(path))
        {
            Console.WriteLine("Input file not found. Downloading from AoC...");

            var downloader = new InputDownloader();
            downloader.Download(year, day).GetAwaiter().GetResult();
        }

        return File.ReadAllText(path);
    }

    private static string GetInputPath(int year, int day)
    {
        return Path.Combine(
            "../../../Data",
            year.ToString(),
            "Inputs",
            $"day{day:D2}.txt");
    }
}