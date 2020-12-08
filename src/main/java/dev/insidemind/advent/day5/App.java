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
        new SeatFinder("row");
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
        final char _A = 'A';
        final char _R = 'R';

        private final char[] chars;

        SeatFinder(String row) {
            this.row = row;
            this.chars = row.toCharArray();
        }

        public int find() {
            var start = 0;
            var end = from;
            int pivot = 128;

            for (int i = 0; i < chars.length; i++) {
                pivot = divide(pivot);
                if (chars[i] == _F) {
                    end = end - pivot;
                } else {
                    start = start + pivot;
                }
            }
            return chars[6]==_F ? start : end;
        }

        private int divide(int from) {
            return from >>> 1;
        }
    }

}
