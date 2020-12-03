package dev.insidemind.advent.day2;

import dev.insidemind.advent.LinesReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Your flight departs in a few days from the coastal airport; the easiest way down to the coast from here is via
 * toboggan.
 * <p>
 * The shopkeeper at the North Pole Toboggan Rental Shop is having a bad day. "Something's wrong with our computers;
 * we can't log in!" You ask if you can take a look.
 * <p>
 * Their password database seems to be a little corrupted: some of the passwords wouldn't have been allowed by the
 * Official Toboggan Corporate Policy that was in effect when they were chosen.
 * <p>
 * To try to debug the problem, they have created a list (your puzzle input) of passwords (according to the corrupted
 * database) and the corporate policy when that password was set.
 * <p>
 * For example, suppose you have the following list:
 * <p>
 * 1-3 a: abcde
 * 1-3 b: cdefg
 * 2-9 c: ccccccccc
 * Each line gives the password policy and then the password. The password policy indicates the lowest and highest
 * number of times a given letter must appear for the password to be valid. For example, 1-3 a means that the
 * password must contain a at least 1 time and at most 3 times.
 * <p>
 * In the above example, 2 passwords are valid. The middle password, cdefg, is not; it contains no instances of b,
 * but needs at least 1. The first and third passwords are valid: they contain one a or nine c, both within the
 * limits of their respective policies.
 * <p>
 * How many passwords are valid according to their policies?
 */
class App {
    static List<Integer> lines;

    static {
        Path INPUT = Paths.get("src/main/java/dev/insidemind/advent/day2/inputs.txt");
        lines = LinesReader.readAllLines(INPUT, Integer::parseInt);
    }

    public static void main(String[] args) {
//        new PasswordLine()
    }

    static class PasswordLine {
        private static final String REGEX = "((\\d-\\d [a-z]): ([a-zA-Z]+))";
        private static final Pattern PATTERN = Pattern.compile(REGEX);

        OccurrenceRange occurrences;
        char requiredLetter;
        char[] password;

        PasswordLine(String passwordLine) {
            Matcher matcher = getMatcher(passwordLine);
            extractPassword(matcher);
            extractRequirements(matcher);
        }

        boolean validate() {
            var hits = 0;
            for (var i : password) {
                if (i == requiredLetter) {
                    hits++;
                }
            }
            return occurrences.validate(hits);
        }

        record OccurrenceRange(int min, int max) {
            boolean validate(int counts) {
                return counts >= min && counts <= max;
            }
        }

        private void extractPassword(Matcher matcher) {
            password = matcher.group(3).toCharArray();
        }

        private void extractRequirements(Matcher matcher) {
            var requirements = matcher.group(2);
            String[] sub = requirements.split("\s");
            requiredLetter = sub[1].toCharArray()[0];
            occurrences = new OccurrenceRange(
                    Integer.parseInt(sub[0].subSequence(0, 1).toString()),
                    Integer.parseInt(sub[0].subSequence(2, 3).toString())
            );
        }

        private Matcher getMatcher(String passwordLine) {
            Matcher matcher = PATTERN.matcher(passwordLine);
            if (!matcher.matches()) {
                System.out.printf("Line [%s] does not match to matcher", passwordLine);
                throw new IllegalArgumentException("Wrong input format for line: " + passwordLine);
            }
            return matcher;
        }
    }
}
