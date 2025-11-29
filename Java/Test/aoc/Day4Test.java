package aoc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day4Test {
    Day4 d;

    @BeforeEach
    void setup() {
        d = new Day4();
    }

    @Test
    void testGetNumbers() {
        ArrayList<Integer> test1 = new ArrayList<>(List.of(3, 4, 5));
        assertEquals(test1, d.getNumbers("3-5"));

        ArrayList<Integer> test2 = new ArrayList<>(List.of(4));
        assertEquals(test2, d.getNumbers("4-4"));
    }

    /*
5-7,7-9 overlaps in a single section, 7.
2-8,3-7 overlaps all of the sections 3 through 7.
6-6,4-6 overlaps in a single section, 6.
2-6,4-8 overlaps in sections 4, 5, and 6.
*/
    @Test
    void testOverlap() {
        assertEquals(1, d.overlap(d.getNumbers("5-7"), d.getNumbers("7-9")));
        assertEquals(1, d.overlap(d.getNumbers("2-8"), d.getNumbers("3-7")));
        assertEquals(1, d.overlap(d.getNumbers("6-6"), d.getNumbers("4-6")));
        assertEquals(0, d.overlap(d.getNumbers("4-6"), d.getNumbers("7-8")));


    }
}
