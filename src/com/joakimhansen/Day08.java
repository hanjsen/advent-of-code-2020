package com.joakimhansen;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Day08 {

    private final File file = new File("./input/d08.txt");
    private final ArrayList<String> operations = new ArrayList<>();
    private final ArrayList<Integer> arguments = new ArrayList<>();
    private ArrayList<Integer> executedOperations;


    public Day08() {
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
        executedOperations = new ArrayList<>();
        for (int index = 0; index < operations.size(); index++) {
            List<String> operationsCopy = new ArrayList<>(operations);
            int accumulator = 0;

            //TODO Fix switch
            switch (operationsCopy.get(index)) {
                case "jmp" -> operationsCopy.set(index, "nop");
                case "nop" -> operationsCopy.set(index, "jmp");
            }

            for (int i = 0; i < operationsCopy.size(); i++) {
                if (executedOperations.contains(i)) {
                    return;
                }
                executedOperations.add(i);
                int currentArgument = arguments.get(i);

                switch (operationsCopy.get(i)) {
                    case "acc" -> accumulator += currentArgument;
                    case "jmp" -> i += currentArgument - 1;
                }
            }
            System.out.println("Execution complete, accumulator " + accumulator);
        }
    }

    private void partOne() {
        executedOperations = new ArrayList<>();
        int accumulator = 0;
        for (int i = 0; i < operations.size(); i++) {

            if (executedOperations.contains(i)) {
                System.out.println("Argument " + i + " executed previously, accumulator = " + accumulator);
                return;
            }

            executedOperations.add(i);
            int currentArgument = arguments.get(i);

            switch (operations.get(i)) {
                case "acc" -> accumulator += currentArgument;
                case "jmp" -> i += currentArgument - 1;
            }
        }
    }

    private void parseFile() throws IOException {
        Reader r = new FileReader(file);
        BufferedReader reader = new BufferedReader(r);

        reader.lines().forEach(line -> {
            operations.add(line.split(" ")[0]);
            arguments.add(Integer.parseInt(line.split(" ")[1]));
        });
    }
}
