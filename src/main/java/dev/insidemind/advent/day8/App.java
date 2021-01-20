package dev.insidemind.advent.day8;

import dev.insidemind.advent.LinesReader;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.function.Function;

import static dev.insidemind.advent.day8.App.OperationType.*;
import static java.lang.String.format;
import static java.lang.String.valueOf;
import static java.util.stream.Collectors.toList;

/**
 * Day 8 Advent od code 2020
 * https://adventofcode.com/2020/day/8
 */
class App {

    static List<String> lines;
    static Set<Integer> visitedOperations = new HashSet<>();
    static List<Operation> original;

    static {
        Path INPUT = Paths.get("src/main/java/dev/insidemind/advent/day8/input.txt");
        lines = LinesReader.readAllLines(INPUT, Function.identity());
    }

    static AtomicLong count = new AtomicLong();

    private static void partOne() {
        long start = System.currentTimeMillis();
        var operations = new OperationParser(lines).parse();
        new OperationMove(operations).go();
        long stop = System.currentTimeMillis();
        var time = stop - start;
        System.out.printf("Time spend: %dms%n", time);
    }

    public static void main(String[] args) {
        partOne();
        partTwo();
    }

    private static void partTwo() {
        long start = System.currentTimeMillis();
        var operations = new OperationParser(lines).parse();
        var value = new OperationMove(operations).goWithSwap();
        System.out.printf("Accumulator value: %s", value);
        long stop = System.currentTimeMillis();
        var time = stop - start;
        System.out.printf("Time spend: %dms%n", time);
    }

    static class OperationMove {
        static Operation lastOperation;
        private final Map<Operation, Integer> processed;
        private final List<Operation> operations;
        private final AtomicLong index = new AtomicLong();
        private final LongAccumulator accumulator;

        public OperationMove(List<Operation> operations) {
            this.operations = operations;
            this.processed = new HashMap<>();
            this.accumulator = new LongAccumulator(Long::sum, 0);
        }

        long go() {
            for (Operation operation : operations) {
                try {
                    move(operation);
                } catch (RuntimeException exception) {
                    break;
                }
            }
            return accumulator.get();
        }

        void move(Operation operation) {
            try {
                validate(operation);
            } catch (RuntimeException ex) {
                System.out.printf("Doubled execution of operation. Accumulator value: %d%n", accumulator.get());
                if (visitedOperations.contains(operations.indexOf(operation)))
                    throw ex;
            }
            proceedOperation(operation);
        }

        private void proceedOperation(Operation operation) {
            index.set(operations.indexOf(operation));

            switch (operation.type) {
                case ACC -> {
                    operation.acc(accumulator);
                    incrementIndex();
                }
                case JMP -> operation.jump(index);
                case NOP -> {
                    operation.nop();
                    incrementIndex();
                }
            }
        }

        private void incrementIndex() {
            index.incrementAndGet();
        }

        private Operation validate(Operation operation) {
            if (processed.containsKey(operation)) {
                processed.computeIfPresent(operation, (k, v) -> v++);
                throw new OperationDoubledException(operation);
            }
            processed.putIfAbsent(operation, 0);
            return operation;
        }

        long goWithSwap() {
            index.set(0);
            while (true) {
                var index = Math.toIntExact(this.index.get());
                if(index==operations.size()-1)
                    break;
                visitedOperations.add(index);
                var operation = operations.get(index);
                try {
                    move(operation);
                } catch (OperationDoubledException exception) {
                    var invokedTwice = exception.doubled;
                    var invokedTwiceOperationIndex = operations.indexOf(invokedTwice);
                    var previousOperationIndex = Math.max(invokedTwiceOperationIndex - 1, 0);
                    var previousOperation = operations.get(previousOperationIndex);
                    previousOperation.type = swap(previousOperation);
                    return new OperationMove(operations).goWithSwap();
                }
            }
            return accumulator.get();
        }

        private OperationType swap(Operation doubced) {
            lastOperation = doubced;
            return switch (doubced.type) {
                case NOP -> JMP;
                case ACC -> ACC;
                case JMP -> NOP;
            };
        }
    }

    static class OperationParser {

        private final List<String> lines;

        public OperationParser(List<String> lines) {
            this.lines = lines;
        }

        List<Operation> parse() {
            return lines.stream().map(this::parse).collect(toList());
        }

        private Operation parse(String s) {
            var operation = s.split("\s");
            var op = operation[0];
            var sign = operation[1].charAt(0);
            var count = Integer.parseInt(operation[1].substring(1));
            return new Operation(op, sign, count);
        }

    }

    enum OperationType {
        JMP, ACC, NOP;

        static OperationType type(String from) {
            return Arrays.stream(values()).filter(v -> v.name().equals(from.toUpperCase())).findAny().orElseThrow();
        }
    }

    private static class OperationDoubledException extends RuntimeException {
        Operation doubled;

        public OperationDoubledException(Operation doubled) {
            super(format("Operation with id %s breaks a program execution. %n", doubled.id));
            this.doubled = doubled;
        }
    }

    static class Operation {

        final long id;
        final char sign;
        final int count;
        OperationType type;

        Operation(String op, char sign, int count) {
            this.id = App.count.getAndIncrement();
            this.type = type(op);
            this.sign = sign;
            this.count = count;
        }

        public void acc(LongAccumulator accumulator) {
            accumulator.accumulate(parse());
        }

        private long parse() {
            return Long.parseLong(valueOf(this.sign) + this.count);
        }

        public void jump(AtomicLong index) {
            var current = index.get();
            index.set(parse() + current);
        }

        public void nop() {
            //do nothing
        }
    }


}
