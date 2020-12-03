package com.joakimhansen;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DayThree {

    private final File input = new File("/Users/jockehansen/workspace/advent-of-code-2020/input-daythree.txt");
    private final List<String> slope = new ArrayList<>();
    private int slopeWidth;
    private int treesHit;
    private int tobogganHorizontalAlignment;

    public DayThree() {
    }

    public void run() {
        try {
            parseFile();
            setSlopeWidth();
            rideTobogganToAirport();
            System.out.println(treesHit);
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
    }

    private void setSlopeWidth() {
        slopeWidth = slope.get(0).length();
    }

    private void rideTobogganToAirport() {
        for (String slopeLevel : slope) {
            checkOffpist();
            checkIfTreeHit(slopeLevel);
            tobogganHorizontalAlignment += 3;
        }
    }

    private void checkIfTreeHit(String slopeLevel) {
        char currentPosition = slopeLevel.charAt(tobogganHorizontalAlignment);
        
        if (isTree(currentPosition)) {
            treesHit++;
        }
    }

    private void checkOffpist() {
        if (tobogganHorizontalAlignment >= slopeWidth) {
            repeatSlope(slopeWidth);
        }
    }

    private void repeatSlope(int slopeWidth) {
        tobogganHorizontalAlignment = (tobogganHorizontalAlignment - slopeWidth);
    }

    private boolean isTree(char charAt) {
        return charAt == '#';
    }

    private void parseFile() throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(input);
        Scanner scanner = new Scanner(fileInputStream);

        while (scanner.hasNext()) {
            slope.add(scanner.next());
        }
    }
}
