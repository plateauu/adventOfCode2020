package dev.insidemind.advent.day7;

import dev.insidemind.advent.LinesReader;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Day 7 Advent od code 2020
 * https://adventofcode.com/2020/day/7
 */
class App {
    static List<String> lines;

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        partOne();
        long stop = System.currentTimeMillis();
        var time = stop - start;
        System.out.printf("Time spend: %dms%n", time);

    }

    private static List<BagRuleParser.BagRule> partOne() {
        var rules = new BagRuleParser(lines).parse();
        var map = rules.stream().collect(Collectors.toUnmodifiableMap(BagRuleParser.BagRule::name, Function.identity()));

        return rules;
    }

    static class BagRuleParser {
        public static final Pattern PATTERN = Pattern.compile(
                "^(\\w+\\W+\\w+){1} bags contain ((\\d+\\W+\\w+\\W+\\w+) bags?[,|\\.] (\\d+\\W+\\w+\\W+\\w+) bags?[,|\\.]" +
                        " (\\d+\\W+\\w+\\W+\\w+) bags?[,|\\.] (\\d+\\W+\\w+\\W+\\w+) bags?[,|\\.]|no other bags.)$");

        private final List<String> lines;

        BagRuleParser(List<String> lines) {
            this.lines = lines;
        }

        List<BagRule> parse() {
            return lines.stream()
                    .map(this::parse)
                    .collect(Collectors.toUnmodifiableList());
        }

        BagRule parse(String line) {
            Matcher matcher = PATTERN.matcher(line);
            int groupCount = matcher.groupCount();
            var internals = new InternalElement[groupCount];
            String ruleName = null;
            boolean end = false;

            if (matcher.matches()) {
                for (int i = 0; i < groupCount; i++) {
                    var el = matcher.group(i + 1);
                    if (el == null)
                        continue;

                    if (i == 0) {
                        ruleName = el;
                        continue;
                    }

                    if (el.matches("^\\d.*$")) {
                        var split = el.split(" ", 2);
                        internals[i] = new InternalElement(Integer.parseInt(split[0]), split[1]);
                    } else if (el.contains("no other bags")) {
                        end = true;
                    }
                }
            }

            InternalElement[] boxedInternalElements = Arrays.stream(internals)
                    .filter(Objects::nonNull)
                    .toArray(InternalElement[]::new);

            return new BagRule(ruleName, boxedInternalElements, end);
        }

        record InternalElement(int count, String ruleName) {

        }

        public record BagRule(String name, InternalElement[] elements, boolean end) {
        }

    }

    static {
        Path INPUT = Paths.get("src/main/java/dev/insidemind/advent/day7/input.txt");
        lines = LinesReader.readAllLines(INPUT, Function.identity());
    }

}
