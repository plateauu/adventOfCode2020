package dev.insidemind.advent.day2;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class AppTest {

    @Test
    void shouldParsePasswordLine() {
        String input = "1-3 a: abcde";
        App.PasswordLine passwordLine = new App.PasswordLine(input);

        //then
        assertArrayEquals("abcde".toCharArray(), passwordLine.password);
        assertEquals(1, passwordLine.minOccurrence);
        assertEquals(3, passwordLine.maxOccurrence);
        assertEquals('a', passwordLine.requiredLetter);
    }

}
