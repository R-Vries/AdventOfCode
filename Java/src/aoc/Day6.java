package aoc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Day6 {
    File file = new File("src/aoc/files/signal.txt");
    public static final int LENGTH = 13;

    public static void main(String[] args) {
        Day6 d = new Day6();
        System.out.println(d.part1());
    }

    private int part1() {
        try (Scanner sc = new Scanner(file)) {
            String signal = sc.nextLine();
            char[] sequence = new char[LENGTH];
            for (int i = 0; i < signal.length(); i++) {
                char currentChar = signal.charAt(i);
                // first fill up sequence
                if (i < LENGTH) {
                    sequence[i] = currentChar;
                } else {
                    // check if sequence contains this char
                    boolean contains = false;
                    for (char c : sequence) {
                        if (currentChar == c) {
                            contains = true;
                            break;
                        }
                    }
                    if (contains || containsDuplicates(sequence)) {
                        char[] copy = sequence.clone();
                        System.arraycopy(copy, 1, sequence, 0, LENGTH - 1);
                        sequence[LENGTH - 1] = currentChar;
                    } else {
                        return i + 1;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            return -2;
        }
        return -1;
    }

    private boolean containsDuplicates(char[] sequence) {
        boolean result = false;
        for (char c1 : sequence) {
            int count = 0;
            for (char c2 : sequence) {
                if (c1 == c2) {
                    count++;
                }
            }
            if (count > 1) {
                result = true;
                break;
            }
        }
        return result;
    }
}
