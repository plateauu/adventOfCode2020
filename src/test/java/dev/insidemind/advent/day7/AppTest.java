package dev.insidemind.advent.day7;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    void shouldParseRules() {
        var input = "dark aqua bags contain 1 dull coral bag," +
                " 4 shiny coral bags, 3 vibrant crimson bags," +
                " 2 muted black bags.";

        App.BagParser parser = new App.BagParser(List.of(input));

        App.BagParser.Rule rule = parser.parse(input);

        assertNotNull(rule);
        assertEquals("dark aqua", rule.name());
        assertFalse(rule.end());
        assertEquals(new App.BagParser.InternalElement(1, "dull coral"), rule.elements()[0]);
        assertEquals(new App.BagParser.InternalElement(4, "shiny coral"), rule.elements()[1]);
        assertEquals(new App.BagParser.InternalElement(3, "vibrant crimson"), rule.elements()[2]);
        assertEquals(new App.BagParser.InternalElement(2, "muted black"), rule.elements()[3]);
    }

    @Test
    void shouldParseRuleWhenNoOtherBags() {
        //given
        var input = "faded blue bags contain no other bags.";

        App.BagParser parser = new App.BagParser(List.of(input));

        //when
        App.BagParser.Rule rule = parser.parse(input);

        //then
        assertNotNull(rule);
        assertEquals("faded blue", rule.name());
        assertEquals(rule.elements().length, 0);
        assertTrue(rule.end());
    }

}
