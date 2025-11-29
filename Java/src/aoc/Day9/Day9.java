package aoc.Day9;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Day9 {
    public static final File file = new File("src/aoc/files/rope.txt");
    public static final int ROW = 10000;
    public static final int SIZE = ROW * ROW;
    private int head;
    private int tail;
//    private int[] list;     // keeps track for each spot, how many times it is visited
    private int[] knots;
    private Map<Integer, Integer> count;

    public static void main(String[] args) {
        Day9 d = new Day9();
        System.out.println(d.part1());
        System.out.println(d.part2());
    }

    private int part2() {
        count = new HashMap<>();
        int result = 0;
        try (Scanner sc = new Scanner(file)) {
            knots = new int[9];
            head = SIZE / 2;
            Arrays.fill(knots, head);
            addToCount(head);
            while (sc.hasNextLine()) {
                String[] instruction = sc.nextLine().split(" ");
                execute2(instruction);
            }
            // now calculate num places visited >= 1
            for (int amount : count.values()) {
                if (amount > 0) {
                    result++;
                }
            }
        } catch (FileNotFoundException e) {
            result = -1;
        }
        return result;
    }

    private void addToCount(int pos) {
        if (count.get(pos) == null) {
            count.put(pos, 1);
        } else {
            count.put(pos, count.get(pos) + 1);
        }
    }

    private int part1() {
        count = new HashMap<>();
        int result = 0;
        try (Scanner sc = new Scanner(file)) {
//            list = new int[SIZE];
            head = SIZE / 2;
            tail = head;
            addToCount(tail);   // initial position
            while (sc.hasNextLine()) {
                String[] instruction = sc.nextLine().split(" ");
                execute(instruction);
            }
            // now calculate num places visited >= 1
            for (int amount : count.values()) {
                if (amount > 0) {
                    result++;
                }
            }
        } catch (FileNotFoundException e) {
            result = -1;
        }
        return result;
    }

    private void execute2(String[] instruction) {
        int amount = Integer.parseInt(instruction[1]);
        for (int i = 0; i < amount; i++) {
            changeHead(instruction[0]);
            // move each tail
            for (int j = 0; j < knots.length; j++) {
                int previousNode;
                if (j == 0) {
                    previousNode = head;
                } else {
                    previousNode = knots[j - 1];
                }
                knots[j] = moveKnot(knots[j], previousNode);
                // add position of last knot
                if (j == (knots.length - 1)) {
                    addToCount(knots[j]);
                }
            }
        }
    }

    private int moveKnot(int knot1, int knot2) {
        if (!touching(knot1, knot2)) {
            int possibleLocation;
            // move orthogonally
            possibleLocation = knot1 + 1;
            if (check(possibleLocation)) {
                return possibleLocation;
            }
            possibleLocation = knot1 - 1;
            if (check(possibleLocation)) {
                return possibleLocation;
            }
            possibleLocation = knot1 - ROW;
            if (check(possibleLocation)) {
                return possibleLocation;
            }
            possibleLocation = knot1 + ROW;
            if (check(possibleLocation)) {
                return possibleLocation;
            }
            // move diagonally
            possibleLocation = knot1 + 1 + ROW;
            if (check(possibleLocation)) {
                return possibleLocation;
            }
            possibleLocation = knot1 - 1 + ROW;
            if (check(possibleLocation)) {
                return possibleLocation;
            }
            possibleLocation = knot1 + 1 - ROW;
            if (check(possibleLocation)) {
                return possibleLocation;
            }
            possibleLocation = knot1 - 1 - ROW;
            check(possibleLocation);
            return possibleLocation;
        }
        return knot1;
    }

    private void changeHead(String direction) {
        switch (direction) {
            case "D" -> head += ROW;
            case "U" -> head -= ROW;
            case "L" -> head -= 1;
            case "R" -> head += 1;
            default -> System.out.println("Not recognized!");
        }
    }

    /**
     * Executes one instruction. Sets the head and the tail to the right index.
     * Also adds to the list of visited places
     * @param instruction the instruction
     */
    private void execute(String[] instruction) {
        int amount = Integer.parseInt(instruction[1]);
        for (int i = 0; i < amount; i++) {
            changeHead(instruction[0]);
            // move tail
            tail = moveKnot(tail, head);
            addToCount(tail);
        }
    }

    private boolean check(int possibleLocation) {
        return nextTo(possibleLocation);
    }

    private boolean nextTo(int location) {
        return location + ROW == head
                || location - ROW == head
                || location + 1 == head
                || location - 1 == head;
    }

    private boolean touching(int knot1, int knot2) {
        return knot1 + ROW + 1 == knot2
                || knot1 + ROW == knot2
                || knot1 + ROW - 1 == knot2
                || knot1 + 1 == knot2
                || knot1 == knot2
                || knot1 - 1 == knot2
                || knot1 - ROW + 1 == knot2
                || knot1 - ROW == knot2
                || knot1 - ROW - 1 == knot2;
    }
}
