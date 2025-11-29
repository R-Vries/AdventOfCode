package aoc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Day4 {
    public static final File file = new File("src/aoc/files/pairs.txt");

    public static void main(String[] args) {
        Day4 d = new Day4();
        System.out.println(d.part1());
        System.out.println(d.part2());
    }

    private int part2() {
        int number = 0;
        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] assignments = line.split(",");
                List<Integer> range1 = getNumbers(assignments[0]);
                List<Integer> range2 = getNumbers(assignments[1]);
                number += overlap(range1, range2);
            }
        } catch (FileNotFoundException e) {
            number = -1;
        }
        return number;
    }

    public int overlap(List<Integer> range1, List<Integer> range2) {
        int result = 0;
        if (!(Collections.max(range1) < Collections.min(range2) || Collections.max(range2) < Collections.min(range1))) {
            result = 1;
        }
        return result;
    }

    private int part1() {
        int number = 0;
        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] assignments = line.split(",");
                List<Integer> range1 = getNumbers(assignments[0]);
                List<Integer> range2 = getNumbers(assignments[1]);
                number += encapsulates(range1, range2);
            }
        } catch (FileNotFoundException e) {
            number = -1;
        }
        return number;
    }

    /**
     * Checks if one interval completely encapsulates another
     * @param range1 the first interval
     * @param range2 the second interval
     * @return 1 if one of the intervals encapsulates the other, 0 if not
     */
    public int encapsulates(List<Integer> range1, List<Integer> range2) {
        int result = 0;
        if ((Collections.min(range1) <= Collections.min(range2) && Collections.max(range1) >= Collections.max(range2)) ||
            (Collections.min(range2) <= Collections.min(range1) && Collections.max(range2) >= Collections.max(range1))) {
            result = 1;
        }
        return result;
    }

    /**
     * Returns a list with the numbers described in the interval
     * @param assignment the interval
     * @return the list of numbers
     */
    public List<Integer> getNumbers(String assignment) {
        ArrayList<Integer> result = new ArrayList<>();
        String[] numbers = assignment.split("-");
        int first = Integer.parseInt(numbers[0]);
        int last = Integer.parseInt(numbers[1]);
        for (int i = first; i <= last; i++) {
            result.add(i);
        }
        return result;
    }
}
