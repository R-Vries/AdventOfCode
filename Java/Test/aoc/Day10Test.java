package aoc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day10Test {
    Day10 d;
    Map<Integer, Integer> cycles;
    static final File testFile = new File("src/aoc/files/simpleInstructions");

    @BeforeEach
    void init() {
        d = new Day10();
        cycles = d.getCycles(testFile);
    }

    @Test
    void testValues() {
        assertEquals(21, cycles.get(19));
        assertEquals(19, cycles.get(59));
        assertEquals(18, cycles.get(99));
        assertEquals(21, cycles.get(139));
        assertEquals(16, cycles.get(179));
        assertEquals(18, cycles.get(219));
    }

    @Test
    void testStrength() {
        assertEquals(420, (19 + 1) * cycles.get(19));
        int result = 0;
        for (int i = 19; i <= 219; i += 40) {
            int strength = (i + 1) * cycles.get(i);
            result += strength;
        }
        assertEquals(13140, result);
    }

    @Test
    void testGetCRT() {
        String[] CRT = d.getCRT(cycles);
        assertEquals("#", CRT[0]);
        assertEquals("#", CRT[1]);
        assertEquals(".", CRT[2]);
        assertEquals(".", CRT[3]);
        assertEquals("#", CRT[4]);
        assertEquals("#", CRT[5]);
        assertEquals(".", CRT[6]);
        assertEquals(".", CRT[7]);
    }

    @Test
    void testPrint() {
        d.print(d.getCRT(cycles));
    }
}
