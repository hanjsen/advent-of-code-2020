package com.joakimhansen;

import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Day13 {

    private final File file = new File("./input/d13.txt");
    private final List<String> inputWithEmptySlots = new ArrayList<>();
    private final List<Integer> cleanInput = new ArrayList<>();
    private int timestamp;

    public Day13() {

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
        ArrayList<Integer> minutesUntilNextDeparture = new ArrayList<>();
        for (int i = 0; i < inputWithEmptySlots.size(); i++) {
            if (!inputWithEmptySlots.get(i).equalsIgnoreCase("x")) {
                minutesUntilNextDeparture.add(i);
                BigInteger intI = new BigInteger(inputWithEmptySlots.get(i));
                for (int j = i + 1; j < inputWithEmptySlots.size(); j++) {
                    if (!inputWithEmptySlots.get(j).equalsIgnoreCase("x")) {
                        BigInteger intJ = new BigInteger(inputWithEmptySlots.get(j));
                        System.out.println("GCD " + intI + " " + intJ + " " + (intI.gcd(intJ).intValue()));
                    }
                }
            }
        }
        for (Integer i : minutesUntilNextDeparture) {
            System.out.print((minutesUntilNextDeparture.indexOf(i) == minutesUntilNextDeparture.size() - 1 ? i : i + " - "));
        }
        System.out.println("");

        int minute = 0;

        for (int i = 0; i < minutesUntilNextDeparture.size() - 1; i++) {
            for (int j = i + 1; j < minutesUntilNextDeparture.size(); j++) {
                int previousDeparture = cleanInput.get(i);
                int currentDeparture = cleanInput.get(j);
                int differenceBetweenDepartures = minutesUntilNextDeparture.get(j);

                int x = 0;
                ArrayList<Integer> deptMinutes = new ArrayList<>();
                while (deptMinutes.size() < 1000) {
                    int mod = (currentDeparture * x) % previousDeparture;
                    if (minute == 0 && mod == differenceBetweenDepartures) {
                        minute = x * currentDeparture;
//                        System.out.println("timestamp " + minute);
                        deptMinutes.add(x * currentDeparture);
                    }

                    if (mod == differenceBetweenDepartures) {
                        if (x * currentDeparture > minute || x * currentDeparture == minute) {
                            // System.out.println("timestamp : " + minute + " new timestamp " + x * currentDeparture);
                            minute = x * currentDeparture;
                        }
                        //System.out.println("Bus (" + currentDeparture + ") ANSWER " + (x * currentDeparture));
                        deptMinutes.add(x * currentDeparture);
                    }
                    x++;
                }
                deptMinutes.forEach(line -> System.out.print(line + " - "));
                System.out.println("");
            }
        }
    }


//            for (int j = 1; j < minutesUntilNextDeparture.size(); j++) {
//        boolean solved = false;
//        int previousDeparture = cleanInput.get(i);
//        int currentDeparture = cleanInput.get(j);
//        int differenceBetweenDepartures = minutesUntilNextDeparture.get(j);
//
//        int x = 0;
//        while (!solved) {
//            int mod = (currentDeparture * x) % previousDeparture;
//            if (minute == 0 && mod == differenceBetweenDepartures) {
//                minute = x * currentDeparture;
//                solved = true;
//            }
//
//            if (mod == differenceBetweenDepartures && (x * currentDeparture == minute)) {
//                System.out.println("Bus (" + currentDeparture + ") ANSWER " + (x * currentDeparture));
//                solved = true;
//            }
//            x++;
//        }
//    }

    private void partOne() {
        int minValue = 0;
        int busID = 0;
        for (Integer bus : cleanInput) {
            int minutesSinceLastDeparture = timestamp % bus;
            int minutesBeforeNextDeparture = bus - minutesSinceLastDeparture;
            if (minutesBeforeNextDeparture < minValue || minValue == 0) {
                minValue = minutesBeforeNextDeparture;
                busID = bus;
            }
        }

        System.out.println("Bus ID (" + busID + ") waited " + minValue + " minutes, AoC answer " + (busID * minValue));
    }

    private void parseFile() throws IOException {
        Reader r = new FileReader(file);
        BufferedReader reader = new BufferedReader(r);

        timestamp = Integer.parseInt(reader.readLine());
        inputWithEmptySlots.addAll(Arrays.asList(reader.readLine().split(",")));
        inputWithEmptySlots.stream()
                .filter(line -> !line.equalsIgnoreCase("x"))
                .map(Integer::parseInt)
                .forEach(cleanInput::add);
    }
}
