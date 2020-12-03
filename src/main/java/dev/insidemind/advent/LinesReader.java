package dev.insidemind.advent;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class LinesReader {
    private LinesReader() {
        //do nothing
    }

    public static <T> List<T> readAllLines(Path path, Function<String, T> parser) {
        List<T> lines = null;
        try {
            lines = Files.readAllLines(path)
                         .stream()
                         .map(parser)
                         .collect(Collectors.toUnmodifiableList());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Objects.requireNonNull(lines);
        return lines;
    }
}
