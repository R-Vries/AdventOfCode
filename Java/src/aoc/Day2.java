package aoc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Day2 {
    // constants for points gained per action
    static final int LOSS = 0;
    static final int DRAW = 3;
    static final int WIN = 6;

    static final int ROCK = 1;
    static final int PAPER = 2;
    static final int SCISSORS = 3;
    static File file = new File("src/aoc/files/strategy.txt");

    public static void main(String[] args) {
        Day2 d = new Day2();
        System.out.println(d.part1());
        System.out.println(d.part2());
    }


    private int part2() {
        /*
        check what opponent has
        check result
        calculate what to play
        calculate points
         */
        try (Scanner sc = new Scanner(file)) {
            int points = 0;
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] chars = line.split(" ");
                String opponent = chars[0];
                String result = chars[1];

                // loss
                if (result.equals("X")) {
                    points += LOSS;
                    if (opponent.equals("A")) {
                        points += SCISSORS;
                    } else if (opponent.equals("B")) {
                        points += ROCK;
                    } else {
                        points += PAPER;
                    }
                } else if (result.equals("Y")) {
                    //draw
                    points += DRAW;
                    if (opponent.equals("A")) {
                        points += ROCK;
                    } else if (opponent.equals("B")) {
                        points += PAPER;
                    } else {
                        points += SCISSORS;
                    }
                } else {
                    // win
                    points += WIN;
                    if (opponent.equals("A")) {
                        points += PAPER;
                    } else if (opponent.equals("B")) {
                        points += SCISSORS;
                    } else {
                        points += ROCK;
                    }
                }
            }
            return points;
        } catch (FileNotFoundException e) {
            return -1;
        }
    }

    private int part1() {
        try (Scanner sc = new Scanner(file)) {
            int result = 0;
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] chars = line.split(" ");
                String opponent = chars[0];
                String me = chars[1];
                result += result(opponent, me);
            }
            return result;
        } catch (FileNotFoundException e) {
            return -1;
        }
    }

    private int result(String opponent, String me) {
        // it is a tie
        int points = points(me);
        if ((opponent.equals("A") && me.equals("X")) || (opponent.equals("B") && me.equals("Y")) || (opponent.equals("C") && me.equals("Z"))) {
            return points + DRAW;
            // it is a loss
        } else if (opponent.equals("A") && me.equals("Z") || opponent.equals("B") && me.equals("X") || opponent.equals("C") && me.equals("Y")) {
            return points + LOSS;
            // it is a win
        } else {
            return points + WIN;
        }
    }

    private int points(String me) {
        if (me.equals("X")) {
            return ROCK;
        } else if (me.equals("Y")) {
            return PAPER;
        } else return SCISSORS;
    }

}
