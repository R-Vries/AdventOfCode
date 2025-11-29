package aoc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day10 {
    public static final File file = new File("src/aoc/files/instructions.txt");
    public static final String LIT = "#";
    public static final String DARK = ".";
    public static final String ADD = "addx";
    public static final int ROWS = 6;
    public static final int COLUMNS = 40;

    public static void main(String[] args) {
        Day10 d = new Day10();
        System.out.println(d.part1());
        d.part2();
    }

    private void part2() {
        Map<Integer, Integer> cycles = getCycles(file);
        String[] CRT = getCRT(cycles);
        print(CRT);
    }

    public void print(String[] CRT) {
        for (int i = 0; i < CRT.length; i += COLUMNS) {
            for (int j = i; j < i + COLUMNS; j++) {
                System.out.print(CRT[j]);
            }
            System.out.println();
        }
    }

    public String[] getCRT(Map<Integer, Integer> cycles) {
        String[] CRT = new String[ROWS * COLUMNS];
        int row = 0;
        int currentPos = 0;
        for (int pos : cycles.values()) {
            if (currentPos != 0 && currentPos % 40 == 0) {
                row++;
            }
            int actualPos = pos + row * 40;
            if (actualPos - 1 == currentPos || actualPos == currentPos || actualPos + 1 == currentPos) {
                CRT[currentPos] = LIT;
            } else if (currentPos != CRT.length){
                CRT[currentPos] = DARK;
            }
            currentPos++;
        }
        return CRT;
    }

    private int part1() {
        Map<Integer, Integer> cycles = getCycles(file); // maps cycle to value of register
        int result = 0;
        // calculate strength add sum values
        for (int i = 20; i <= 220; i += 40) {
            int strength = i * cycles.get(i - 1);
            result += strength;
        }
        return result;
    }

    /**
     * Get a map with the value of the register during each cycle.
     * Key 0 means during the first cycle, key 1 means during the second cycle, etc.
     * @param file the file containing the instructions
     * @return the map
     */
    public Map<Integer, Integer> getCycles(File file) {
        Map<Integer, Integer> result = new HashMap<>();
        result.put(0, 1);
        try (Scanner sc = new Scanner(file)) {
            int currentCycle = 0;
            while (sc.hasNextLine()) {
                currentCycle++;
                String[] line = sc.nextLine().split(" ");
                String instr = line[0];
                int previous = result.get(currentCycle -1);
                // execute instruction
                if (instr.equals(ADD)) {
                    int amount = Integer.parseInt(line[1]);
                    result.put(currentCycle, previous);
                    currentCycle++;
                    result.put(currentCycle, previous + amount);
                } else {
                    // noop
                    result.put(currentCycle, previous);
                }
            }
        } catch (FileNotFoundException e) {
            result = null;
            System.out.println("File not found!");
        }
        return result;
    }
}
