package com.joakimhansen;

import javax.naming.directory.InvalidAttributesException;
import java.io.*;
import java.util.*;

public class DayFour {

    private final File file = new File("/Users/jockehansen/workspace/advent-of-code-2020/input-dayfour.txt");
    private final List<String> input = new ArrayList<>();
    private final List<Map<String, String>> passports = new ArrayList<>();
    private final List<String> validEyeColor = Arrays.asList("amb", "blu", "brn", "gry", "grn", "hzl", "oth");
    private int validPassports;

    public DayFour() {
    }

    public void run() {
        try {
            parseFile();
            createPassports();
            validatePassports();
            System.out.println("# Valid passports: " + validPassports);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private void validatePassports() {
        for (Map<String, String> passport : passports) {
            if (hasMandatoryInformation(passport)) {
                try {
                    validateBirthYear(passport.get("byr"));
                    validateIssueYear(passport.get("iyr"));
                    validateExpirationYear(passport.get("eyr"));
                    validateHeight(passport.get("hgt"));
                    validateHairColor(passport.get("hcl"));
                    validateEyeColor(passport.get("ecl"));
                    validatePassportID(passport.get("pid"));
                    validPassports++;
                } catch (InvalidAttributesException | NumberFormatException e) {
                    System.out.println(e);
                }
            }
        }
    }

    //cid (Country ID) - ignored, missing or not.
    private boolean hasMandatoryInformation(Map<String, String> passport) {
        return passport.size() == 8 || (passport.size() == 7 && !passport.containsKey("cid"));
    }

    //pid (Passport ID) - a nine-digit number, including leading zeroes.
    private void validatePassportID(String pid) throws InvalidAttributesException, NumberFormatException {
        if (pid.length() != 9) {
            throw new InvalidAttributesException("Invalid PassportID {" + pid + "}");
        }
        Integer.parseInt(pid);
    }

    //ecl (Eye Color) - exactly one of: amb blu brn gry grn hzl oth.
    private void validateEyeColor(String ecl) throws InvalidAttributesException {
        if (!validEyeColor.contains(ecl)) {
            throw new InvalidAttributesException("Invalid EyeColor {" + ecl + "}");
        }
    }

    //hcl (Hair Color) - a # followed by exactly six characters 0-9 or a-f.
    private void validateHairColor(String hcl) throws InvalidAttributesException {
        if (hcl.length() != 7 || hcl.charAt(0) != '#' || !hcl.substring(1).matches("^[a-fA-F0-9]*$")) {
            throw new InvalidAttributesException("Invalid HairColor format {" + hcl + "}");
        }
    }

    //hgt (Height) - a number followed by either cm or in:
    //If cm, the number must be at least 150 and at most 193.
    //If in, the number must be at least 59 and at most 76.
    private void validateHeight(String hgt) throws InvalidAttributesException {
        if (!(hgt.contains("cm") || hgt.contains("in"))) {
            throw new InvalidAttributesException("No height unit specified");
        }

        int height = Integer.parseInt(hgt.substring(0, hgt.length() - 2));
        if (hgt.contains("cm") && (height < 150 || height > 193)) {
            throw new InvalidAttributesException("Invalid height {" + hgt + "}");
        }
        else if (hgt.contains("in") && (height < 59 || height > 76)) {
            throw new InvalidAttributesException("Invalid height {" + hgt + "}");
        }
    }

    //eyr (Expiration Year) - four digits; at least 2020 and at most 2030.
    private void validateExpirationYear(String eyr) throws InvalidAttributesException {
        int year = Integer.parseInt(eyr);
        if (year < 2020 || year > 2030) {
            throw new InvalidAttributesException("Invalid ExpirationYear {" + eyr + "}");
        }
    }

    //iyr (Issue Year) - four digits; at least 2010 and at most 2020.
    private void validateIssueYear(String iyr) throws InvalidAttributesException {
        int year = Integer.parseInt(iyr);
        if (year < 2010 || year > 2020) {
            throw new InvalidAttributesException("Invalid IssueYear {" + iyr + "}");
        }
    }

    //byr (Birth Year) - four digits; at least 1920 and at most 2002.
    private void validateBirthYear(String byr) throws InvalidAttributesException {
        int year = Integer.parseInt(byr);
        if (year < 1920 || year > 2002) {
            throw new InvalidAttributesException("Invalid BirthYear {" + byr + "}");
        }
    }

    private void createPassports() {
        Map<String, String> passport = new HashMap<>();
        for (String row : input) {
            if (row.equalsIgnoreCase("")) {
                passports.add(passport);
                passport = new HashMap<>();
                continue;
            }
            String[] fields = row.split(" ");
            for (String keyValue : fields) {
                String key = keyValue.split(":")[0];
                String value = keyValue.split(":")[1];
                passport.put(key, value);
            }
        }
    }

    private void parseFile() throws IOException {
        Reader r = new FileReader(file);
        BufferedReader reader = new BufferedReader(r);
        reader.lines().forEach(input::add);
    }
}
