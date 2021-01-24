package dev.insidemind.advent.day8;

import dev.insidemind.advent.LinesReader;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.function.Function;

import static dev.insidemind.advent.day8.App.OperationType.*;
import static java.util.stream.Collectors.toList;

/**
 * Day 8 Advent od code 2020
 * https://adventofcode.com/2020/day/8
 */
class App {

    static List<String> lines;

    static {
        Path INPUT = Paths.get("src/main/java/dev/insidemind/advent/day8/input.txt");
        lines = LinesReader.readAllLines(INPUT, Function.identity());
    }

    public static void main(String[] args) {
        partOne();
        partTwo();
    }

    private static void partOne() {
        long start = System.currentTimeMillis();
        var operations = new OperationParser(lines).parse();
        new OperationMove(operations).go();
        long stop = System.currentTimeMillis();
        var time = stop - start;
        System.out.printf("Time spend: %dms%n", time);
    }

    private static void partTwo() {
        long start = System.currentTimeMillis();
        var operations = new OperationParser(lines).parse();
        new OperationMove(operations).goWithSwap();
        long stop = System.currentTimeMillis();
        var time = stop - start;
        System.out.printf("Time spend: %dms%n", time);
    }

    static class OperationMove {
        private final Map<Operation, Integer> processed;
        private final List<Operation> operations;
        private final Operation terminalOperation;
        private final AtomicLong index = new AtomicLong();
        private final LongAccumulator accumulator;
        private final List<Operation> processedOrdered = new ArrayList<>();
        private Operation lastSwap;
        private int inCatch;
        private boolean terminal=false;

         OperationMove(List<Operation> operations) {
            this.operations = operations;
            this.processed = new HashMap<>();
            this.accumulator = new LongAccumulator(Long::sum, 0);
            this.terminalOperation = operations.get(operations.size() - 1);
        }

        long go() {
            while (true) {
                try {
                    move();
                } catch (RuntimeException exception) {
                    break;
                }
            }
            return accumulator.get();
        }

        long goWithSwap() {
            reset();
            while (true) {
                try {
                    move();
                } catch (RuntimeException exception) {
                    if (terminal) {
                        break;
                    }
                    rollback();
                    swap(inCatch);
                    inCatch++;
                    goWithSwap();
                    break;
                }
            }
            return accumulator.get();
        }

        private void reset() {
            processed.clear();
            processedOrdered.clear();
            accumulator.reset();
            index.set(0);
        }

        private void rollback() {
            if (lastSwap == null)
                return;
            doSwap(lastSwap);
            lastSwap = null;
        }

        private void swap(int inCatch) {
            var maybeSwap = processedOrdered.stream()
                    .filter(o -> o.type != ACC)
                    .collect(toList());
            var size = maybeSwap.size();
            var operation = maybeSwap.get(size - 1 - inCatch);
            lastSwap = operation;
            doSwap(operation);
        }

        private void doSwap(Operation operation) {
            operation.type = switch (operation.type) {
                case ACC -> ACC;
                case NOP -> JMP;
                case JMP -> NOP;
            };
        }

        void move() {
            Operation operation;
            try {
                operation = next(index);
            } catch (DoubleExecutionException ex) {
                System.out.printf("Doubled execution of operation. Accumulator value: %d. Infinite-loop operation: %s%n", accumulator.get(), ex.operation);
                throw ex;
            }
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

        private Operation next(AtomicLong idx) {
            var operation = operations.get(index(idx));
            processedOrdered.add(operation);
            terminal = isTerminal(operation);
            if (processed.containsKey(operation)) {
                processed.computeIfPresent(operation, (k, v) -> v++);
                throw new DoubleExecutionException(operation);
            }
            processed.putIfAbsent(operation, 0);
            return operation;
        }

        static class DoubleExecutionException extends RuntimeException {
            private static final String msg = "Operation second execution: %s%n";
            Operation operation;

            public DoubleExecutionException(Operation operation) {
                super(String.format(msg, operation.id));
                this.operation = operation;
            }
        }

        private boolean isTerminal(Operation operation) {
            return terminalOperation == operation;
        }

        private void incrementIndex() {
            index.incrementAndGet();
        }

        private static int index(AtomicLong index) {
            return Math.toIntExact(index.get());
        }
    }

    static class OperationParser {
        private final List<String> lines;

        OperationParser(List<String> lines) {
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

    static class Operation {
        OperationType type;
        final char sign;
        final int count;
        final UUID id;

        Operation(String op, char sign, int count) {
            this.id = UUID.randomUUID();
            this.type = OperationType.type(op);
            this.sign = sign;
            this.count = count;
        }

        public void acc(LongAccumulator accumulator) {
            accumulator.accumulate(parse());
        }

        private long parse() {
            return Long.parseLong(String.valueOf(this.sign) + this.count);
        }

        public void jump(AtomicLong index) {
            var current = index.get();
            index.set(parse() + current);
        }

        public void nop() {
            //do nothing
        }

        @Override
        public String toString() {
            return "Operation{" +
                    "type=" + type +
                    ", sign=" + sign +
                    ", count=" + count +
                    ", id=" + id +
                    '}';
        }
    }
}
