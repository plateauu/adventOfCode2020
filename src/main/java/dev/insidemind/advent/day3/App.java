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
        Path INPUT = Paths.get("src/main/java/dev/insidemind/advent/day3/input.txt");
        lines = LinesReader.readAllLines(INPUT, Function.identity());
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        Map<Integer, List<TrajectoryParser.Field>> trajectory = new TrajectoryParser(lines).parse();

        var results = new ArrayList<Integer>();
        results.add(count(trajectory, 1, 1));
        results.add(count(trajectory, 3, 1));
        results.add(count(trajectory, 5, 1));
        results.add(count(trajectory, 7, 1));
        results.add(count(trajectory, 1, 2));

        var result = results.stream()
                            .reduce(1, (a, b) -> a * b);

        System.out.printf("Multipled value of encounter trees is: %d%n", result);

        long stop = System.currentTimeMillis();
        var time = stop - start;
        System.out.printf("Time spend for whole program: %dms%n", time);
    }

    private static int count(Map<Integer, List<TrajectoryParser.Field>> parse, int rightMove, int downMove) {
        long start = System.currentTimeMillis();
        var treeCounter = new TreeCounter(parse, rightMove, downMove);
        treeCounter.count();
        System.out.printf("Tree counted for right step %d, down step %d: %d%n", rightMove, downMove,
                treeCounter.getTreeCount()
        );

        long stop = System.currentTimeMillis();
        var time = stop - start;
        System.out.printf("Time spend: %dms%n", time);
        return treeCounter.getTreeCount();
    }

    static class TreeCounter {
        private static final int DEFAULT_RIGHT_MOVE = 3;
        private static final int DEFAULT_DOWN_MOVE = 1;
        private static final int CALIBRATOR = 1;

        private final Map<Integer, List<TrajectoryParser.Field>> trajectory;
        private final int lineSize;

        private int treeCount = 0;
        private int fieldLaneNumber = 1;
        private int lineIndex = 1;

        private final int rightMove;
        private final int downMove;

        TreeCounter(Map<Integer, List<TrajectoryParser.Field>> trajectory) {
            this.trajectory = trajectory;
            this.lineSize = this.trajectory.get(fieldLaneNumber).size();
            this.rightMove = DEFAULT_RIGHT_MOVE;
            this.downMove = DEFAULT_DOWN_MOVE;
        }

        TreeCounter(Map<Integer, List<TrajectoryParser.Field>> trajectory, int rightMove, int downMove) {
            this.trajectory = trajectory;
            this.lineSize = this.trajectory.get(fieldLaneNumber).size();
            this.rightMove = rightMove;
            this.downMove = downMove;
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

            var idx = lineIndex + rightMove;
            lineIndex = idx <= lineSize
                    ? idx
                    : idx - lineSize;

            if (line.get(lineIndex - CALIBRATOR).type == TrajectoryParser.FieldType.TREE) {
                treeCount++;
            }

            walk(nextLine());
        }

        private List<TrajectoryParser.Field> nextLine() {
            fieldLaneNumber += downMove;
            if (fieldLaneNumber > trajectory.size()) {
                return Collections.emptyList();
            }
            return this.trajectory.get(fieldLaneNumber);
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
