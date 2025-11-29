package aoc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day1 {
    File file = new File("src/aoc/files/calories.txt");

    public static void main(String[] args) {
        Day1 d = new Day1();
        System.out.println(d.part1());
        System.out.println(d.part2());
    }

    /**
     * Reads a file that counts calories per food item and calculates the highest number.
     * Each group of food items is on a new line.
     */
    private int part1() {
        int highest = 0;
        int current = 0;
        try (Scanner sc = new Scanner(file)) {
            while(sc.hasNextLine()) {
                String line = sc.nextLine();
                if (line.length() != 0) {
                    int calories = Integer.parseInt(line);
                    current += calories;
                } else {
                    if (current > highest) {
                        highest = current;
                    }
                    current = 0;
                }
            }
            return highest;
        } catch (FileNotFoundException e) {
            System.out.println("file not found!");
            return -1;
        }
    }

    /**
     * Get the sum of the top 3 calorie carriers.
     */
    private int part2() {
        int current = 0;
        List<Integer> values = new ArrayList<>();
        try (Scanner sc = new Scanner(file)) {
            while(sc.hasNextLine()) {
                String line = sc.nextLine();
                if (line.length() != 0) {
                    int calories = Integer.parseInt(line);
                    current += calories;
                } else {
                    values.add(current);
                    current = 0;
                }
            }
            int num1 = Collections.max(values);
            values.remove((Integer) num1);
            int num2 = Collections.max(values);
            values.remove((Integer) num2);
            int num3 = Collections.max(values);
            values.remove((Integer) num3);
            return num1 + num2 + num3;
        } catch (FileNotFoundException e) {
            System.out.println("file not found!");
            return -1;
        }
    }
}
