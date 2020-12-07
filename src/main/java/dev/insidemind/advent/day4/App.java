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

    static class CredentialValidator {
        private final List<String> credentials;

        private static final Pattern PATTERN = Pattern.compile(
                "^(?=.*hcl.*)(?=.*ecl:.*)(?=.*pid:)(?=.*eyr:.*)(?=.*byr:)(?=.*iyr:.*)(?=.*hgt:)(?=.*cid:)?.*$"
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
            var count = credentials.stream()
                                   .filter(s -> PATTERN.matcher(s).matches())
                                   .map(Credential::new)
                                   .filter(Credential::validateBirthYear)
                                   .filter(Credential::validateExpirationYear)
                                   .filter(Credential::validateEachOfField)
                                   .filter(Credential::validateEyeColor)
                                   .filter(Credential::validateHairColor)
                                   .filter(Credential::validateHeight)
                                   .filter(Credential::validateIssueYear)
                                   .filter(Credential::validatePassportId)
                                   .count();

            System.out.printf("Valid: %d%n", count);
            return count;
        }
    }

    static class Credential {
        /*
        byr (Birth Year) - four digits; at least 1920 and at most 2002.
        iyr (Issue Year) - four digits; at least 2010 and at most 2020.
        eyr (Expiration Year) - four digits; at least 2020 and at most 2030.
        hgt (Height) - a number followed by either cm or in:
        If cm, the number must be at least 150 and at most 193.
        If in, the number must be at least 59 and at most 76.
        hcl (Hair Color) - a # followed by exactly six characters 0-9 or a-f.
        ecl (Eye Color) - exactly one of: amb blu brn gry grn hzl oth.
        pid (Passport ID) - a nine-digit number, including leading zeroes.
        cid (Country ID) - ignored, missing or not.
         */
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
                if (s.contains("byr")) this.byr = s.substring(4);
                if (s.contains("hgt")) this.hgt = s.substring(4);
                if (s.contains("pid")) this.pid = s.substring(4);
                if (s.contains("iyr")) this.iyr = s.substring(4);
                if (s.contains("hcl")) this.hcl = s.substring(4);
                if (s.contains("eyr")) this.eyr = s.substring(4);
                if (s.contains("ecl")) this.ecl = s.substring(4);
                if (s.contains("cid")) this.cid = s.substring(4);
            }
        }

        boolean isValid() {
            return validateEachOfField();
        }

        private boolean validateEachOfField() {
            return hgt != null
                    && byr != null
                    && pid != null
                    && iyr != null
                    && hcl != null
                    && eyr != null
                    && ecl != null;
        }

        private boolean validateBirthYear() {
            var year = Integer.parseInt(byr);
            if (byr.toCharArray().length != 4) {
                return false;
            }
            return year >= 1920 && year <= 2002;
        }

        private boolean validateIssueYear() {
            var year = Integer.parseInt(iyr);
            if (iyr.toCharArray().length != 4) {
                return false;
            }
            return year >= 2010 && year <= 2020;
        }

        private boolean validateExpirationYear() {
            var year = Integer.parseInt(eyr);
            if (eyr.toCharArray().length != 4) {
                return false;
            }
            return year >= 2020 && year <= 2030;
        }

        private boolean validateHairColor() {
            Pattern p = Pattern.compile("^#(\\d|[a-f]){6}$");
            return p.matcher(hcl).matches();
        }

        private boolean validateEyeColor() {
            Pattern p = Pattern.compile("^(amb|blu|brn|gry|grn|hzl|oth)$");
            return p.matcher(hcl).matches();
        }

        private boolean validatePassportId() {
            Pattern p = Pattern.compile("^\\d{9}$");
            return p.matcher(pid).matches();
        }

        private boolean validateHeight() {
            Pattern p = Pattern.compile("^(\\d{2,3})(cm|in)$");
            var matchResult = p.matcher(hgt).toMatchResult();
            var number = parse(matchResult.group(1));

            if (number == null) {
                return false;
            }

            var unit = matchResult.group(2);
            if (unit.equals("cm")) {
                return number >= 150 && number <= 193;
            } else {
                return number >= 59 && number <= 76;
            }
        }

        private Integer parse(String s) {
            try {
                return Integer.parseInt(s);
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        public String toString() {
            return "Credential{" +
                    "hgt='" + hgt + '\'' +
                    ", byr='" + byr + '\'' +
                    ", pid='" + pid + '\'' +
                    ", iyr='" + iyr + '\'' +
                    ", hcl='" + hcl + '\'' +
                    ", eyr='" + eyr + '\'' +
                    ", ecl='" + ecl + '\'' +
                    ", cid='" + cid + '\'' +
                    '}';
        }
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
