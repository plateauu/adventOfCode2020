package dev.insidemind.advent.day6;

import dev.insidemind.advent.LinesReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Function;

/**
 * Day 6 Advent od code 2020
 * https://adventofcode.com/2020/day/6
 */
class App {
    static List<String> lines;

    public static void main(String[] args) {

    }

    static {
        Path INPUT = Paths.get("src/main/java/dev/insidemind/advent/day6/input.txt");
        lines = LinesReader.readAllLines(INPUT, Function.identity());
    }
}
