package dev.insidemind.advent.day5;

import org.junit.jupiter.api.Test;

class AppTest {

    @Test
    void testName() {
        System.out.printf("Right shifted >>> : %d%n", 127 >>> 1);
        System.out.printf("Right shifted >>> : %d%n", 127 >>> 1 >>> 1);
        System.out.printf("Right shifted >>> : %d%n", 127 >>> 1 >>> 1 >>> 1);
        System.out.printf("Right shifted >>> : %d%n", 127 >>> 1 >>> 1 >>> 1 >>> 1);
        System.out.printf("Right shifted >>> : %d%n", 127 >>> 1 >>> 1 >>> 1 >>> 1 >>> 1);
        System.out.printf("Right shifted >>> : %d%n", 127 >>> 1 >>> 1 >>> 1 >>> 1 >>> 1 >>> 1);
    }
}
