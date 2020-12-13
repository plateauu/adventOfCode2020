package dev.insidemind.advent.day6;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AppTest {
    @Test
    void shouldMergeIntoProperCustomGroups() {
        //when
        var input = List.of(
                "abc",
                "",
                "a",
                "b",
                "c",
                "",
                "ab",
                "ac",
                "",
                "a",
                "a",
                "a",
                "a",
                "",
                "b"
        );
        var merger = new App.CustomGroupMerger(input);

        //when
        var result = merger.merge();

        //then
        Assertions.assertEquals(5, result.size());
        result.forEach(customGroup -> Assertions.assertFalse(result.get(0).oneLine().contains(" ")));
    }
}
