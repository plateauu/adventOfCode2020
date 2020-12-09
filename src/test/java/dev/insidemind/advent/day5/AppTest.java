package dev.insidemind.advent.day5;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AppTest {

    @Test
    void testName() {
        var i = 127 >>> 1;
        System.out.printf("Right shifted >>> : %d%n", i);
//        System.out.printf("Right shifted >>> : %d%n", 127 - i);
        System.out.printf("Right shifted >>> : %d%n", 127 >>> 1 >>> 1);
        System.out.printf("Right shifted >>> : %d%n", 127 >>> 1 >>> 1 >>> 1);
        System.out.printf("Right shifted >>> : %d%n", 127 >>> 1 >>> 1 >>> 1 >>> 1);
        System.out.printf("Right shifted >>> : %d%n", 127 >>> 1 >>> 1 >>> 1 >>> 1 >>> 1);
        System.out.printf("Right shifted >>> : %d%n", 127 >>> 1 >>> 1 >>> 1 >>> 1 >>> 1 >>> 1);
    }

    @Test
    void testName2() {
        var v = 127;
        var d = divide(v);
        System.out.printf("result1: %d%n", v - d);
        v = v - d;
        d = divide(d);
        System.out.printf("result2: %d%n", v - d);
        d = divide(v);
        System.out.printf("result3: %d%n", v - d);
        d = divide(v);
        System.out.printf("result4: %d%n", v - d);
        d = divide(v);
        System.out.printf("result5: %d%n", v - d);
        d = divide(v);
        System.out.printf("result6: %d%n", v - d);
    }

    private int divide(int d) {
        return d - (d >>> 1);
    }

    @ParameterizedTest
    @CsvSource({
            "FBFBBFFRLR,44,5,357",
            "BFFFBBFRRR,70,7,567",
            "FFFBBBFRRR,14,7,119",
            "BBFFBBFRLL,102,4,820",
    })
    void shouldProperlyResultToEmptySeat(String input, Integer row, Integer column, Integer seat) {
        /**
         * FBFBBFFRLR: row 44, column 5, seat ID 357
         * BFFFBBFRRR: row 70, column 7, seat ID 567.",
         * FFFBBBFRRR: row 14, column 7, seat ID 119.",
         * BBFFBBFRLL: row 102, column 4, seat ID 820."
         */
        var seatFinder = new App.SeatFinder(input);
        Assertions.assertEquals(new App.Pair(row, column), seatFinder.find());
        Assertions.assertEquals(seat, seatFinder.find().calculateSeatId());
    }
}
