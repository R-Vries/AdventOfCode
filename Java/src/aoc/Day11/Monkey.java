package aoc.Day11;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Monkey {
    private final List<BigInteger> currentItems;
    private final int factor;
    private final String operation;
    private final int divisor;
    private final int[] monkeyNumbers;
    private Monkey[] monkeys;      // 0 if true, 1 if false
    private int inspections;

    public Monkey(List<Integer> currentItems, int factor, String operation, int divisor, int[] monkeyNumbers) {
        this.currentItems = new ArrayList<>();
        for (Integer item : currentItems) {
            this.currentItems.add(BigInteger.valueOf(item));
        }
        this.factor = factor;
        this.operation = operation;
        this.divisor = divisor;
        this.monkeyNumbers = monkeyNumbers;
        inspections = 0;
    }

    public void turn(boolean part1) {
        for (int i = 0; i < currentItems.size(); i++) {
            BigInteger item = currentItems.get(i);
            item = doOperation(item);
            if (part1) {
                item = item.divide(BigInteger.valueOf(3));
            }
            if (item.mod(BigInteger.valueOf(divisor)).equals(BigInteger.valueOf(0))) {
                monkeys[0].addItem(item);
            } else {
                monkeys[1].addItem(item);
            }
            currentItems.remove(i);
            i--;
            inspections++;
        }
    }

    private void addItem(BigInteger item) {
        currentItems.add(item);
    }

    private BigInteger doOperation(BigInteger worry) {
        BigInteger realFactor = BigInteger.valueOf(factor);
        if (factor == 0) {
            realFactor = worry;     // old * old
        }
        if (operation.equals("*")) {
            worry = worry.multiply(realFactor);
        } else {
            worry = worry.add(realFactor);
        }
        return worry;
    }

    @Override
    public String toString() {
        return "Monkey{" +
                "currentItems=" + currentItems +
                ", factor=" + factor +
                ", operation='" + operation + '\'' +
                ", divisor=" + divisor +
                '}';
    }

    public void init(Monkey[] allMonkeys) {
        monkeys = new Monkey[2];
        monkeys[0] = allMonkeys[monkeyNumbers[0]];
        monkeys[1] = allMonkeys[monkeyNumbers[1]];
    }

    public int getInspections() {
        return inspections;
    }
}
