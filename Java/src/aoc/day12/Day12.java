package aoc.day12;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Day12 {
    public static final File file = new File("src/aoc/day12/Day12.txt");
    public static final char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private int[] dest = new int[2];

    public static void main(String[] args) {
        Day12 d = new Day12();
        System.out.println(d.part1());
    }

    private int part1() {
        int result = 0;
        List<List<Integer>> map = parseInput(file);
        return result;
    }

    private List<List<Integer>> parseInput(File file) {
        List<List<Integer>> result = new ArrayList<>();
        try (Scanner sc = new Scanner(file)) {
            int i = 0;
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                List<Integer> row = new ArrayList<>();
                for (int j = 0; j < line.length(); j++) {
                    if (line.charAt(j) == 'E') {
                        dest[0] = i;
                        dest[1] = j;
                    }
                    row.add(Arrays.binarySearch(alphabet, line.charAt(j)));
                }
                result.add(row);
                i++;
            }
        } catch (FileNotFoundException e) {
            result = null;
        }
        return result;
    }
}
