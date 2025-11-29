package aoc;

import aoc.Day11.Day11;
import aoc.Day11.Monkey;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day11Test {
    Day11 d;
    public static final File file = new File("src/aoc/files/monkey2.txt");

    @BeforeEach
    void init() {
        d = new Day11(4);
    }

    @Test
    void testRounding() {
        assertEquals(500, 1501 / 3);
        assertEquals(620, 1862 / 3);
        assertEquals(23, 71 / 3);
        assertEquals(26, 80 / 3);
    }

    @Test
    void testGetItems() {
        List<Integer> items = d.getItems("  Starting items: 83, 88, 96, 79, 86, 88, 70");
        List<Integer> test = Arrays.asList(83, 88, 96, 79, 86, 88, 70);
        assertEquals(test, items);
    }

    @Test
    void testPart1() {
        assertEquals(10605, d.part1(file));
    }

    @Test
    void testPart2() {
        Monkey[] monkeys = d.setup(file);
        d.turns(monkeys, false, 1000);
        assertEquals(5204, monkeys[0].getInspections());
    }
}
