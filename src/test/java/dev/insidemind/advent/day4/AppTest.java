package dev.insidemind.advent.day4;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AppTest {
    List<String> input = List.of(
            "ecl:gry pid:860033327 eyr:2020 hcl:#fffffd",
            "byr:1937 iyr:2017 cid:147 hgt:183cm",
            "",
            "iyr:2013 ecl:amb cid:350 eyr:2023 pid:028048884",
            "hcl:#cfa07d byr:1929",
            "",
            "hcl:#ae17e1 iyr:2013",
            "eyr:2024",
            "ecl:brn pid:760753108 byr:1931",
            "hgt:179cm",
            "",
            "hcl:#cfa07d eyr:2025 pid:166559648",
            "iyr:2011 ecl:brn hgt:59in"
    );

    @Test
    void shouldParseLineProperlyIntoCredentials() {
        var credentialMerger = new App.CredentialMerger(input);
        var result = credentialMerger.merge();

        Assertions.assertEquals(4,result.size());
    }

    @Test
    void shouldFindTwoValidCredentials() {
        var credentialMerger = new App.CredentialMerger(input);
        var result = new App.CredentialValidator(credentialMerger.merge()).validate();

        Assertions.assertEquals(2,result);
    }
}
