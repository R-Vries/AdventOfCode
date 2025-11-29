package aoc.Day11;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Day11 {
    public static final File file = new File("src/aoc/files/monkey.txt");
    private final int numMonkeys;

    public Day11(int numMonkeys) {
        this.numMonkeys = numMonkeys;
    }

    public static void main(String[] args) {
        Day11 d = new Day11(8);
        System.out.println(d.part1(file));
        System.out.println(d.part2(file));
    }

    public int part2(File file) {
        Monkey[] monkeys = setup(file);
        turns(monkeys, false, 10000);
        return monkeyBusiness(monkeys);
    }

    public int part1(File file) {
        Monkey[] monkeys = setup(file);
        turns(monkeys, true, 20);
        return monkeyBusiness(monkeys);
    }

    public int monkeyBusiness(Monkey[] monkeys) {
        List<Integer> inspections = new ArrayList<>();
        for (int i = 0; i < numMonkeys; i++) {
            inspections.add(monkeys[i].getInspections());
        }
        int highest = Collections.max(inspections);
        inspections.remove((Integer) highest);
        int secondHighest = Collections.max(inspections);
        return highest * secondHighest;
    }

    public void turns(Monkey[] monkeys, boolean part1, int rounds) {
        // do the rounds
        for (int i = 0; i < rounds; i++) {
            for (Monkey m : monkeys) {
                m.turn(part1);
            }
            System.out.println("round: " + i);
        }
    }

    public Monkey[] setup(File file) {
        Monkey[] monkeys = new Monkey[numMonkeys];
        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                int monkeyNumber = Integer.parseInt(sc.nextLine().split(" ")[1].substring(0, 1));
                List<Integer> startItems = getItems(sc.nextLine());
                String line = sc.nextLine();
                String operation = line.substring(23, 24);
                int factor = 0;
                String factorString = line.substring(25);
                if (!factorString.equals("old")) {
                    factor = Integer.parseInt(factorString);
                }
                int divisor = Integer.parseInt(sc.nextLine().substring(21));
                int[] throwsTo = new int[2];
                throwsTo[0] = Integer.parseInt(sc.nextLine().substring(29));
                throwsTo[1] = Integer.parseInt(sc.nextLine().substring(30));
                monkeys[monkeyNumber] = new Monkey(startItems, factor, operation, divisor, throwsTo);
                if (sc.hasNextLine()) {
                    sc.nextLine();      // skip the empty line
                }
            }
            // initialise the throwTo monkey arrays
            for (Monkey monkey : monkeys) {
                monkey.init(monkeys);
            }
        } catch (FileNotFoundException e) {
            monkeys = null;
        }
        return monkeys;
    }

    public List<Integer> getItems(String line) {
        List<Integer> result = new ArrayList<>();
        line = line.substring(17);  //only get the numbers
        String[] split = line.split(",");
        for (String number : split) {
            int value = Integer.parseInt(number.substring(1));
            result.add(value);
        }
        return result;
    }
}
