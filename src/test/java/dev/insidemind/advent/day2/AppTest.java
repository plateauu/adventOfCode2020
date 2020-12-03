package dev.insidemind.advent.day2;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AppTest {

    @Test
    void shouldParsePasswordLine() {
        String input = "1-3 a: abcde";
        App.PasswordLine passwordLine = new App.PasswordLine(input);

        //then
        assertArrayEquals("abcde".toCharArray(), passwordLine.password);
        assertEquals(1, passwordLine.occurrences.min());
        assertEquals(3, passwordLine.occurrences.max());
        assertEquals('a', passwordLine.requiredLetter);
    }

    @Test
    void shouldParsePasswordLineWhenMoreThanOneDigitsExists() {
        String input = "11-33 a: abcde";
        App.PasswordLine passwordLine = new App.PasswordLine(input);

        //then
        assertArrayEquals("abcde".toCharArray(), passwordLine.password);
        assertEquals(11, passwordLine.occurrences.min());
        assertEquals(33, passwordLine.occurrences.max());
        assertEquals('a', passwordLine.requiredLetter);
    }

    @Test
    void shouldFindInvalidPassword() {
        String input = "1-3 b: cdefg";
        App.PasswordLine passwordLine = new App.PasswordLine(input);

        //then
        Assertions.assertFalse(passwordLine.validate());
    }

    @Test
    void shouldFindValidPassword() {
        String input = "1-3 a: abcde";
        App.PasswordLine passwordLine = new App.PasswordLine(input);

        //then
        Assertions.assertTrue(passwordLine.validate());
    }
}
