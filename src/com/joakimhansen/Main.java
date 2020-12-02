package com.joakimhansen;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static final File input = new File("/Users/jockehansen/workspace/advent-of-code-2020/input.txt");
    private static final ArrayList<String> inputRows = new ArrayList<>();
    private static final ArrayList<Integer> firstPosition = new ArrayList<>();
    private static final ArrayList<Integer> secondPosition = new ArrayList<>();
    private static final ArrayList<Character> keys = new ArrayList<>();
    private static final ArrayList<String> passwords = new ArrayList<>();
    private static Integer validPasswords = 0;
    private static final Integer tobogganNoConceptOfIndexZero = 1;

    public static void main(String[] args) {
        try {
            parseFile();
            splitContent();
            validatePasswords();
            System.out.println("Number of valid passwords: " + validPasswords);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void validatePasswords() {
        for (int i = 0; i < keys.size(); i++) {
            char key = keys.get(i);
            int first = firstPosition.get(i) - tobogganNoConceptOfIndexZero;
            int second = secondPosition.get(i) - tobogganNoConceptOfIndexZero;
            String password = passwords.get(i);
            char charAtFirstPosition = password.charAt(first);
            char charAtSecondPosition = password.charAt(second);

            if (charAtFirstPosition == key ^ charAtSecondPosition == key) {
                validPasswords++;
                System.out.println("VALID - " + charAtFirstPosition + " - " + charAtSecondPosition + " " + "{" + key + "}");
            } else {
                System.out.println(charAtFirstPosition + " - " + charAtSecondPosition + " " + "{" + key + "}");
            }
        }
    }

    private static void splitContent() {
        for (int i = 0; i < inputRows.size(); i += 3) {
            String[] occurrences = inputRows.get(i).split("-");
            firstPosition.add(Integer.parseInt(occurrences[0]));
            secondPosition.add(Integer.parseInt(occurrences[1]));
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
