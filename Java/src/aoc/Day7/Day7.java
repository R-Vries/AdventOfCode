package aoc.Day7;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Day7 {
    public static final int MAX = 100000;
    public static final int TOTAL = 70000000;
    public static final int NEEDED = 30000000;
    public static final File file = new File("src/aoc/files/filesystem.txt");
    private Directory current;

    public static void main(String[] args) {
        Day7 d = new Day7();
        System.out.println(d.part1());
        System.out.println(d.part2());
    }

    private int part2() {
        Directory root = getStructure();
        List<Integer> allSizes = root.getALlSizes();
        int result = Collections.max(allSizes);
        int free = TOTAL - root.getSize();
        for (int size : allSizes) {
            if (free + size > NEEDED && size < result) {
                result = size;
            }
        }
        return result;
    }

    private int part1() {
        int result = 0;
        Directory root = getStructure();
        List<Integer> allSizes = root.getALlSizes();
        for (int size : allSizes) {
            if (size <= MAX) {
                result += size;
            }
        }
        return result;
    }

    private Directory getStructure() {
        Directory root = null;
        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String[] line = sc.nextLine().split(" ");
                // create root
                if (line[0].equals("$") && line[1].equals("cd") && line[2].equals("/")) {
                    root = new Directory("/", null);
                    current = root;
                } else if (line[1].equals("cd")){
                    if (line[2].equals("..")) {
                        if (current.getParent() != null) {
                            current = current.getParent();
                        }
                    } else {
                        current = current.find(line[2]);
                    }
                } else if (line[0].equals("dir")) {
                    current.addDirectory(new Directory(line[1], current));
                } else if (!line[1].equals("ls")){
                    // it is a file
                    current.addFile(new MyFile(Integer.parseInt(line[0]), line[1]));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        }
        current = root;
        return root;
    }
}
