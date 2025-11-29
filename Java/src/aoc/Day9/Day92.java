package aoc.Day9;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Day92 {
    public static final File file = new File("src/aoc/files/rope.txt");
    private Knot head;
    private Knot tail;
    private Knot[] knots;
    private Map<Knot, Integer> count;

    public static void main(String[] args) {
        Day92 d = new Day92();
        System.out.println(d.part1());
        System.out.println(d.part2());
    }

    private int part2() {
        count = new HashMap<>();
        int result = 0;
        try (Scanner sc = new Scanner(file)) {
            knots = new Knot[9];
            head = new Knot();
            for (int i = 0; i < knots.length; i++) {
                knots[i] = new Knot();
            }
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

    private void addToCount(Knot pos) {
        Knot copy = pos.getCopy();
        if (count.get(pos) == null) {
            count.put(copy, 1);
        } else {
            count.put(copy, count.get(copy) + 1);
        }
    }

    private int part1() {
        count = new HashMap<>();
        int result = 0;
        try (Scanner sc = new Scanner(file)) {
            head = new Knot();
            tail = new Knot();
            addToCount(tail);
            while (sc.hasNextLine()) {
                String[] instruction = sc.nextLine().split(" ");
                execute(instruction);
            }
            result = count.size();
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
                Knot previousNode;
                if (j == 0) {
                    previousNode = head;
                } else {
                    previousNode = knots[j - 1];
                }
                moveKnot(knots[j], previousNode);
                // add position of last knot
                if (j == (knots.length - 1)) {
                    addToCount(knots[j]);
                }
            }
        }
    }

    private void moveKnot(Knot knot1, Knot knot2) {
        if (!knot1.touchesKnot(knot2)) {
            if (knot1.sameColumnRow(knot2)) {
                knot1.testMove(0, 1, knot2, true);
                knot1.testMove(0, -1, knot2, true);
                knot1.testMove(1, 0, knot2, true);
                knot1.testMove(-1, 0, knot2, true);
            } else {
                // not in same column or row, must go diagonal
                if (knot1.testMove(1, 1, knot2, true)) {

                } else if (knot1.testMove(1, -1, knot2, true)) {

                } else if (knot1.testMove(-1, 1, knot2, true)) {

                } else if (knot1.testMove(-1, -1, knot2, true)) {

                } else if (knot1.testMove(1, 1, knot2, false)) {

                } else if (knot1.testMove(1, -1, knot2, false)) {

                } else if (knot1.testMove(-1, 1, knot2, false)) {

                } else if (knot1.testMove(-1, -1, knot2, false)) {

                }
            }
        }
    }

    private void changeHead(String direction) {
        switch (direction) {
            case "D" -> head.move(0, -1);
            case "U" -> head.move(0, 1);
            case "L" -> head.move(-1, 0);
            case "R" -> head.move(1, 0);
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
            moveKnot(tail, head);
            addToCount(tail);
        }
    }
}

