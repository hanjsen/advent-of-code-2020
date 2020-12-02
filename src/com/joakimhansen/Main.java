package com.joakimhansen;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static final ArrayList<Integer> expenseReport = new ArrayList<>();
    private static final File input = new File("/Users/jockehansen/workspace/advent-of-code-2020/input.txt");

    public static void main(String[] args) {
        try {
            parseFile();
            checkSumOfExpenses();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void checkSumOfExpenses() {
        for (int i = 0; i < expenseReport.size(); i++) {
            int outerNumber = expenseReport.get(i);

            for (int j = 1; j < expenseReport.size(); j++) {
                int middleNumber = expenseReport.get(j);

                for (int k = 2; k < expenseReport.size(); k++) {
                    int innerNumber = expenseReport.get(k);

                    sumNumbers(outerNumber, middleNumber, innerNumber);
                }
            }
        }
    }

    private static void parseFile() throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(input);
        Scanner scanner = new Scanner(fileInputStream);

        while (scanner.hasNext()) {
            expenseReport.add(Integer.parseInt(scanner.next()));
        }
    }

    private static void sumNumbers(int outerNumber, int middleNumber, int innerNumber) {
        int expense = outerNumber + middleNumber + innerNumber;
        if (expense == 2020) {
            multiply(outerNumber, middleNumber, innerNumber);
            System.exit(1);
        }
    }

    private static void multiply(int first, int second, int third) {
        System.out.println("** WIN **");
        System.out.println("" + first + ", " + second + ", " + third);
        System.out.println(first * second * third);
    }
}
