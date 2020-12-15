package com.joakimhansen;

import java.io.*;
import java.util.*;

public class DayFive {

    private final File file = new File("/Users/jockehansen/workspace/advent-of-code-2020/input-05.txt");
    private final List<String> input = new ArrayList<>();
    private final List<Integer> seatIDs = new ArrayList<>();

    public DayFive() {
    }

    public void run() {
        try {
            parseFile();
            checkSeats();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private void checkSeats() {
        for (String i : input) {
            if (i.equals("FBFBBFFRLR")) {
                System.out.println("stop");
            }
            int row = getRow(i.substring(0, 7));
            int column = getColumn(i.substring(7));
            getSeatID(row, column);
            System.out.println("(" + i + ") ROW {" + row + "} COLUMN {" + column + "} ID {" + ((row * 8) + column) + "}");
        }
    }

    private void getSeatID(int row, int column) {
        int seatId = row * 8 + column;
        seatIDs.add(seatId);
    }

    private int getColumn(String substring) {
        int low = 0;
        int high = 7;
        for (char c : substring.substring(0, substring.length() - 1).toCharArray()) {
            if (c == 'L') {
                high = high - (int) Math.ceil((high - low) / 2.0);
            }
            else if (c == 'R') {
                low = low + (int) Math.ceil((high - low) / 2.0);
            }
        }
        int finalRow;
        if (substring.charAt(substring.length() - 1) == 'L') {
            finalRow = low;
        }
        else {
            finalRow = high;
        }
//        System.out.println(low + " " + high);
//        System.out.println("Final position [" + substring.charAt(substring.length() - 1) + "] " + finalRow);
        return finalRow;
    }

    private int getRow(String binaryRow) {
        int rows = 127;
        int low = binaryRow.charAt(0) == 'F' ? 0 : (int) Math.ceil(rows / 2.0);
        int high = binaryRow.charAt(0) == 'F' ? (int) Math.floor(rows / 2.0) : rows;
        for (char c : binaryRow.substring(1, binaryRow.length() - 1).toCharArray()) {
            if (c == 'F') {
                high = high - (int) Math.ceil((high - low) / 2.0);
            }
            else if (c == 'B') {
                low = low + (int) Math.ceil((high - low) / 2.0);
            }
        }
        int finalRow;
        if (binaryRow.charAt(binaryRow.length() - 1) == 'F') {
            finalRow = low;
        }
        else {
            finalRow = high;
        }
//        System.out.println(low + " " + high);
//        System.out.println("Final binary [" + binaryRow.charAt(binaryRow.length() - 1) + "] " + finalRow);
        return finalRow;
    }


    private void parseFile() throws IOException {
        Reader r = new FileReader(file);
        BufferedReader reader = new BufferedReader(r);
        reader.lines().forEach(input::add);
    }
}
