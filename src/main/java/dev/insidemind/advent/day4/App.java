package dev.insidemind.advent.day4;

import dev.insidemind.advent.LinesReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Pattern;

/**
 * Day 4 Advent od code 2020
 * https://adventofcode.com/2020/day/4
 */
class App {
    static List<String> lines;

    static class CredentialValidator {
        private final List<String> credentials;

        private static final Pattern PATTERN = Pattern.compile(
                "^(?=.*ecl:.*)(?=.*pid:)(?=.*eyr:.*)(?=.*byr:)(?=.*iyr:.*)(?=.*hgt:)(?=.*cid:)?.*$"
        );

        CredentialValidator(List<String> credentials) {
            this.credentials = credentials;
        }

        long validate() {
            var result = countValid();
            System.out.printf("Valid passports: %d%n", result);
            return result;
        }

        private long countValid() {
            long count = 0L;
            var invalid = new LinkedList<Credential>();
            var valid = new LinkedList<Credential>();
            for (String s : credentials) {
                if (PATTERN.matcher(s).matches()) {
                    count++;
                    valid.add(new Credential(s));
                } else {
                    invalid.add(new Credential(s));
                }
            }
            System.out.printf("Valid: %n");
            valid.stream().filter(Credential::isValid).forEach(System.out::println);
            return valid.stream().filter(Credential::isValid).count();
        }

        class Credential {
            String hgt;
            String byr;
            String pid;
            String iyr;
            String hcl;
            String eyr;
            String ecl;
            String cid;

            Credential(String line) {
                for (var s : line.split("\s")) {
                    if(s.contains("byr")) this.byr = s.substring(4);
                    if(s.contains("hgt")) this.hgt = s.substring(4);
                    if(s.contains("pid")) this.pid = s.substring(4);
                    if(s.contains("iyr")) this.iyr = s.substring(4);
                    if(s.contains("hcl")) this.hcl = s.substring(4);
                    if(s.contains("eyr")) this.eyr = s.substring(4);
                    if(s.contains("ecl")) this.ecl = s.substring(4);
                    if(s.contains("cid")) this.cid = s.substring(4);
                }
            }

            boolean isValid() {
                return hgt != null
                        && byr != null
                        && pid != null
                        && iyr != null
                        && hcl != null
                        && eyr != null
                        && ecl != null;

            }
        }

    }

    static {
        Path INPUT = Paths.get("src/main/java/dev/insidemind/advent/day4/input.txt");
        lines = LinesReader.readAllLines(INPUT, Function.identity());
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        var merged = new CredentialMerger(lines).merge();
        new CredentialValidator(merged).validate();
        long stop = System.currentTimeMillis();
        var time = stop - start;
        System.out.printf("Time spend: %dms%n", time);
    }

    static class CredentialMerger {
        private final List<String> lines;
        private final List<String> toOneLine = new ArrayList<>();
        private final List<String> result = new LinkedList<>();

        CredentialMerger(List<String> lines) {
            this.lines = lines;
        }

        List<String> merge() {
            Pattern p = Pattern.compile("^\\s*$");

            for (var line : lines) {
                if (!p.matcher(line).matches()) {
                    toOneLine.add(line);
                } else {
                    join();
                }
            }

            join();
            return result;
        }

        private void join() {
            var joined = String.join(" ", toOneLine);
            result.add(joined);
            toOneLine.clear();
        }
    }
}
