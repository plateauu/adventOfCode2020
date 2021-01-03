package dev.insidemind.advent.day7;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {
    @Test
    void shouldParseRulesWithSingleElement() {
        var input = "wavy tan bags contain 2 plaid aqua bags.";

        App.BagRuleParser parser = new App.BagRuleParser(List.of(input));

        App.BagRuleParser.BagRule rule = parser.parse(input);

        assertNotNull(rule);
        assertEquals("wavy tan", rule.name());
        assertFalse(rule.end());
        assertEquals(new App.BagRuleParser.InternalElement(2, "plaid aqua"), rule.elements()[0]);
    }

    @Test
    void shouldParseRulesWith2Elements() {
        var input = "dark aqua bags contain 1 dull coral bag," +
                " 2 muted black bags.";

        App.BagRuleParser parser = new App.BagRuleParser(List.of(input));

        App.BagRuleParser.BagRule rule = parser.parse(input);

        assertNotNull(rule);
        assertEquals("dark aqua", rule.name());
        assertFalse(rule.end());
        assertEquals(new App.BagRuleParser.InternalElement(1, "dull coral"), rule.elements()[0]);
        assertEquals(new App.BagRuleParser.InternalElement(2, "muted black"), rule.elements()[1]);
    }

    @Test
    void shouldParseRulesWith3Elements() {
        var input = "dark aqua bags contain 1 dull coral bag," +
                " 4 shiny coral bags," +
                " 2 muted black bags.";

        App.BagRuleParser parser = new App.BagRuleParser(List.of(input));

        App.BagRuleParser.BagRule rule = parser.parse(input);

        assertNotNull(rule);
        assertEquals("dark aqua", rule.name());
        assertFalse(rule.end());
        assertEquals(new App.BagRuleParser.InternalElement(1, "dull coral"), rule.elements()[0]);
        assertEquals(new App.BagRuleParser.InternalElement(4, "shiny coral"), rule.elements()[1]);
        assertEquals(new App.BagRuleParser.InternalElement(2, "muted black"), rule.elements()[2]);
    }

        @Test
    void shouldParseRulesWith4Elements() {
        var input = "dark aqua bags contain 1 dull coral bag," +
                " 4 shiny coral bags," +
                " 3 vibrant crimson bags," +
                " 2 muted black bags.";

        App.BagRuleParser parser = new App.BagRuleParser(List.of(input));

        App.BagRuleParser.BagRule rule = parser.parse(input);

        assertNotNull(rule);
        assertEquals("dark aqua", rule.name());
        assertFalse(rule.end());
        assertEquals(new App.BagRuleParser.InternalElement(1, "dull coral"), rule.elements()[0]);
        assertEquals(new App.BagRuleParser.InternalElement(4, "shiny coral"), rule.elements()[1]);
        assertEquals(new App.BagRuleParser.InternalElement(3, "vibrant crimson"), rule.elements()[2]);
        assertEquals(new App.BagRuleParser.InternalElement(2, "muted black"), rule.elements()[3]);
    }

    @Test
    void shouldParseRuleWhenNoOtherBags() {
        //given
        var input = "faded blue bags contain no other bags.";

        App.BagRuleParser parser = new App.BagRuleParser(List.of(input));

        //when
        App.BagRuleParser.BagRule rule = parser.parse(input);

        //then
        assertNotNull(rule);
        assertEquals("faded blue", rule.name());
        assertEquals(rule.elements().length, 0);
        assertTrue(rule.end());
    }

    @Test
    void shouldCountBags() {
        //given
        var input = List.of(
                "dark aqua bags contain 2 dull coral bags, 1 black coral bags",
                "dull coral bags contain no other bags",
                "black coral bags contain 2 red gold bag",
                "red gold bags contain no other bags"
        );

        new App.BagRuleParser(input).parse();

        //when
        var result = App.countBagsInsideShinyGold("dark aqua");

        //then
        assertNotEquals(0, result);
        assertEquals(4, result);
    }

}
