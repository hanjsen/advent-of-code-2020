package com.joakimhansen;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Day11 {

    private final File file = new File("./input/d11.txt");
    private char[][] matrix;
    private char[][] swaps;

    public Day11() {
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
        while (applyPartTwoRules(matrix)) {
            makeSwaps(swaps);
//            Arrays.stream(matrix).forEach(System.out::println);
//            System.out.println("");
        }
        System.out.println("Occupied seats - (" + countFilledSeats() + ")");
    }

    private boolean applyPartTwoRules(char[][] matrix) {
        boolean swapsWillHappen = false;
        swaps = new char[matrix.length][matrix[0].length];
        for (int y = 0; y < matrix.length; y++) {
            for (int x = 0; x < matrix[0].length; x++) {
                swaps[y][x] = '.';
                if (matrix[y][x] == '#' || matrix[y][x] == 'L') {
                    int visibleSeats = checkUpDown(y, x) + checkDiagonal(y, x);
                    if (visibleSeats >= 5 && matrix[y][x] == '#') {
                        swapsWillHappen = true;
                        swaps[y][x] = matrix[y][x];
                    } else if (matrix[y][x] == 'L' && visibleSeats == 0) {
                        swapsWillHappen = true;
                        swaps[y][x] = matrix[y][x];
                    }
                }
            }
        }

        return swapsWillHappen;
    }

    private int checkDiagonal(int y, int x) {
        int counter = 0;

        for (int i = 1; i < Math.min(y + 1, x + 1); i++) {
            int verticalPos = y - i;
            int horizontalPos = x - i;
            if (matrix[verticalPos][horizontalPos] == 'L')
                break;
            if (matrix[verticalPos][horizontalPos] == '#') {
                counter++;
                break;
            }
        }

        for (int i = 1; i < Math.min(y + 1, (matrix[0].length - x)); i++) {
            int verticalPos = y - i;
            int horizontalPos = x + i;
            if (matrix[verticalPos][horizontalPos] == 'L')
                break;
            if (matrix[verticalPos][horizontalPos] == '#') {
                counter++;
                break;
            }
        }


        for (int i = 1; i < Math.min((matrix.length - y), x + 1); i++) {
            int verticalPos = y + i;
            int horizontalPos = x - i;
            if (matrix[verticalPos][horizontalPos] == 'L')
                break;
            if (matrix[verticalPos][horizontalPos] == '#') {
                counter++;
                break;
            }
        }

        for (int i = 1; i < Math.min((matrix.length - y), (matrix[0].length - x)); i++) {
            int verticalPos = y + i;
            int horizontalPos = x + i;
            if (matrix[verticalPos][horizontalPos] == 'L')
                break;
            if (matrix[verticalPos][horizontalPos] == '#') {
                counter++;
                break;
            }
        }

        return counter;
    }

    private int checkUpDown(int y, int x) {
        int counter = 0;

        //Left
        for (int i = x - 1; i >= 0; i--) {
            if (matrix[y][i] == 'L')
                break;
            if (matrix[y][i] == '#') {
                counter++;
                break;
            }
        }

        //Right
        for (int i = x + 1; i < matrix[x].length; i++) {
            if (matrix[y][i] == 'L')
                break;
            if (matrix[y][i] == '#') {
                counter++;
                break;
            }
        }

        //Up
        for (int i = y - 1; i >= 0; i--) {
            if (matrix[i][x] == 'L')
                break;
            if (matrix[i][x] == '#') {
                counter++;
                break;
            }
        }

        //Down
        for (int i = y + 1; i < matrix[y].length; i++) {
            if (matrix[i][x] == 'L')
                break;
            if (matrix[i][x] == '#') {
                counter++;
                break;
            }
        }

        return counter;
    }

    private void partOne() {
        fillSeats(matrix);
        while (applyPartOneRules(matrix, 4)) {
            makeSwaps(swaps);
            //Arrays.stream(matrix).forEach(System.out::println);
        }
        System.out.println("Occupied seats - (" + countFilledSeats() + ")");
    }

    private int countFilledSeats() {
        int counter = 0;
        for (char[] chars : matrix) {
            for (int x = 0; x < matrix[0].length; x++) {
                if (chars[x] == '#') {
                    counter++;
                }
            }
        }
        return counter;
    }

    private void makeSwaps(char[][] swaps) {
        for (int y = 0; y < swaps.length; y++) {
            for (int x = 0; x < swaps[y].length; x++) {
                if (swaps[y][x] == '#' || swaps[y][x] == 'L') {
                    swapSeat(y, x);
                }
            }
        }
    }

    private boolean applyPartOneRules(char[][] matrix, int visibleOccupiedSeats) {
        swaps = new char[matrix.length][matrix[0].length];
        boolean swapsWillHappen = false;
        for (int y = 0; y < matrix.length; y++) {
            for (int x = 0; x < matrix[y].length; x++) {
                swaps[y][x] = '.';
                if (matrix[y][x] == 'L') {
                    if (hasAdjacent(y, x) == 0) {
                        swapsWillHappen = true;
                        swaps[y][x] = matrix[y][x];
                    }
                } else if (matrix[y][x] == '#') {
                    if (hasAdjacent(y, x) >= visibleOccupiedSeats) {
                        swapsWillHappen = true;
                        swaps[y][x] = matrix[y][x];
                    }
                }
            }
        }
        return swapsWillHappen;
    }

    private int hasAdjacent(int y, int x) {
        int adjacentSeats = 0;
        int startY = y - 1;
        int startX = x - 1;
        int iterLengthY = 3;
        int iterLengthX = 3;
        if (y == 0) {
            startY = y;
            iterLengthY = 2;
        }
        if (x == 0) {
            startX = x;
            iterLengthX = 2;
        }
        if (y == matrix.length - 1) {
            iterLengthY = 2;
        }
        if (x == matrix[0].length - 1) {
            iterLengthX = 2;
        }
        for (int i = startY; i < startY + iterLengthY; i++) {
            for (int j = startX; j < startX + iterLengthX; j++) {
                try {
                    if (matrix[i][j] == '#' && !(i == y && j == x)) {
                        adjacentSeats++;
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("" + i + " " + j);
                }
            }
        }
        return adjacentSeats;
    }

    private void fillSeats(char[][] matrix) {
        for (int y = 0; y < matrix.length; y++) {
            for (int x = 0; x < matrix[y].length; x++) {
                swapSeat(y, x);
            }
        }
    }

    private void swapSeat(int y, int x) {
        switch (matrix[y][x]) {
            case 'L' -> matrix[y][x] = '#';
            case '#' -> matrix[y][x] = 'L';
        }
    }

    private void parseFile() throws IOException {
        Reader r = new FileReader(file);
        BufferedReader reader = new BufferedReader(r);
        List<String> input = new ArrayList<>();
        reader.lines().forEach(input::add);
        matrix = new char[input.get(0).length()+1][input.size()];
        for (int y = 0; y < input.size(); y++) {
            String line = input.get(y);
            for (int x = 0; x < line.length(); x++) {
                matrix[y][x] = line.charAt(x);
            }
        }
    }
}
