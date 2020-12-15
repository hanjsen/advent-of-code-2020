package com.joakimhansen;

import java.io.*;
import java.util.*;

public class DaySix {

    private final File file = new File("/Users/jockehansen/workspace/advent-of-code-2020/input-06.txt");
    private final List<String> input = new ArrayList<>();
    private int declarationCounter;
    private int secondDeclarationCounter;

    public DaySix() {
    }

    public void run() {
        try {
            parseFile();
            partOne();
            partTwo();
            System.out.println(declarationCounter);
            System.out.println(secondDeclarationCounter);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private void partTwo() {
        HashMap<Integer, char[]> yesAnswers = new HashMap<>();
        int groupCount = 0;
        for (int i = 0; i < input.size(); i++) {
            if (!input.get(i).equals("")) {
                yesAnswers.put(groupCount, input.get(i).toCharArray());
                groupCount++;
            }

            if (input.get(i).equals("") || i == input.size() - 1) {
                secondDeclarationCounter = secondDeclarationCounter + countQuestions(yesAnswers);
                yesAnswers = new HashMap<>();
                groupCount = 0;
            }
        }
    }

    private int countQuestions(HashMap<Integer, char[]> yesAnswers) {
        if (yesAnswers.size() == 1) {
            return yesAnswers.get(0).length;
        }

        char[] firstPerson = yesAnswers.get(0);
        int counter = 0;
        for (char c : firstPerson) {
            int numbOfPersons = 1;
            for (int i = 1; i < yesAnswers.size(); i++) {
                char[] list = yesAnswers.get(i);
                for (char listC : list) {
                    if (listC == c) {
                        numbOfPersons++;
                    }
                }

            }

            if (numbOfPersons == yesAnswers.size()) {
                counter++;
            }
        }
        return counter;
    }

    private void partOne() {
        HashSet<Character> yesAnswers = new HashSet<>();
        for (int i = 0; i < input.size(); i++) {

            for (Character question : input.get(i).toCharArray()) {
                yesAnswers.add(question);
            }

            if (input.get(i).equals("") || i == input.size() - 1) {
                declarationCounter = declarationCounter + yesAnswers.size();
                yesAnswers = new HashSet<>();
            }

        }
    }

    private void parseFile() throws IOException {
        Reader r = new FileReader(file);
        BufferedReader reader = new BufferedReader(r);
        reader.lines().forEach(input::add);
    }
}
