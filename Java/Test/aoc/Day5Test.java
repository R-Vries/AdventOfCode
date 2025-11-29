package aoc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

class Day5Test {
    Day5 d;

    @BeforeEach
    void init() {
        d = new Day5();
    }

    @Test
    void testRead() {
        String test = " 1   2   3   4   5   6   7   8   9";
        assertEquals(9, Integer.parseInt(String.valueOf(test.charAt(test.length() - 1))));
        assertFalse(test.charAt(0) != ' ');
        assertTrue(test.charAt(1) != ' ');
    }

    @Test
    void testGetOrder() {
        assertTrue(d.getOrder().get(0).contains('R'));
        assertTrue(d.getOrder().get(0).contains('T'));
        assertTrue(d.getOrder().get(0).contains('P'));
        assertFalse(d.getOrder().get(0).contains('A'));

        assertTrue(d.getOrder().get(8).contains('D'));
        assertFalse(d.getOrder().get(8).contains('R'));

        List<Stack<Character>> res = d.getOrder();
        assertEquals('T', res.get(0).pop());
        assertEquals('R', res.get(0).pop());
        assertEquals('R', res.get(1).pop());
        assertEquals('H', res.get(8).pop());

    }
}
