package com.joakimhansen;

import java.io.*;
import java.util.*;

public class Day07 {

    private final File file = new File("./input-d07.txt");
    private final Map<String, String[]> input = new HashMap<>();
    private final TreeSet<String> canHoldShinyGoldBag = new TreeSet<>();
    private int totalBags = 0;

    public Day07() {
    }

    public void run() {
        try {
            parseFile();
            partOne();
            partTwo();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private void partTwo() {
        System.out.println(recursiveFindInnerBags("1 shiny gold") - 1);
    }

    private int recursiveFindInnerBags(String bag) {
        String currentBagType = bag.substring(2).trim();
        String[] currentBagContent = input.get(currentBagType);
        int downstreamBagCount = 0;

        if (isEmpty(currentBagContent)) {
            return 1;
        }

        for (String innerBag : currentBagContent) {
            int currentBags = Integer.parseInt(innerBag.substring(0, 1));
            downstreamBagCount += currentBags * recursiveFindInnerBags(innerBag);
        }

        return 1 + downstreamBagCount;
    }

    private boolean isEmpty(String[] currentBagContent) {
        return currentBagContent == null || currentBagContent[0].equals("") || currentBagContent[0].equalsIgnoreCase("no other");
    }

    private void partOne() {
        recursiveFindOuterBag("shiny gold");
        System.out.println(canHoldShinyGoldBag.size());
    }

    private void recursiveFindOuterBag(String bag) {
        for (Map.Entry<String, String[]> entry : input.entrySet()) {
            for (String value : entry.getValue()) {
                if (value.contains(bag)) {
                    canHoldShinyGoldBag.add(entry.getKey());
                    recursiveFindOuterBag(entry.getKey());
                }
            }
        }
    }

    private void parseFile() throws IOException {
        Reader r = new FileReader(file);
        BufferedReader reader = new BufferedReader(r);

        reader.lines().forEach(line ->
                input.put(line
                                .split(" contain ")[0]
                                .replace("bags", "").trim()
                        , line
                                .replace(".", "")
                                .replace("bags", "")
                                .replace("bag", "")
                                .split(" contain ")[1].trim()
                                .split(", ")));
    }
}
