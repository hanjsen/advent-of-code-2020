package com.joakimhansen;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    private static final File input = new File("/Users/jockehansen/workspace/advent-of-code-2020/input.txt");
    private static final ArrayList<String> inputRows = new ArrayList<>();
    private static final ArrayList<Integer> minimumInstances = new ArrayList<>();
    private static final ArrayList<Integer> maximumInstances = new ArrayList<>();
    private static final ArrayList<Character> keys = new ArrayList<>();
    private static final ArrayList<String> passwords = new ArrayList<>();
    private static Integer validPasswords = 0;

    public static void main(String[] args) {
        try {
            parseFile();
            splitContent();
            checkPassword();
            System.out.println("Number of valid passwords: " + validPasswords);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void checkPassword() {
        for (int i = 0; i < keys.size(); i++) {
            int finalI = i;
            int occurrences = (int) passwords.get(i).chars().filter(ch -> ch == keys.get(finalI)).count();
            validate(occurrences, minimumInstances.get(i), maximumInstances.get(i));
        }
    }

    private static void validate(int occurrences, int minimum, int maximum) {
        if (occurrences >= minimum && occurrences <= maximum){
            validPasswords++;
        }
    }

    private static void splitContent() {
        for (int i = 0; i < inputRows.size(); i+=3) {
            String[] occurrences = inputRows.get(i).split("-");
            minimumInstances.add(Integer.parseInt(occurrences[0]));
            maximumInstances.add(Integer.parseInt(occurrences[1]));
            keys.add(inputRows.get(i + 1).charAt(0));
            passwords.add(inputRows.get(i + 2));
        }
    }

    private static void parseFile() throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(input);
        Scanner scanner = new Scanner(fileInputStream);

        while (scanner.hasNext()) {
            inputRows.add(scanner.next());
        }
    }

}
