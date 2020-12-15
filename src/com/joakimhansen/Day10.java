package com.joakimhansen;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class Day10 {

    private final File file = new File("./input/d10.txt");
    private final TreeSet<Integer> input = new TreeSet<>();
    private List<Integer> ray;

    public Day10() {
    }

    public void run() {
        try {
            parseFile();
            //partOne();
            partTwo();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void partTwo() {
        ray = new ArrayList<>(input);
        long currTime = System.currentTimeMillis();
        long combos = getCombinations(0);
        long totalTime = System.currentTimeMillis() - currTime;
        System.out.println(combos + " - execution took " + totalTime + " ms");
    }

    private int getCombinations(int currentJolt) {
        int combinations = 0;

        for (int i = 1; i < ray.size(); i++) {
            if (ray.get(i) > currentJolt && ray.get(i) <= currentJolt + 3) {
                combinations += getCombinations(ray.get(i));
            }
        }

        return combinations + 1;
    }

    private void partOne() {
        int chargingOutlet = 0;
        int adapterJolts = input.last() + 3;
        int oneJoltDiff = 0;
        int threeJoltDiff = 0;
        ArrayList<Integer> copy = new ArrayList<>(input);

        if (chargingOutlet - (copy.get(0)) == 1) {
            oneJoltDiff++;
        } else if (chargingOutlet - (copy.get(0)) == 3) {
            threeJoltDiff++;
        }

        for (int i = 1; i < copy.size(); i++) {
            if (copy.get(i) - (copy.get(i - 1)) == 1) {
                oneJoltDiff++;
            } else if (copy.get(i) - copy.get(i - 1) == 3) {
                threeJoltDiff++;
            }
        }

        if (adapterJolts - copy.get(copy.size() - 1) == 1) {
            oneJoltDiff++;
        } else if (adapterJolts - copy.get(copy.size() - 1) == 3) {
            threeJoltDiff++;
        }


        System.out.println("1 diff " + oneJoltDiff);
        System.out.println("3 diff " + threeJoltDiff);
        System.out.println((oneJoltDiff + 1) * threeJoltDiff);
    }

    private void parseFile() throws IOException {
        Reader r = new FileReader(file);
        BufferedReader reader = new BufferedReader(r);

        reader.lines().forEach(line -> input.add(Integer.parseInt(line)));
    }
}
