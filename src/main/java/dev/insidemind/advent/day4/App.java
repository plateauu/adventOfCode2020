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

    static class CredentialValidator {
        private final List<String> credentials;

        private static final Pattern PATTERN = Pattern.compile(
                "^(?=.*hcl.*)(?=.*ecl:.*)(?=.*pid:)(?=.*eyr:.*)(?=.*byr:)(?=.*iyr:.*)(?=.*hgt:)(?=.*cid:)?.*$"
        );

        CredentialValidator(List<String> credentials) {
            this.credentials = credentials;
        }

        long validate() {
            var result = countValid();
            System.out.printf("Valid passports: %d%n", result);
            return result;
        }

        private long countValid() {
            long count = 0L;
            for (String s : credentials) {
                if (PATTERN.matcher(s).matches()) {
                    count++;
                }
            }
            System.out.printf("Valid: %n");
            return count;
        }
    }

    static {
        Path INPUT = Paths.get("src/main/java/dev/insidemind/advent/day4/input.txt");
        lines = LinesReader.readAllLines(INPUT, Function.identity());
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        var merged = new CredentialMerger(lines).merge();
        new CredentialValidator(merged).validate();
        long stop = System.currentTimeMillis();
        var time = stop - start;
        System.out.printf("Time spend: %dms%n", time);
    }

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
}
