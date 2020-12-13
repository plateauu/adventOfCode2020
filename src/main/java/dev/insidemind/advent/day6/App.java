package dev.insidemind.advent.day6;

import dev.insidemind.advent.LinesReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Pattern;

/**
 * Day 6 Advent od code 2020
 * https://adventofcode.com/2020/day/6
 */
class App {
    static List<String> lines;

    static {
        Path INPUT = Paths.get("src/main/java/dev/insidemind/advent/day6/input.txt");
        lines = LinesReader.readAllLines(INPUT, Function.identity());
    }

    public static void main(String[] args) {
        partOne();
    }

    private static void partOne() {
        var groups = new CustomGroupMerger(lines).merge();
        long start = System.currentTimeMillis();
        var result = sumYesGroups(groups);
        System.out.printf("Sum of all yes group is: %d%n", result);
        long stop = System.currentTimeMillis();
        var time = stop - start;
        System.out.printf("Time spend: %dms%n", time);
    }

    record GroupToYesAnswers(CustomGroup g, Set<Character> yesAnswers) {
        public int count() {
            return yesAnswers().size();
        }
    }

    private static int sumYesGroups(List<CustomGroup> groups) {
        return groups.stream()
                     .map(group -> {
                         Set<Character> result = new HashSet<>();
                         var chars = group.oneLine().toCharArray();
                         for (char c : chars) {
                             result.add(c);
                         }
                         return new GroupToYesAnswers(group, result);
                     })
                     .map(GroupToYesAnswers::count)
                     .mapToInt(value -> value)
                     .sum();
    }

    static class CustomGroupMerger {
        private final List<String> lines;
        private final List<String> toOneLine = new ArrayList<>();
        private final List<CustomGroup> result = new ArrayList<>();

        public CustomGroupMerger(List<String> lines) {
            this.lines = lines;
        }

        public List<CustomGroup> merge() {
            Pattern p = Pattern.compile("^\\s*$");

            for (var line : lines) {
                if (!p.matcher(line).matches()) {
                    toOneLine.add(line);
                } else {
                    join();
                }
            }

            join();
            return result;
        }

        private void join() {
            var joined = String.join("", toOneLine);
            result.add(new CustomGroup(joined));
            toOneLine.clear();
        }
    }

    record CustomGroup(String oneLine) {
    }
}
