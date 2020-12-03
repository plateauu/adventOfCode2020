package dev.insidemind.advent.day2;

import static dev.insidemind.advent.day2.App.PasswordLine.ValidateType.POSITIONS;
import static dev.insidemind.advent.day2.App.PasswordLine.ValidateType.REPETITIONS;
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
        assertEquals(1, passwordLine.range.min());
        assertEquals(3, passwordLine.range.max());
        assertEquals('a', passwordLine.requiredLetter);
    }

    @Test
    void shouldParsePasswordLineWhenMoreThanOneDigitsExists() {
        String input = "11-33 a: abcde";
        App.PasswordLine passwordLine = new App.PasswordLine(input);

        //then
        assertArrayEquals("abcde".toCharArray(), passwordLine.password);
        assertEquals(11, passwordLine.range.min());
        assertEquals(33, passwordLine.range.max());
        assertEquals('a', passwordLine.requiredLetter);
    }

    @Test
    void shouldFindInvalidPasswordRegardingToRepetition() {
        String input = "1-3 b: cdefg";
        App.PasswordLine passwordLine = new App.PasswordLine(input);

        //then
        Assertions.assertFalse(passwordLine.validate(REPETITIONS));
    }

    @Test
    void shouldFindValidPasswordRegardingToRepetition() {
        String input = "1-3 a: abcde";
        App.PasswordLine passwordLine = new App.PasswordLine(input);

        //then
        Assertions.assertTrue(passwordLine.validate(REPETITIONS));
    }

    @Test
    void shouldFindInvalidPasswordRegardingToItsPosition() {
        String input = "1-3 b: cdefg";
        App.PasswordLine passwordLine = new App.PasswordLine(input);

        //then
        Assertions.assertFalse(passwordLine.validate(POSITIONS));
    }

    @Test
    void shouldFindInvalidPasswordRegardingToItsExistenceAtBothPositions() {
        String input = "1-3 b: bbbde";
        App.PasswordLine passwordLine = new App.PasswordLine(input);

        //then
        Assertions.assertFalse(passwordLine.validate(POSITIONS));
    }

    @Test
    void shouldFindValidPasswordRegardingToItsFirstPosition() {
        String input = "1-3 b: bbcde";
        App.PasswordLine passwordLine = new App.PasswordLine(input);

        //then
        Assertions.assertTrue(passwordLine.validate(POSITIONS));
    }

    @Test
    void shouldFindValidPasswordRegardingToItsSecondPosition() {
        String input = "1-3 b: abbde";
        App.PasswordLine passwordLine = new App.PasswordLine(input);

        //then
        Assertions.assertTrue(passwordLine.validate(POSITIONS));
    }
}
