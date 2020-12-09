package dev.insidemind.advent.day5;

import dev.insidemind.advent.LinesReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Function;

/**
 * Day 5 Advent od code 2020
 * https://adventofcode.com/2020/day/5
 */
class App {
    static List<String> lines;

    static {
        Path INPUT = Paths.get("src/main/java/dev/insidemind/advent/day5/input.txt");
        lines = LinesReader.readAllLines(INPUT, Function.identity());
    }

    private static final int from = 127;

    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        var max = lines.stream()
                       .map(SeatFinder::new)
                       .map(SeatFinder::find)
                       .map(Pair::calculateSeatId)
                       .max(Integer::compareTo);

        System.out.printf("Highest boarding pass is: %d%n", max.orElseThrow());
        long stop = System.currentTimeMillis();
        var time = stop - start;
        System.out.printf("Time spend: %dms%n", time);
    }

    /**
     * Start by considering the whole range, rows 0 through 127.
     * F means to take the lower half, keeping rows 0 through 63.
     * B means to take the upper half, keeping rows 32 through 63.
     * F means to take the lower half, keeping rows 32 through 47.
     * B means to take the upper half, keeping rows 40 through 47.
     * B keeps rows 44 through 47.
     * F keeps rows 44 through 45.
     * The final F keeps the lower of the two, row 44.
     */
    static class SeatFinder {
        final String row;
        final char _F = 'F';
        final char _B = 'B';
        final char _L = 'L';
        final char _R = 'R';

        private final char[] chars;

        SeatFinder(String row) {
            this.row = row;
            this.chars = row.toCharArray();
        }

        public Pair find() {
            var startRow = 0;
            var endRow = from;
            var startCol = 0;
            var endCol = 7;
            int row = 128;
            int column = 8;
            for (int i = 0; i < chars.length; i++) {
                if (List.of(_F, _B).contains(chars[i])) {
                    row = divide(row);
                    if (chars[i] == _F) {
                        endRow = endRow - row;
                    } else {
                        startRow = startRow + row;
                    }
                } else {
                    column = divide(column);
                    if (chars[i] == _L) {
                        endCol = endCol - column;
                    } else {
                        startCol = startCol + column;
                    }
                }
            }
            row = chars[6] == _F ? startRow : endRow;
            column = chars[9] == _R ? startCol : endCol;
            return new Pair(row, column);
        }

        private int divide(int from) {
            return from >>> 1;
        }
    }

    static record Pair(int row, int column) {
        public Integer calculateSeatId() {
            return row * 8 + column;
        }
    }

}
