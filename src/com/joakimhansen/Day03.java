package com.joakimhansen;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Day03 {

    private final File input = new File("./input-d03.txt");
    private final List<String> slope = new ArrayList<>();
    private final List<Integer> horizontalAttackAngle = Arrays.asList(1, 3, 5, 7, 1);
    private final List<Integer> verticalAttackAngle = Arrays.asList(1, 1, 1, 1, 2);
    private final List<Integer> treesHitPerAttempt = new ArrayList<>();
    private int tobogganPosition;
    private int slopeWidth;
    private int treesHit;

    public Day03() {
    }

    public void run() {
        try {
            parseFile();
            setSlopeWidth();
            startAttempts();
            printAggregatedTreesHit();
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
    }

    private void startAttempts() {
        for (int attempt = 0; attempt < horizontalAttackAngle.size(); attempt++) {
            resetRideStats();
            rideTobogganToAirport(attempt);
            System.out.println("Hit " + treesHit + " trees, current attack angle {" + horizontalAttackAngle.get(attempt) + " - " + verticalAttackAngle.get(attempt) +"}");
            treesHitPerAttempt.add(treesHit);
        }
    }

    private void resetRideStats() {
        tobogganPosition = 0;
        treesHit = 0;
    }

    private void printAggregatedTreesHit() {
        long aggregatedCrashes = 1;
        for (Integer trees : treesHitPerAttempt) {
            aggregatedCrashes *= trees;
        }
        System.out.println(aggregatedCrashes);
    }

    private void setSlopeWidth() {
        slopeWidth = slope.get(0).length();
    }

    private void rideTobogganToAirport(Integer attempt) {
        int attackAngle = verticalAttackAngle.get(attempt);
        for (int downhillLevel = 0; downhillLevel < slope.size(); downhillLevel += attackAngle) {
            checkOffpist();
            checkIfTreeHit(slope.get(downhillLevel));
            tobogganPosition += horizontalAttackAngle.get(attempt);
        }
    }

    private void checkIfTreeHit(String slopeLevel) {
        char currentPosition = slopeLevel.charAt(tobogganPosition);

        if (isTree(currentPosition)) {
            treesHit++;
        }
    }

    private void checkOffpist() {
        if (tobogganPosition >= slopeWidth) {
            repeatSlope(slopeWidth);
        }
    }

    private void repeatSlope(int slopeWidth) {
        tobogganPosition = (tobogganPosition - slopeWidth);
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
