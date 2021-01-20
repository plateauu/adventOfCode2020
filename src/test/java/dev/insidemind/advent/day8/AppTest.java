package dev.insidemind.advent.day8;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AppTest {

    @ParameterizedTest
    @MethodSource({"input"})
    void shouldParseOperation(String line, App.OperationType expectedOperation, char expectedSign, int expectedCount) {
        //expect
        var parsed = new App.OperationParser(List.of(line)).parse();
        assertEquals(1, parsed.size());
        assertEquals(expectedOperation, parsed.get(0).type);
        assertEquals(expectedSign, parsed.get(0).sign);
        assertEquals(expectedCount, parsed.get(0).count);
    }

    private static Stream<Arguments> input() {
        return Stream.of(
                Arguments.of("nop +283", App.OperationType.NOP, '+', 283),
                Arguments.of("acc +26", App.OperationType.ACC, '+', 26),
                Arguments.of("jmp -269", App.OperationType.JMP, '-', 269)
        );
    }

    @Test
    void shouldFindAccumulator() {
        var input =
                List.of(
                        "nop +0",
                        "acc +1",
                        "jmp +4",
                        "acc +3",
                        "jmp -3",
                        "acc -99",
                        "acc +1",
                        "jmp -4",
                        "acc +6"
                );
        var parsed = new App.OperationParser(input).parse();
        var operationMove = new App.OperationMove(parsed);

        //when
        var accumulatorValue = operationMove.go();

        //then
        Assertions.assertEquals(5, accumulatorValue);
    }

    @Test
    void shouldFindAccumulatorWithSwap() {
        var input =
                List.of(
                        "nop +0",
                        "acc +1",
                        "jmp +4",
                        "acc +3",
                        "jmp -3",
                        "acc -99",
                        "acc +1",
                        "jmp -4",
                        "acc +6"
                );
        var parsed = new App.OperationParser(input).parse();
        var operationMove = new App.OperationMove(parsed);

        //when
        var accumulatorValue = operationMove.goWithSwap();

        //then
        Assertions.assertEquals(8, accumulatorValue);
    }}
