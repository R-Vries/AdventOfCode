package aoc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Variance {
    public static final File file = new File("src/aoc/variance");

    public static void main(String[] args) {
        double result = 0;
        int lines = 0;
        try (Scanner sc = new Scanner(file)) {
            System.out.print("Average: ");
            Scanner sc2 = new Scanner(System.in);
            double average = sc2.nextDouble();
            while (sc.hasNextLine()) {
                double val = sc.nextDouble();
                result += ((val - average) * (val - average));
                lines++;
            }
            result /= (lines - 1);
            System.out.println(result);
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        }
    }
}
