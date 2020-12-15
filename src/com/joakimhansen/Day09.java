package com.joakimhansen;

import java.io.*;
import java.util.*;

public class Day09 {

    private final File file = new File("./input/d09.txt");
    private final ArrayList<Long> input = new ArrayList<>();
    private final int ENCRYPTION_WEAKNESS = 18272118;

    public Day09() {
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
        input.forEach(value -> {

            TreeSet<Long> aggregatedNumbers;
            int currentIndex = input.indexOf(value);
            long aggregatedValue = value;

            for (int j = currentIndex + 1; j < input.size(); j++) {
                aggregatedValue += input.get(j);

                if (aggregatedValue == ENCRYPTION_WEAKNESS) {
                    aggregatedNumbers = new TreeSet<>(input.subList(currentIndex, j));

                    System.out.println(
                            "Weakness found! " + (aggregatedNumbers.first() + aggregatedNumbers.last())
                                    + "\nSmallest number " + aggregatedNumbers.first()
                                    + " Largest number " + aggregatedNumbers.last()
                                    + " = " + ENCRYPTION_WEAKNESS
                    );
                    break;

                } else if (aggregatedValue > ENCRYPTION_WEAKNESS) {
                    break;
                }
            }
        });
    }

    private void partOne() {
        int PREAMBLE = 25;
        for (int i = PREAMBLE; i < input.size() - PREAMBLE; i++) {

            List<Long> subList = input.subList(i - PREAMBLE, i);
            boolean cypherKeyValidated = false;

            for (int j = 0; j < subList.size() - 2; j++) {

                for (int k = 1; k < subList.size() - 1; k++) {
                    cypherKeyValidated = validateXMAS(subList.get(j), subList.get(k), input.get(i));

                    if (cypherKeyValidated) {
                        break;
                    }
                }
                if (cypherKeyValidated) {
                    break;
                }
            }
            if (!cypherKeyValidated && i > PREAMBLE * 2) {
                System.out.println("CypherKey " + input.get(i) + " not found");
                break;
            }
        }
    }

    private boolean validateXMAS(Long input1, Long input2, Long cypherKey) {
        return input1 + input2 == cypherKey;
    }

    private void parseFile() throws IOException {
        Reader r = new FileReader(file);
        BufferedReader reader = new BufferedReader(r);

        reader.lines().forEach(line -> input.add(Long.parseLong(line)));
    }
}
