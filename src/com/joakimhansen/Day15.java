package com.joakimhansen;

import java.io.*;
import java.util.*;

public class Day15 {

    private final File file = new File("./input/d15.txt");
    private final ArrayList<Integer> input = new ArrayList<>();

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
        System.out.println("Part 2 answer = " + playElvesGame(30000000));
    }

    private void partOne() {
        System.out.println("Part 1 answer = " + playElvesGame(2020));
    }

    private int playElvesGame(int dinnerReady) {
        HashMap<Integer, Integer> spokenNumbersMap = new HashMap<>();
        List<Integer> spokenNumbers = new ArrayList<>(input);

        // Populate map with initial values
        spokenNumbers.subList(0, spokenNumbers.size() - 1)
                .forEach(line -> spokenNumbersMap
                        .put(line, spokenNumbers.indexOf(line))
                );

        for (int i = spokenNumbers.size() - 1; i < dinnerReady - 1; i++) {
            int recentlySpokenNumber = spokenNumbers.get(i);

            if (spokenNumbersMap.containsKey(recentlySpokenNumber)) {
                int previousIndex = spokenNumbersMap.get(recentlySpokenNumber);

                spokenNumbers.add(i - previousIndex);
                spokenNumbersMap.put(recentlySpokenNumber, i);
            } else {
                spokenNumbers.add(0);
            }
            spokenNumbersMap.put(recentlySpokenNumber, i);
        }

        return spokenNumbers.get(dinnerReady - 1);
    }

    private void parseFile() throws IOException {
        Reader r = new FileReader(file);
        BufferedReader reader = new BufferedReader(r);

        Arrays.asList(reader.readLine().split(","))
                .forEach(l -> input.add(Integer.parseInt(l)));
    }
}