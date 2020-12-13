package dev.insidemind.advent.day6;

import dev.insidemind.advent.LinesReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
        var groups = new CustomGroupMerger(lines).merge();
        partOne(groups);
        partTwo(groups);
    }

    private static void partOne(List<CustomGroup> groups) {
        execute(() -> sumYesGroups(groups), "Sum of all yes group is: %d%n");
    }

    private static void partTwo(List<CustomGroup> groups) {
        execute(() -> sumEveryoneAnsweredYesGroups(groups), "Sum of all answer everyone say yes is: %d%n");
    }

    private static void execute(Supplier<Integer> processTask, String message) {
        long start = System.currentTimeMillis();
        var result = processTask.get();
        System.out.printf(message, result);
        long stop = System.currentTimeMillis();
        var time = stop - start;
        System.out.printf("Time spend: %dms%n", time);
    }

    record GroupToYesAnswers(CustomGroup g, Set<Character> yesAnswers) {
        public int count() {
            return yesAnswers().size();
        }
    }

    private static int sumEveryoneAnsweredYesGroups(List<CustomGroup> groups) {
        return groups.stream()
                     .map(CustomGroup::countEveryoneAnswered)
                     .mapToInt(value -> value)
                     .sum();
    }

    private static int sumYesGroups(List<CustomGroup> groups) {
        return groups.stream()
                     .map(group -> {
                         Set<Character> result = new HashSet<>();
                         var chars = group.joinAnswers().toCharArray();
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
        private final List<Person> persons = new ArrayList<>();
        private final List<CustomGroup> result = new ArrayList<>();

        public CustomGroupMerger(List<String> lines) {
            this.lines = lines;
        }

        public List<CustomGroup> merge() {
            Pattern p = Pattern.compile("^\\s*$");

            for (var line : lines) {
                if (!p.matcher(line).matches()) {
                    persons.add(new Person(line));
                } else {
                    createGroup();
                }
            }

            createGroup();
            return result;
        }

        private void createGroup() {
            result.add(new CustomGroup(persons.stream().collect(Collectors.toUnmodifiableList())));
            persons.clear();
        }
    }

    static class Person {
        final String answers;

        Person(String answers) {
            this.answers = answers;
        }

        String answers() {
            return answers;
        }
    }

    record CustomGroup(List<Person> persons) {
        String joinAnswers() {
            return persons.stream().map(Person::answers).collect(Collectors.joining());
        }

        int countEveryoneAnswered() {
            Map<Person, String> p = persons.stream()
                                           .collect(Collectors.toMap(Function.identity(), person -> person.answers));

            var result = new HashMap<Character, List<Person>>();
            p.forEach((person, answers) -> {
                for (var answer : answers.toCharArray()) {
                    result.compute(answer, (c, r) -> {
                        if (r == null)
                            r = new ArrayList<>();
                        r.add(person);
                        return r;
                    });
                }
            });

            return (int) result.values()
                         .stream()
                         .filter(l -> l.size() == persons.size())
                         .count();
        }

    }
}
