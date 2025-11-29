package aoc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Day3 {
    File file = new File("src/aoc/files/rucksack.txt");
    public static final String PRIORITIES = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static void main(String[] args) {
        Day3 d = new Day3();
        System.out.println(d.part1());
        System.out.println(d.part2());
    }

    public int part2() {
        int points = 0;
        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String line1 = sc.nextLine();
                String line2 = sc.nextLine();
                String line3 = sc.nextLine();
                points += findCommon(line1, line2, line3);
            }
        } catch (FileNotFoundException e) {
            points = -1;
        }
        return points;
    }

    public int findCommon(String line1, String line2, String line3) {
        ArrayList<Character> passed = new ArrayList<>();
        int points = 0;
        for (int i = 0; i < line1.length(); i++) {
            if (line2.indexOf(line1.charAt(i)) != -1 && line3.indexOf(line1.charAt(i)) != -1 && !passed.contains(line1.charAt(i))) {
                points += getPoints(line1.charAt(i));
                passed.add(line1.charAt(i));
            }
        }
        return points;
    }


    private int part1() {
        try (Scanner sc = new Scanner(file)) {
            int points = 0;
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                points += testLine(line);
            }
            return points;
        } catch (FileNotFoundException e) {
            return -1;
        }
    }

    public int testLine(String line) {
        int points = 0;
        ArrayList<Character> passed = new ArrayList<>();
        int half = line.length() / 2;
        String first = line.substring(0, half);
        String second = line.substring(half);
        for (int i = 0; i < half; i++) {
            for (int j = 0; j < half; j++) {
                if (first.charAt(i) == second.charAt(j) && !passed.contains(first.charAt(i))) {
                    points += getPoints(first.charAt(i));
                    passed.add(first.charAt(i));
                }
            }
        }
        return points;
    }

    public int getPoints(char character) {
        return PRIORITIES.indexOf(character) + 1;
    }
}
