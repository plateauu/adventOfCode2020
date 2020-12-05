package dev.insidemind.advent.day3;

import static dev.insidemind.advent.day3.App.TrajectoryParser.FieldType.OPEN_SQUARE;
import static dev.insidemind.advent.day3.App.TrajectoryParser.FieldType.TREE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.insidemind.advent.day3.App.TrajectoryParser.Field;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.junit.jupiter.api.Test;

class AppTest {
    private List<String> input = List.of(
            "..##.......",
            "#...#...#..",
            ".#....#..#.",
            "..#.#...#.#",
            ".#...##..#.",
            "..#.##.....",
            ".#.#.#....#",
            ".#........#",
            "#.##...#...",
            "#...##....#",
            ".#..#...#.#"
    );

    @Test
    void shouldParseFieldCorrectly() {
        var trajectoryParser = new App.TrajectoryParser(input);
        Map<Integer, List<Field>> result = trajectoryParser.parse();

        assertTrue(result.keySet().containsAll(List.of(1, 2, 3, 4, 5)));
        assertFalse(result.values().stream().anyMatch(Objects::isNull));
        assertEquals(10, result.size());

        var fields = result.get(1);
        assertNotNull(fields);
        var openField = fields.get(0);
        assertEquals(1, openField.line());
        assertEquals(1, openField.index());
        assertEquals(OPEN_SQUARE, openField.type());

        var forestField = fields.get(2);
        assertEquals(1, forestField.line());
        assertEquals(3, forestField.index());
        assertEquals(TREE, forestField.type());

        var size = fields.size();
        var lastField = fields.get(size - 1);
        assertEquals(1, lastField.line());
        assertEquals(11, lastField.index());
        assertEquals(OPEN_SQUARE, lastField.type());
    }

   @Test
    void shouldCountTree() {
        var trajectoryParser = new App.TrajectoryParser(input);
        Map<Integer, List<Field>> fields = trajectoryParser.parse();
        var walker = new App.TreeCounter(fields);
        walker.count();
        assertEquals(2,walker.getTreeCount());
   }
}
