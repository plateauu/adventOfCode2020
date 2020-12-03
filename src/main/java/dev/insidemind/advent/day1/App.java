package dev.insidemind.advent.day1;

import dev.insidemind.advent.LinesReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Day 1 Advent od code 2020
 * https://adventofcode.com/2020/day/1
 */
class App {
    static final int YEAR = 2020;
    static List<Integer> lines;

    static {
        Path INPUT = Paths.get("src/main/java/dev/insidemind/advent/day1/inputs.txt");
        lines = LinesReader.readAllLines(INPUT, Integer::parseInt);
    }

    public static void main(String[] args) {
        int size = lines.size();
        for (int i : lines) {
            for (int secondIdx = lines.indexOf(i); secondIdx < size - 1; secondIdx++) {
                Integer second = lines.get(secondIdx);
                processElements(i, second, secondIdx);
            }
        }
    }

    private static void processElements(int first, int second, int secondIdx) {
        if (first + second == YEAR) {
            System.out.printf("Pair of %d,%d sums to 2020%n", first, second);
            int multiply = first * second;
            System.out.printf("Result of multiplication is: %d%n", multiply);
        } else {
            for (int k = secondIdx; k < lines.size() - 1; k++) {
                int third = lines.get(k);
                if (first + second + third == YEAR) {
                    System.out.printf("Sum of three %d,%d,%d sums to 2020%n", first, second, third);
                    int multiply = first * second * third;
                    System.out.printf("Result of multiplication of three is: %d%n", multiply);
                }
            }
        }

    }
}
