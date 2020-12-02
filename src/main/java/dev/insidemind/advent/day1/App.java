package dev.insidemind.advent.day1;

import com.sun.jdi.IntegerType;
import java.io.IOException;
import java.lang.reflect.Parameter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public static void main(String[] args) {
        ArrayList<Pair<Integer, Integer>> results = new ArrayList<>();

        Path path = Paths.get("src/main/java/dev/insidemind/advent/day1/inputs.txt");
        try {
            List<Integer> lines = Files.readAllLines(path)
                                       .stream()
                                       .map(Integer::parseInt)
                                       .collect(Collectors.toUnmodifiableList());
            int size = lines.size();
            for (int i : lines) {
                int idx = lines.indexOf(i);
                for (int k = idx; k < size - 1; k++) {
                    if (i + k == 2020) {
                        results.add(new Pair<>(i, k));
                        System.out.printf("Pair of %d,%d sums to 2020", i,k);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

record Pair<T, K>(T first, K second){
}
