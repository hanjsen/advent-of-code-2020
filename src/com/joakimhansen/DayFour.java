package com.joakimhansen;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DayFour {

    private final File input = new File("/Users/jockehansen/workspace/advent-of-code-2020/input-dayfour.txt");
    private final List<String> strings = new ArrayList<>();
    private final int REQUIRED_FIELDS = 7;
    private int validPassports;

    public DayFour() {
    }

    public void run() {
        try {
            parseFile();
            validatePassport();
            System.out.println(validPassports);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private void validatePassport() {
        boolean hadCid = false;
        int fields = 0;
        for (int i = 0; i < strings.size(); i++) {
            if (strings.get(i).contains("cid")) {
                hadCid = true;
            }
            for (char c : strings.get(i).toCharArray()) {
                if (c == ':') {
                    fields++;
                }
            }

            if (strings.get(i).equals("") || i == strings.size() - 1) {
                if (!hadCid && fields == REQUIRED_FIELDS) {
                    validPassports++;
                }
                else if (hadCid && fields == REQUIRED_FIELDS + 1) {
                    validPassports++;
                }
                hadCid = false;
                fields = 0;
            }
        }
    }

    private void parseFile() throws IOException {
        Reader r = new FileReader(input);
        BufferedReader reader = new BufferedReader(r);
        reader.lines().forEach(strings::add);
    }
}
