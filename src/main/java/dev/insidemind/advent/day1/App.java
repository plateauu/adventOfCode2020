package dev.insidemind.advent.day1;

import dev.insidemind.advent.LinesReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Before you leave, the Elves in accounting just need you to fix your expense report (your puzzle input);
 * apparently, something isn't quite adding up.
 * Specifically, they need you to find the two entries that sum to 2020 and then multiply those two numbers together.
 * For example, suppose your expense report contained the following:
 * 1721
 * 979
 * 366
 * 299
 * 675
 * 1456
 * In this list, the two entries that sum to 2020 are 1721 and 299. Multiplying them together produces 1721 * 299 =
 * 514579, so the correct answer is 514579.
 * <p>
 * Of course, your expense report is much larger. Find the two entries that sum to 2020; what do you get if you
 * multiply them together?
 */

class App {
    static final int YEAR = 2020;
    static List<Integer> lines;
    static final Path INPUT = Paths.get("src/main/java/dev/insidemind/advent/day1/inputs.txt");

    public static void main(String[] args) {
        lines = LinesReader.readAllLines(INPUT, Integer::parseInt);
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
