package com.joakimhansen.utils;

import java.util.*;
import java.util.function.IntPredicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TileUtil {

    private final List<Tile> tiles = new ArrayList<>();
    private final Map<Tile, List<Tile>> connectedTiles = new HashMap<>();
    private final Tile[][] matrix = new Tile[12][12];
    private char[][] megaMatrix;

    public void addTile(Tile t) {
        tiles.add(t);
        connectedTiles.put(t, new ArrayList<>());
    }

    public void buildMatrix() {
        Tile startingCorner = getCornerTiles().get(0);
        startingCorner.flip();
        startingCorner.setCanRotate(false);
        matrix[0][0] = startingCorner;
        for (int j = 0; j < matrix.length; j++) {
            if (j > 0) {
                matrix[0][j] = getColumnStartingTile(matrix[0][j - 1]);
            }
        }

        for (int i = 1; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                Tile previousTile = matrix[i - 1][j];
                matrix[i][j] = getBelowTile(previousTile);
            }
        }
    }

    private Tile getColumnStartingTile(Tile previousTile) {
        for (Tile nextTile : connectedTiles.get(previousTile)) {
            if (nextTile.canRotate()) {
                for (int i = 0; i < 8; i++) {
                    if (i == 4) {
                        nextTile.flip();
                    }
                    if (previousTile.getRightFrame().equalsIgnoreCase(nextTile.getLeftFrame())) {
                        nextTile.setCanRotate(false);
                        return nextTile;
                    } else {
                        nextTile.rotate();
                    }
                }
            }
        }

        // Shouldn't happen
        return null;
    }

    private Tile getBelowTile(Tile previousTile) {
        for (Tile nextTile : connectedTiles.get(previousTile)) {
            if (nextTile.canRotate()) {
                for (int i = 0; i < 8; i++) {
                    if (i == 4) {
                        nextTile.flip();
                    }
                    if (previousTile.getBottomFrame().equalsIgnoreCase(nextTile.getTopFrame())) {
                        nextTile.setCanRotate(false);
                        return nextTile;
                    } else {
                        nextTile.rotate();
                    }
                }
            }
        }
        return null;
    }

    public List<Tile> getCornerTiles() {
        List<Tile> cornerTiles = new ArrayList<>();
        for (Map.Entry<Tile, List<Tile>> tiles : connectedTiles.entrySet()) {
            if (tiles.getValue().size() == 2) {
                cornerTiles.add(tiles.getKey());
            }
        }
        return cornerTiles;
    }

    public void matchTiles() {
        for (int i = 0; i < tiles.size(); i++) {
            Tile currentTile = tiles.get(i);
            for (int j = 0; j < tiles.size(); j++) {
                Tile randomTile = tiles.get(j);
                validateMatch(currentTile, randomTile);
            }
        }
    }

    private void validateMatch(Tile currentTile, Tile testTile) {
        if (currentTile.equals(testTile)) {
            return;
        }

        List<Tile> connectedToCurrent = connectedTiles.get(currentTile);

        for (int i = 0; i < 8; i++) {
            if (i == 4) {
                testTile.flip();
            }
            if (currentTile.getBottomFrame().equalsIgnoreCase(testTile.getTopFrame())) {
                connectedToCurrent.add(testTile);
                break;
            } else if (currentTile.getRightFrame().equalsIgnoreCase(testTile.getLeftFrame())) {
                connectedToCurrent.add(testTile);
                break;
            } else if (currentTile.getLeftFrame().equalsIgnoreCase(testTile.getRightFrame())) {
                connectedToCurrent.add(testTile);
                break;
            } else if (currentTile.getTopFrame().equalsIgnoreCase(testTile.getBottomFrame())) {
                connectedToCurrent.add(testTile);
                break;
            } else {
                testTile.rotate();
            }
        }
    }

    public void megaMerge() {
        megaMatrix = new char[96][96];

        for (int m = 0; m < tiles.size() / 12; m++) {
            for (int i = 0; i < tiles.size() / 12; i++) {
                char[][] miniMatrix = matrix[m][i].getContent();
                for (int j = 0; j < miniMatrix.length; j++) {
                    char[] row = miniMatrix[j];
                    for (int k = 0; k < miniMatrix.length; k++) {
                        megaMatrix[j + 8 * m][k + 8 * i] = row[k];
                    }
                }
            }
        }
    }

    public void removeAllFrames() {
        for (Tile tile : tiles) {
            tile.removeFrame();
        }
    }

    public void findMonster() {
        String row1pattern = "..................#.";
        String row2pattern = "#....##....##....###";
        String row3pattern = ".#..#..#..#..#..#...";
        Pattern pattern1 = Pattern.compile(row1pattern);
        Pattern pattern2 = Pattern.compile(row2pattern);
        Pattern pattern3 = Pattern.compile(row3pattern);

        List<String> rows = new ArrayList<>();
        for (char[] row : megaMatrix) {
            StringBuilder sb = new StringBuilder();
            for (char c : row) {
                sb.append(c);
            }
            rows.add(sb.toString());
        }

        int monstersFound = 0;
        for (int k = 0; k < 8; k++) {
            if (k == 4) {
                rows = flip(rows);
            }

            for (int i = 0; i < rows.size() - 2; i++) {
                String row = rows.get(i);
                for (int j = 0; j < row.length() - 20; j++) {
                    String monsterRow1 = row.substring(j, j + 20);
                    Matcher matcher1 = pattern1.matcher(monsterRow1);
                    if (matcher1.find()) {
                        String monsterRow2 = rows.get(i + 1).substring(j, j + 20);
                        Matcher matcher2 = pattern2.matcher(monsterRow2);
                        if (matcher2.find()) {
                            String monsterRow3 = rows.get(i + 2).substring(j, j + 20);
                            Matcher matcher3 = pattern3.matcher(monsterRow3);
                            if (matcher3.find()) {
                                monstersFound++;
//                                System.out.println("Monster found starting at y " + i + ", x " + j);
                            }
                        }
                    }
                }
            }

            rows = rotate(rows);
        }
        long waves = 0;
        for (String s : rows) {
            waves += s.chars().filter(c -> c == '#').count();
        }

        int monsterCharSize = 15;
        System.out.println("Waves " + waves + ", monsters " + monstersFound + ".\nPart two solution, sea roughness " + (waves - ((long) monstersFound * monsterCharSize)));
    }


    public List<String> rotate(List<String> cnt) {
        char[][] content = new char[cnt.size()][cnt.size()];
        for (int i = 0; i < cnt.size(); i++) {
            String row = cnt.get(i);
            for (int j = 0; j < row.length(); j++) {
                content[i][j] = row.charAt(j);
            }
        }

        int row = content.length;
        for (int i = 0; i < row; i++) {
            for (int j = i; j < row; j++) {
                char temp = content[i][j];
                content[i][j] = content[j][i];
                content[j][i] = temp;
            }
        }

        StringBuilder sb;
        for (int i = 0; i < content.length; i++) {
            sb = new StringBuilder();
            for (int j = 0; j < content[i].length; j++) {
                sb.append(content[i][j]);
            }
            sb.reverse();
            for (int k = 0; k < sb.length(); k++) {
                content[i][k] = sb.charAt(k);
            }
        }

        List<String> returnList = new ArrayList<>();
        for (int i = 0; i < content.length; i++) {
            char[] r = content[i];
            sb = new StringBuilder();
            for (int j = 0; j < r.length; j++) {
                sb.append(r[j]);
            }
            returnList.add(sb.toString());
        }

        return returnList;
    }

    public List<String> flip(List<String> cnt) {
        char[][] content = new char[cnt.size()][cnt.size()];
        for (int i = 0; i < cnt.size(); i++) {
            String row = cnt.get(i);
            for (int j = 0; j < row.length(); j++) {
                content[i][j] = row.charAt(j);
            }
        }

        char[][] matrixCopy = content.clone();
        for (int i = 0; i < content.length; i++) {
            matrixCopy[i] = content[content.length - 1 - i];
        }

        List<String> returnList = new ArrayList<>();
        for (char[] r : matrixCopy) {
            StringBuilder sb = new StringBuilder();
            for (char c : r) {
                sb.append(c);
            }
            returnList.add(sb.toString());
        }

        return returnList;
    }

}