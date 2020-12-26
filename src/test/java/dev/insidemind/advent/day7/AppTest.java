package dev.insidemind.advent.day7;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import org.junit.jupiter.api.Test;

class AppTest {

    @Test
    void shouldParseRule() {
        var input = "dark aqua bags contain 1 dull coral bag," +
            " 4 shiny coral bags, 3 vibrant crimson bags," +
            " 2 muted black bags.";

        App.BagParser parser = new App.BagParser(List.of(input));

        String[] parsed = parser.parse(input);

        assertNotNull(parsed);
        assertNotNull(parsed[0]);
        assertNotNull(parsed[1]);
        assertNotNull(parsed[2]);
        assertNotNull(parsed[3]);
        assertNotNull(parsed[4]);
        assertNotNull(parsed[5]);

        assertEquals("dark aqua", parsed[0]);
        assertEquals("1 dull coral", parsed[2]);
        assertEquals("4 shiny coral", parsed[3]);
        assertEquals("3 vibrant crimson", parsed[4]);
        assertEquals("2 muted black", parsed[5]);
    }

    @Test
    void shouldParseRuleWhenNoOtherBags() {
        var input = "faded blue bags contain no other bags.";

        App.BagParser parser = new App.BagParser(List.of(input));

        String[] parsed = parser.parse(input);

        assertNotNull(parsed);
        assertNotNull(parsed[0]);
        assertNotNull(parsed[1]);

        assertEquals("faded blue", parsed[0]);
        assertEquals("no other bags.", parsed[1]);
    }

}
