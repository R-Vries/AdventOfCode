package main.java;

public record AppConfig(int year, int day, Number expectedPart1, Number expectedPart2) {

    public static AppConfig fromArgs(String[] args) {
        if (args.length < 2) {
            throw new IllegalArgumentException("Usage: <year> <day> [expectedPart1] [expectedPart2]");
        }

        int year = Integer.parseInt(args[0]);
        int day = Integer.parseInt(args[1]);
        Number expectedPart1 = args.length > 2 ? Integer.parseInt(args[2]) : null;
        Number expectedPart2 = args.length > 3 ? Integer.parseInt(args[3]) : null;

        return new AppConfig(year, day, expectedPart1, expectedPart2);
    }
}
