package dev.insidemind.advent.day4;

import dev.insidemind.advent.LinesReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Pattern;

/**
 * Day 4 Advent od code 2020
 * https://adventofcode.com/2020/day/4
 */
class App {
    static List<String> lines;

    static class CredentialMerger {
        private final List<String> lines;
        private final List<String> toOneLine = new ArrayList<>();
        private final List<String> result = new LinkedList<>();

        CredentialMerger(List<String> lines) {
            this.lines = lines;
        }

        List<String> merge() {
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
            var joined = String.join(" ", toOneLine);
            result.add(joined);
            toOneLine.clear();
        }
    }

    static {
        Path INPUT = Paths.get("src/main/java/dev/insidemind/advent/day4/input.txt");
        lines = LinesReader.readAllLines(INPUT, Function.identity());
    }

    public static void main(String[] args) {
        new CredentialMerger(lines);
    }
}
