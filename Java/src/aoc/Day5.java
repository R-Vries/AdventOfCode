package aoc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day5 {
    public static final File file = new File("src/aoc/files/crates.txt");

    public static void main(String[] args) {
        Day5 d = new Day5();
        System.out.println(d.part1());
        System.out.println(d.part2());
    }

    private String part2() {
        StringBuilder result = new StringBuilder();
        List<Stack<Character>> order = getOrder();

        try (Scanner sc = new Scanner(file)) {
            // skip the setup part
            while (sc.hasNextLine()) {
                if (sc.nextLine().length() == 0) {
                    break;
                }
            }
            // execute all instructions
            while (sc.hasNextLine()) {
                // start reading the instructions
                String line = sc.nextLine();
                order = instruction9001(line, order);
            }
            // get the final order
            for (Stack<Character> stack : order) {
                result.append(stack.pop());
            }
        } catch (FileNotFoundException e) {
            result = new StringBuilder("file not found");
        }
        return result.toString();
    }

    private List<Stack<Character>> instruction9001(String line, List<Stack<Character>> order) {
        String[] split = line.split(" ");
        int amount = Integer.parseInt(split[1]);
        int from = Integer.parseInt(split[3]) - 1;
        int to = Integer.parseInt(split[5]) - 1;



//        Stack<Character> chars = new Stack<>();
//        for (int i = 0; i < amount; i++) {
//            chars.add(order.get(from).pop());
//        }
//        for (int i = 0; i < chars.size(); i++) {
//            order.get(to).add(chars.pop());
//        }
//        return order;
        return order;
    }

    private String part1() {
        StringBuilder result = new StringBuilder();
        List<Stack<Character>> order = getOrder();

        try (Scanner sc = new Scanner(file)) {
            // skip the setup part
            while (sc.hasNextLine()) {
                if (sc.nextLine().length() == 0) {
                    break;
                }
            }
            // execute all instructions
            while (sc.hasNextLine()) {
                // start reading the instructions
                String line = sc.nextLine();
                order = instruction(line, order);
            }
            // get the final order
            for (Stack<Character> stack : order) {
                result.append(stack.pop());
            }

        } catch (FileNotFoundException e) {
            result = new StringBuilder("file not found");
        }
        return result.toString();
    }

    /**
     * Executes one instruction
     * @param line the instruction
     * @param order the current situation of the crates
     * @return the new situation of the crates
     */
    public List<Stack<Character>> instruction(String line, List<Stack<Character>> order) {
        String[] split = line.split(" ");
        int amount = Integer.parseInt(split[1]);
        int from = Integer.parseInt(split[3]) - 1;
        int to = Integer.parseInt(split[5]) - 1;

        Stack<Character> chars = new Stack<>();

        for (int i = 0; i < amount; i++) {
            chars.add(order.get(from).pop());
//            order.get(to).add(order.get(from).pop());
        }
        for (int i = 0; i < amount; i++) {
            order.get(to).add(chars.pop());
        }
        return order;
    }

    /**
     * Get a list that contains the items on each stack as lists
     * @return the list
     */
    public List<Stack<Character>> getOrder() {
        List<Stack<Character>> order = new ArrayList<>();

        List<List<Character>> lists = new ArrayList<>();

        List<String> setup = getSetup();
        List<Integer> indices = new ArrayList<>();
        String last = setup.get(setup.size() - 1);
        // add all the indices to an array, for checking later
        int numStacks = Integer.parseInt(String.valueOf(last.charAt(last.length() - 1)));
        for (int i = 1; i <= numStacks; i++) {
            indices.add(last.indexOf(Integer.toString(i)));
        }
        // create list for each stack, correct amount
        for (int i = 0; i < indices.size(); i++) {
            lists.add(new ArrayList<>());
        }
        setup.remove(setup.size() - 1);
        // add boxes to the correct list;
        // find index in string in indices, check if it is not a space
        for (String line : setup) {
            for (int i = 0; i < indices.size(); i++) {
                int indexOfStack = indices.get(i);
                if (indexOfStack < line.length() && line.charAt(indexOfStack) != ' ') {
                    lists.get(i).add(line.charAt(indexOfStack));
                }
            }
        }
        // create stack
        for (List<Character> list : lists) {
            Stack<Character> stack = new Stack<>();
            for (int j = list.size() - 1; j >= 0; j--) {
                stack.add(list.get(j));
            }
            order.add(stack);
        }
        return order;
    }

    /**
     * Get an arrayList of all lines of the setup
     * @return the list
     */
    public List<String> getSetup() {
        List<String> lines = new ArrayList<>();
        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (line.length() != 0) {
                    lines.add(line);
                } else {
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            return null;
        }
        return lines;
    }
}
