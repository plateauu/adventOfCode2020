package dev.insidemind.advent.day2;

import static dev.insidemind.advent.day2.App.PasswordLine.ValidateType.POSITIONS;
import static dev.insidemind.advent.day2.App.PasswordLine.ValidateType.REPETITIONS;

import dev.insidemind.advent.LinesReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *  Day 2 Advent od code 2020
 *  https://adventofcode.com/2020/day/2
 */
class App {
    static List<PasswordLine> lines;

    static {
        Path INPUT = Paths.get("src/main/java/dev/insidemind/advent/day2/inputs.txt");
        lines = LinesReader.readAllLines(INPUT, PasswordLine::new);
    }

    public static void main(String[] args) {
        validate(REPETITIONS);
        validate(POSITIONS);
    }

    public static void validate(PasswordLine.ValidateType type) {
        long start = System.currentTimeMillis();
        var hits = lines.stream()
                        .filter(line -> line.validate(type))
                        .count();
        long stop = System.currentTimeMillis();
        var time = stop - start;
        System.out.printf("Time spend for %s: %dms%n", type, time);
        System.out.printf("Invalid passwords for %s hits: %d%n", type, hits);
    }
    static class PasswordLine {
        private static final String REGEX = "((\\d{1,2}-\\d{1,2} [a-z]): ([a-zA-Z]+))";
        private static final Pattern PATTERN = Pattern.compile(REGEX);

        Range range;
        char requiredLetter;
        char[] password;

        PasswordLine(String passwordLine) {
            Matcher matcher = getMatcher(passwordLine);
            extractPassword(matcher);
            extractRequirements(matcher);
        }

        boolean validate(ValidateType type) {
            return switch (type) {
                case POSITIONS -> validatePosition();
                case REPETITIONS -> validateRepetitions();
            };
        }

        private boolean validatePosition() {
            if (password[range.min - 1] == requiredLetter &&
                    password[range.max - 1] != requiredLetter
            ) {
                return true;
            }
            return password[range.min - 1] != requiredLetter &&
                    password[range.max - 1] == requiredLetter;
        }

        enum ValidateType {
            REPETITIONS, POSITIONS
        }

        private boolean validateRepetitions() {
            var hits = 0;
            for (var i : password) {
                if (i == requiredLetter) {
                    hits++;
                }
            }
            return hits >= range.min && hits <= range.max();
        }

        record Range(int min, int max) {
        }

        private void extractPassword(Matcher matcher) {
            password = matcher.group(3).toCharArray();
        }

        private void extractRequirements(Matcher matcher) {
            var requirements = matcher.group(2);
            String[] sub = requirements.split("\s");
            requiredLetter = sub[1].toCharArray()[0];
            var o = sub[0].split("-");
            range = new Range(
                    Integer.parseInt(o[0]), Integer.parseInt(o[1])
            );
        }

        private Matcher getMatcher(String passwordLine) {
            Matcher matcher = PATTERN.matcher(passwordLine);
            if (!matcher.matches()) {
                System.out.printf("Line [%s] does not match to matcher%n", passwordLine);
                throw new IllegalArgumentException("Wrong input format for line: " + passwordLine);
            }
            return matcher;
        }
    }
}
