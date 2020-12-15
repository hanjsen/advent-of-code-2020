package com.joakimhansen;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Day14 {

    private final File file = new File("./input/d14.txt");
    private final ArrayList<String> operations = new ArrayList<>();
    private final HashMap<Integer, String> memory = new HashMap<>();
    private String currentBitmask;

    public Day14() {

    }

    public void run() {
        try {
            parseFile();
            partOne();
            partTwo();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void partTwo() {

    }


    private void partOne() {
        for (String operation : operations) {
            if (operation.contains("mask")) {
                setBitmask(operation);
            } else {
                nextOperation(operation);
            }
        }

        summarizeMemory();
    }

    private void summarizeMemory() {
        long sum = 0L;
        for (String m : memory.values()) {
            long currentMemory = Long.parseLong(m, 2);
            sum = sum + currentMemory;
        }

        System.out.println("Sum of memory values : " + sum);
    }

    private void nextOperation(String operation) {
        int memoryAddress = Integer.parseInt(operation.split("=")[0]
                .replace("[", "")
                .replace("]", "")
                .substring(3)
                .trim()
        );

        String bits = bitConvert(operation.split("=")[1].trim());

        storeInMemory(memoryAddress, bits);
    }

    private void storeInMemory(int memoryAddress, String value) {
        char[] value32bit = setPadding(value).toCharArray();
        for (int i = 0; i < currentBitmask.length(); i++) {
            if (currentBitmask.charAt(i) == '0') {
                value32bit[i] = '0';
            } else if (currentBitmask.charAt(i) == '1') {
                value32bit[i] = '1';
            }
        }

        memory.put(memoryAddress
                , Arrays.toString(value32bit)
                        .replaceAll(",", "")
                        .replaceAll(" ", "")
                        .replace("[", "")
                        .replace("]", "")
                        .trim()
        );
    }

    private String setPadding(String bit) {
        return ("000000000000000000000000000000000000" + bit).substring(bit.length());
    }

    private String bitConvert(String integer) {
        return Integer.toBinaryString(Integer.parseInt(integer));
    }

    private void setBitmask(String operation) {
        currentBitmask = operation.split("=")[1].trim();
    }

    private void parseFile() throws IOException {
        Reader r = new FileReader(file);
        BufferedReader reader = new BufferedReader(r);

        reader.lines().forEach(operations::add);
    }
}