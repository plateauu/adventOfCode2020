package dev.insidemind.advent.day8;

import dev.insidemind.advent.LinesReader;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
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

    enum OperationType {
        JMP, ACC, NOP;

        static OperationType type(String from) {
            return Arrays.stream(values()).filter(v -> v.name().equals(from.toUpperCase())).findAny().orElseThrow();
        }
    }

    static class Operation {

         final OperationType type;
         final char sign;
         final int count;

        Operation(String op, char sign, int count) {
            this.type = OperationType.type(op);
            this.sign = sign;
            this.count = count;
        }

    }


}
