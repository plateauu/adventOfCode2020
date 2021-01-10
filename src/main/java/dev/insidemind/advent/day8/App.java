package dev.insidemind.advent.day8;

import dev.insidemind.advent.LinesReader;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Day 8 Advent od code 2020
 * https://adventofcode.com/2020/day/8
 */
class App {

    static List<String> lines;

    static {
        Path INPUT = Paths.get("src/main/java/dev/insidemind/advent/day7/input.txt");
        lines = LinesReader.readAllLines(INPUT, Function.identity());
    }

    public static void main(String[] args) {

    }

    static class OperationParser {
        private final List<String> lines;

        public OperationParser(List<String> lines) {
            this.lines = lines;
        }

        List<Operation> parse() {
            return lines.stream().map(this::parse).collect(Collectors.toList());
        }

        private Operation parse(String s) {
            var operation = s.split("\s");
            var op = operation[0];
            var sign = operation[1].charAt(0);
            var count = Integer.parseInt(operation[1].substring(1));
            return new Operation(op, sign, count);
        }

    }

    record Operation(String op, char sign, int count) {
    }


}
