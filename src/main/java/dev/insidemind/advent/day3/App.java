package dev.insidemind.advent.day3;

import dev.insidemind.advent.LinesReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Day 3 Advent od code 2020
 * https://adventofcode.com/2020/day/3
 */
class App {
    static List<String> lines;

    static {
        Path INPUT = Paths.get("src/main/java/dev/insidemind/advent/day3/inputs.txt");
        lines = LinesReader.readAllLines(INPUT, Function.identity());
    }

    public static void main(String[] args) {

    }

    static class TreeCounter {
        private static final int HORIZONTAL_MOVE = 3;
        private static final int CALIBRATOR = 1;

        private final Map<Integer, List<TrajectoryParser.Field>> fields;
        private final int lineSize;

        private int treeCount = 0;
        private int fieldLaneNumber = 0;
        private int lineIndex = 1;

        TreeCounter(Map<Integer, List<TrajectoryParser.Field>> fields) {
            this.fields = fields;
            this.lineSize = this.fields.get(1).size();
        }

        int getTreeCount() {
            return treeCount;
        }

        void count() {
            List<TrajectoryParser.Field> line = nextLine();
            walk(line);
        }

        private void walk(List<TrajectoryParser.Field> line) {
            if (line.isEmpty()) {
                return;
            }
            var idx = lineIndex + HORIZONTAL_MOVE;
            if (idx <= lineSize) {
                lineIndex = idx;
                if (line.get(lineIndex - CALIBRATOR).type == TrajectoryParser.FieldType.TREE) {
                    treeCount++;
                }
            } else {
                lineIndex = idx - lineSize;
            }
            walk(nextLine());
        }

        private List<TrajectoryParser.Field> nextLine() {
            fieldLaneNumber++;
            if (fieldLaneNumber > fields.size()) {
                return Collections.emptyList();
            }
            return this.fields.get(fieldLaneNumber);
        }

    }

    static class TrajectoryParser {
        private final List<String> lines;

        TrajectoryParser(List<String> lines) {
            this.lines = lines;
        }

        Map<Integer, List<Field>> parse() {
            var counter = new AtomicInteger(0);
            return lines.stream()
                        .collect(Collectors.toMap(
                                l -> counter.incrementAndGet(), l -> parse(l, counter.get())
                        ));
        }

        private static List<Field> parse(String line, int lineNumber) {
            var chars = line.toCharArray();
            var result = new ArrayList<Field>();
            for (int i = 0; i < chars.length; i++) {
                result.add(new Field(lineNumber, i + 1, chars[i]));
            }
            return result;
        }

        enum FieldType {
            TREE('#'), OPEN_SQUARE('.');
            char symbol;

            FieldType(char symbol) {
                this.symbol = symbol;
            }

            static FieldType of(char symbol) {
                return Stream.of(FieldType.values())
                             .filter(s -> s.symbol == symbol)
                             .findFirst()
                             .orElseThrow();
            }
        }

        static record Field(int line, int index, FieldType type) {
            Field(int line, int index, char field) {
                this(line, index, FieldType.of(field));
            }
        }
    }
}
