package dev.insidemind.advent.day5;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AppTest {

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
