package com.joakimhansen;

import java.io.*;
import java.util.*;

public class Day20 {

    private final File file = new File("./input/d20.txt");
    private final List<Tile> tiles = new ArrayList<>();

    public void run() {
        parseFile();
        partOne();
        partTwo();
    }

    private void partTwo() {

    }

    private void partOne() {
        long solutionCode = 0;
        for (int i = 0; i < tiles.size() - 1; i++) {
            Tile currentTile = tiles.get(i);
            for (int j = 1; j < tiles.size(); j++) {
                Tile testTile = tiles.get(j);
                if (currentTile != testTile) {
                    findMatchingPieces(currentTile, testTile);
                }
            }
            if (currentTile.matchingPieces.size() == 2){
                if (solutionCode == 0){
                    solutionCode = currentTile.ID;
                } else {
                    solutionCode *= currentTile.ID;
                }
                System.out.println("Tile " + currentTile.ID + " is a corner piece");
            }
        }

        System.out.println(solutionCode);
    }

    private void findMatchingPieces(Tile currentTile, Tile testTile) {
        for (int i = 0; i < 8; i++) {
            if (i == 4) {
                currentTile.flip();
            }
            if (currentTile.downSide.equalsIgnoreCase(testTile.upperSide)) {
                currentTile.setMatchingPiece(testTile);
                testTile.setMatchingPiece(currentTile);
                break;
            } else if (currentTile.rightSide.equalsIgnoreCase(testTile.leftSide)) {
                currentTile.setMatchingPiece(testTile);
                testTile.setMatchingPiece(currentTile);
                break;
            } else if (currentTile.leftSide.equalsIgnoreCase(testTile.rightSide)) {
                currentTile.setMatchingPiece(testTile);
                testTile.setMatchingPiece(currentTile);
                break;
            } else if (currentTile.upperSide.equalsIgnoreCase(testTile.downSide)) {
                currentTile.setMatchingPiece(testTile);
                testTile.setMatchingPiece(currentTile);
                break;
            } else {
                currentTile.rotate();
            }
        }
    }

    private void parseFile() {
        Scanner scanner;
        try {
            scanner = new Scanner(file);
            Tile t = new Tile();
            tiles.add(t);
            while (scanner.hasNext()) {
                String nextLine = scanner.nextLine();
                if (nextLine.equalsIgnoreCase("")) {
                    t.createSides();
                    t = new Tile();
                    tiles.add(t);
                } else if (nextLine.contains("Tile")) {
                    t.setID(Integer.parseInt(nextLine.split(" ")[1].replace(":", "").trim()));
                } else {
                    t.setContent(nextLine);
                }
            }
            t.createSides();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static class Tile {
        private int ID;
        private List<String> content = new ArrayList<>();
        private Set<Tile> matchingPieces = new HashSet<>();
        private String upperSide;
        private String rightSide;
        private String leftSide;
        private String downSide;

        public void rotate() {
            StringBuilder sb;
            String rTemp = rightSide;
            String lTemp = leftSide;
            String uTemp = upperSide;
            String dTemp = downSide;
            rightSide = uTemp;

            sb = new StringBuilder(rTemp).reverse();
            downSide = sb.toString();

            leftSide = dTemp;

            sb = new StringBuilder(lTemp).reverse();
            upperSide = sb.toString();
        }

        public void flip() {
            StringBuilder sb;
            String uTemp = upperSide;
            String dTemp = downSide;
            String rTemp = rightSide;
            String lTemp = leftSide;
            leftSide = rTemp;
            rightSide = lTemp;

            sb = new StringBuilder(uTemp).reverse();
            upperSide = sb.toString();

            sb = new StringBuilder(dTemp).reverse();
            downSide = sb.toString();
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public void setContent(String content) {
            this.content.add(content);
        }

        public void setMatchingPiece(Tile t) {
            matchingPieces.add(t);
        }

        public void createSides() {
            for (int i = 0; i < content.size(); i++) {
                String row = content.get(i);
                char firstChar = row.charAt(0);
                char lastChar = row.charAt(row.length() - 1);
                if (i == 0) {
                    upperSide = row;
                    leftSide = "" + firstChar;
                    rightSide = "" + lastChar;
                } else if (i < content.size() - 2) {
                    leftSide += firstChar;
                    rightSide += lastChar;
                } else {
                    leftSide += firstChar;
                    rightSide += lastChar;
                    downSide = row;
                }
            }
        }

        @Override
        public String toString() {
            String tileSides = upperSide + " = Top row" + "\n" +
                    rightSide + " = Right row" + "\n" +
                    leftSide + " = Left row" + "\n" +
                    downSide + " = Bottom row" + "\n";
            return tileSides;
        }
    }
}