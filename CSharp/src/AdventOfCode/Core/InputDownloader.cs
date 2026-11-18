using System.Net.Http;

namespace AdventOfCode.Core;

public sealed class InputDownloader
{
    public async Task Download(int year, int day)
    {
        string cookiePath = Path.Combine("..", "..", "..", "..", "..", "..", "cookie.txt");

        Console.WriteLine($"Looking for cookie at: {Path.GetFullPath(cookiePath)}");

        if (!File.Exists(cookiePath))
        {
            throw new FileNotFoundException(
                "Missing cookie.txt in repository root.");
        }

        string sessionCookie = File.ReadAllText(cookiePath).Trim();

        if (string.IsNullOrWhiteSpace(sessionCookie))
        {
            throw new InvalidOperationException(
                "cookie.txt is empty.");
        }

        using var client = new HttpClient();

        client.DefaultRequestHeaders.Add(
            "Cookie",
            $"session={sessionCookie}");

        string response = await client.GetStringAsync(
            $"https://adventofcode.com/{year}/day/{day}/input");

        string directory = Path.Combine(
            "../../../Data",
            year.ToString(),
            "Inputs");

        Directory.CreateDirectory(directory);

        string filePath = Path.Combine(
            directory,
            $"day{day:D2}.txt");

        await File.WriteAllTextAsync(filePath, response);

        Console.WriteLine(
            $"Downloaded input to {filePath}");
    }
}