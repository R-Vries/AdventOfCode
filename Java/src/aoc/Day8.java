package aoc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day8 {
    public static final File file = new File("src/aoc/files/trees.txt");

    public static void main(String[] args) {
        Day8 d = new Day8();
        System.out.println(d.part1());
        System.out.println(d.part2());
    }

    private int part2() {
        int result = 0;
        List<List<Integer>> list = getList(file);
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.get(i).size(); j++) {
                int current = list.get(i).get(j);
                int[] visible = new int[]{0, 0, 0, 0}; //[bottom, top, left, right]

                for (int k = i + 1; k < list.size(); k++) {
                    visible[0]++;
                    if (list.get(k).get(j) >= current) {
                        break;
                    }
                }
                //top
                for (int k = i - 1; k >= 0; k--) {
                    visible[1]++;
                    if (list.get(k).get(j) >= current) {
                       break;
                    }
                }
                //left
                for (int k = j - 1; k >= 0; k--) {
                    visible[2]++;
                    if (list.get(i).get(k) >= current) {
                        break;
                    }
                }
                //right
                for (int k = j + 1; k < list.get(i).size(); k++) {
                    visible[3]++;
                    if (list.get(i).get(k) >= current) {
                        break;
                    }
                }
                int score = visible[0] * visible[1] * visible[2] * visible[3];
                if (score > result) {
                    result = score;
                }
            }
        }
        return result;
    }

    private int part1() {
        int numHidden = 0;
        List<List<Integer>> list = getList(file);
        int total = list.size() * list.get(0).size();
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.get(i).size(); j++) {
                // for each tree, check top, bottom, left and right
                int sidesHidden = 0;
                int current = list.get(i).get(j);

                // bottom
                for (int k = i + 1; k < list.size(); k++) {
                    if (current <= list.get(k).get(j)) {
                        sidesHidden++;
                        break;
                    }
                }
                // top
                for (int k = i - 1; k >= 0; k--) {
                    if (current <= list.get(k).get(j)) {
                        sidesHidden++;
                        break;
                    }
                }
                // left
                for (int k = j - 1; k >= 0; k--) {
                    if (current <= list.get(i).get(k)) {
                        sidesHidden++;
                        break;
                    }
                }
                // right
                for (int k = j + 1; k < list.get(i).size(); k++) {
                    if (current <= list.get(i).get(k)) {
                        sidesHidden++;
                        break;
                    }
                }
                if (sidesHidden == 4) {
                    numHidden ++;
                }
            }
        }
        return total - numHidden;
    }

    /**
     * Creates a two-dimensional list containing all the heights
     * @return the list
     */
    public List<List<Integer>> getList(File file) {
        List<List<Integer>> result = new ArrayList<>();
        try (Scanner sc = new Scanner(file)) {
            int i = 0;
            while (sc.hasNextLine()) {
                result.add(new ArrayList<>());
                String line = sc.nextLine();
                for (int j = 0; j < line.length(); j++) {
                    int height = Integer.parseInt(String.valueOf(line.charAt(j)));
                    result.get(i).add(height);
                }
                i++;
            }
        } catch (FileNotFoundException e) {
            result = null;
        }
        return result;
    }
}
