package dev.insidemind.advent.day7;

import dev.insidemind.advent.LinesReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Day 7 Advent od code 2020
 * https://adventofcode.com/2020/day/7
 */
class App {
    static List<String> lines;

    public static void main(String[] args) {

    }

    static class BagParser {
        public static final Pattern PATTERN = Pattern.compile(
            "^(\\w+\\W+\\w+){1} bags contain ((\\d+\\W+\\w+\\W+\\w+) bags?[,|\\.] (\\d+\\W+\\w+\\W+\\w+) bags?[,|\\.]" +
                " (\\d+\\W+\\w+\\W+\\w+) bags?[,|\\.] (\\d+\\W+\\w+\\W+\\w+) bags?[,|\\.]|no other bags.)$");

        private final List<String> lines;

        public BagParser(List<String> lines) {
            this.lines = lines;
        }

        String[] parse(String line) {
            Matcher matcher = PATTERN.matcher(line);
            int groupCount = matcher.groupCount();
            var r = new String[groupCount];
            if (matcher.matches()) {
                for (int i = 0; i < groupCount; i++) {
                    r[i] = matcher.group(i + 1);
                }
            }
            return r;
        }

    }

    static {
        Path INPUT = Paths.get("src/main/java/dev/insidemind/advent/day7/input.txt");
        lines = LinesReader.readAllLines(INPUT, Function.identity());
    }

}
