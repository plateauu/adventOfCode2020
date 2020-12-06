package dev.insidemind.advent.day4;

import dev.insidemind.advent.LinesReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Function;

/**
 * Day 4 Advent od code 2020
 * https://adventofcode.com/2020/day/4
 */
class App {
    static List<String> lines;

    static {
        Path INPUT = Paths.get("src/main/java/dev/insidemind/advent/day3/input.txt");
        lines = LinesReader.readAllLines(INPUT, Function.identity());
    }

    public static void main(String[] args) {
    }
}
