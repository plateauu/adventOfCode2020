package dev.insidemind.advent.day7;

import dev.insidemind.advent.LinesReader;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toUnmodifiableMap;

/**
 * Day 7 Advent od code 2020
 * https://adventofcode.com/2020/day/7
 */
class App {
    private static final String SHINY_GOLD = "shiny gold";

    static List<String> lines;
    static Map<String, BagRuleParser.BagRule> map;

    public static void main(String[] args) {
        partOne();
        partTwo();
    }

    private static void partTwo() {
        long start = System.currentTimeMillis();
        var shinyBagsCount = countBagsInsideShinyGold(SHINY_GOLD);
        System.out.printf("shiny gold bag must contain: %d bags.%n", shinyBagsCount);
        long stop = System.currentTimeMillis();
        var time = stop - start;
        System.out.printf("Time spend: %dms%n", time);
    }

    static long countBagsInsideShinyGold(String name) {
        return map.get(name).countBags();
    }


    private static void partOne() {
        long start = System.currentTimeMillis();
        var shinyBagsCount = countBagsWithShinyGold();
        System.out.printf("shiny bag can be at: %d bags.%n", shinyBagsCount);
        long stop = System.currentTimeMillis();
        var time = stop - start;
        System.out.printf("Time spend: %dms%n", time);
    }

    private static int countBagsWithShinyGold() {
        new BagRuleParser(lines).parse();
        int result = 0;
        for (var rule : map.entrySet()) {
            if (rule.getValue().find(SHINY_GOLD)) {
                result++;
                if (rule.getValue().name.equals(SHINY_GOLD)) {
                    System.out.printf("Shine gold can be at: %s%n", rule.getValue().name);
                }
            }
        }
        return result;
    }

    static class BagRuleParser {
        public static final Pattern PATTERN = Pattern.compile(
                        "^(\\w+\\W\\w+) bags contain ((\\d\\W\\w+\\W\\w+) bags?\\.|" +
                        "(\\d\\W\\w+\\W\\w+) bags?, (\\d\\W\\w+\\W\\w+) bags?\\.|" +
                        "(\\d\\W\\w+\\W\\w+) bags?, (\\d\\W\\w+\\W\\w+) bags?, (\\d\\W\\w+\\W\\w+) bags?\\.|" +
                        "(\\d\\W\\w+\\W\\w+) bags?, (\\d\\W\\w+\\W\\w+) bags?, (\\d\\W\\w+\\W\\w+) bags?, (\\d\\W\\w+\\W\\w+) bags?\\.|" +
                        "no other bags.)$");

        private final List<String> lines;
        public static final Set<String> TO_SKIP_INDICATORS = Set.of(",", ".");

        BagRuleParser(List<String> lines) {
            this.lines = lines;
        }

        List<BagRule> parse() {
            var rules = lines.stream()
                    .map(this::parse)
                    .collect(Collectors.toUnmodifiableList());
            map = rules.stream().collect(toUnmodifiableMap(BagRuleParser.BagRule::name, Function.identity()));
            return rules;
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

                    if (shouldParseGroup(el) && el.matches("^\\d.*$")) {
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

        private boolean shouldParseGroup(String el) {
            return TO_SKIP_INDICATORS.stream()
                    .map(i -> !el.contains(i))
                    .reduce((a, b) -> a && b)
                    .orElse(false);
        }

        record InternalElement(int count, String ruleName) {
            Boolean find(String name) {
                var thisElement = ruleName.equals(name);
                if (thisElement) return true;
                return map.get(ruleName).find(name);
            }

            long countBags() {
                var bagRule = map.get(ruleName());
                if (bagRule.end()) {
                    return count();
                }
                var sum = count() + count() * Arrays.stream(bagRule.elements)
                        .mapToLong(InternalElement::countBags)
                        .sum();
                System.out.printf("Inside %s rule. Sum: %d%n", ruleName, sum);
                return sum;
            }
        }

        record BagRule(String name, InternalElement[] elements, boolean end) {
            Boolean find(String bagName) {
                for (InternalElement i : elements()) {
                    if (i.find(bagName)) {
                        return true;
                    }
                }
                return false;
            }

            public long countBags() {
                var sum = Arrays.stream(elements).mapToLong(InternalElement::countBags).sum();
                System.out.printf("Inside %s rule. Sum: %d %n", name, sum);
                return sum;
            }
        }
    }

    static {
        Path INPUT = Paths.get("src/main/java/dev/insidemind/advent/day7/input.txt");
        lines = LinesReader.readAllLines(INPUT, Function.identity());
    }

}
