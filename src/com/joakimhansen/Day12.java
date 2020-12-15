package com.joakimhansen;

import java.io.*;
import java.util.*;

public class Day12 {

    private final File file = new File("./input-d12.txt");
    private final List<String> input = new ArrayList<>();
    private Map<String, Integer> manhattanDistance = new HashMap<>();

    public Day12() {

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
        manhattanDistance = new HashMap<>();
        HashMap<String, Integer> waypoint = new HashMap<>();
        waypoint.put("N", 1);
        waypoint.put("E", 10);
        waypoint.put("W", 0);
        waypoint.put("S", 0);
        manhattanDistance.put("N", 0);
        manhattanDistance.put("E", 0);
        manhattanDistance.put("W", 0);
        manhattanDistance.put("S", 0);

        for (String s : input) {
            String instruction = s.substring(0, 1);
            int distance = Integer.parseInt(s.substring(1));
            if (instruction.equalsIgnoreCase("F")) {
                manhattanDistance.put("N", manhattanDistance.get("N") + waypoint.get("N") * distance);
                manhattanDistance.put("E", manhattanDistance.get("E") + waypoint.get("E") * distance);
                manhattanDistance.put("W", manhattanDistance.get("W") + waypoint.get("W") * distance);
                manhattanDistance.put("S", manhattanDistance.get("S") + waypoint.get("S") * distance);
            } else if (instruction.equalsIgnoreCase("R")) {
                //Rotate counterclockwise
                int nValue = waypoint.get("N");
                int eValue = waypoint.get("E");
                int wValue = waypoint.get("W");
                int sValue = waypoint.get("S");
                if (distance == 90) {
                    waypoint.put("N", wValue);
                    waypoint.put("E", nValue);
                    waypoint.put("W", sValue);
                    waypoint.put("S", eValue);
                } else if (distance == 180) {
                    waypoint.put("N", sValue);
                    waypoint.put("E", wValue);
                    waypoint.put("W", eValue);
                    waypoint.put("S", nValue);
                } else if (distance == 270) {
                    waypoint.put("N", eValue);
                    waypoint.put("E", sValue);
                    waypoint.put("W", nValue);
                    waypoint.put("S", wValue);
                }
            } else if (instruction.equalsIgnoreCase("L")) {
                //Rotate clockwise
                int nValue = waypoint.get("N");
                int eValue = waypoint.get("E");
                int wValue = waypoint.get("W");
                int sValue = waypoint.get("S");
                if (distance == 90) {
                    waypoint.put("N", eValue);
                    waypoint.put("E", sValue);
                    waypoint.put("W", nValue);
                    waypoint.put("S", wValue);
                } else if (distance == 180) {
                    waypoint.put("N", sValue);
                    waypoint.put("E", wValue);
                    waypoint.put("W", eValue);
                    waypoint.put("S", nValue);
                } else if (distance == 270) {
                    waypoint.put("N", wValue);
                    waypoint.put("E", nValue);
                    waypoint.put("W", sValue);
                    waypoint.put("S", eValue);
                }
            } else {
                waypoint.put(instruction, waypoint.get(instruction) + distance);
            }
        }

        printSolution("""
                    \n--------------
                    Part Two
                    --------------
                    """);
    }

    private void partOne() {
        manhattanDistance = new HashMap<>();
        List<String> turns = new ArrayList<>();
        for (String s : input) {
            String direction = s.substring(0, 1);
            Integer distance = Integer.parseInt(s.substring(1));
            if (!direction.equalsIgnoreCase("L")
                    && !direction.equalsIgnoreCase("R")
                    && !direction.equalsIgnoreCase("F")) {
                if (manhattanDistance.containsKey(direction)) {
                    manhattanDistance.put(direction, distance + manhattanDistance.get(direction));
                } else {
                    manhattanDistance.put(direction, distance);
                }
            } else {
                turns.add(s);
            }
        }

        computeTurns(turns);
        printSolution("""
                --------------
                Part One
                --------------
                """);
    }

    private void printSolution(String s) {
        System.out.println(s);
        for (Map.Entry<String, Integer> e :
                manhattanDistance.entrySet()) {
            System.out.println(e.getKey() + " - distance [" + e.getValue() + "]");
        }
        System.out.println("W/E = " + Math.abs(manhattanDistance.get("W") - manhattanDistance.get("E")));
        System.out.println("S/N = " + Math.abs(manhattanDistance.get("S") - manhattanDistance.get("N")));
        System.out.println("Manhattan distance = " + (Math.abs(manhattanDistance.get("W") - manhattanDistance.get("E")) + Math.abs(manhattanDistance.get("S") - manhattanDistance.get("N"))));
    }

    private void computeTurns(List<String> turns) {
        int currentDirection = 0;
        int compassDegrees = 360;
        Map<Integer, String> compass = new HashMap<>();
        compass.put(0, "E");
        compass.put(90, "S");
        compass.put(180, "W");
        compass.put(270, "N");

        for (String s : turns) {
            String operation = s.substring(0, 1);
            Integer distance = Integer.parseInt(s.substring(1));
            if (operation.equalsIgnoreCase("F")) {
                if (manhattanDistance.containsKey(compass.get(currentDirection))) {
                    manhattanDistance.put(compass.get(currentDirection), distance + manhattanDistance.get(compass.get(currentDirection)));
                } else {
                    manhattanDistance.put(compass.get(currentDirection), distance);
                }
            } else if (operation.equalsIgnoreCase("R")) {
                currentDirection = currentDirection + distance;
                if (currentDirection >= compassDegrees) {
                    currentDirection = currentDirection - compassDegrees;
                }
            } else if (operation.equalsIgnoreCase("L")) {
                currentDirection = currentDirection - distance;
                if (currentDirection < 0) {
                    currentDirection = currentDirection + compassDegrees;
                } else if (currentDirection == compassDegrees) {
                    currentDirection = 0;
                }
            }
        }
    }

    private void parseFile() throws IOException {
        Reader r = new FileReader(file);
        BufferedReader reader = new BufferedReader(r);
        reader.lines().forEach(input::add);
    }
}
