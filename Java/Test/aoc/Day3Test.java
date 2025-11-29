package aoc;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day3Test {
    Day3 d;

    @BeforeEach
    void init() {
        d = new Day3();
    }

    @Test
    void testGetPoints() {
        assertEquals(1, d.getPoints('a'));
        assertEquals(26, d.getPoints('z'));
        assertEquals(51, d.getPoints('Y'));
        assertEquals(52, d.getPoints('Z'));
    }

    @Test
    void testTestLine() {
        assertEquals(16, d.testLine("vJrwpWtwJgWrhcsFMMfFFhFp"));
        assertEquals(38, d.testLine("jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL"));
    }

    @Test
    void testFindCommon() {
        assertEquals(18, d.findCommon("vJrwpWtwJgWrhcsFMMfFFhFp", "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL", "PmmdzqPrVvPwwTWBwg"));
    }
}
