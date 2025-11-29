package aoc;

import aoc.Day8;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day8Test {
    Day8 d;
    String row = "222322213033345255533423306545562424165440655115256171674620442636621123532003623343351021112300040";

    @BeforeEach
    void init() {
        d = new Day8();
    }

    @Test
    void testGetList() {
        File file = new File("src/aoc/files/trees.txt");
        assertEquals(3, d.getList(file).get(0).get(3));
        assertEquals(1, d.getList(file).get(1).get(row.length() - 1));
        assertEquals(99, d.getList(file).size());
        for (int i = 0; i < row.length(); i++) {
            assertEquals(99, d.getList(file).get(i).size());
        }
    }
}
