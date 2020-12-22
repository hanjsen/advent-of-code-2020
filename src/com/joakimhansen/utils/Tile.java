package com.joakimhansen.utils;

import java.util.List;

public class Tile {

    private char[][] content;
    private boolean canRotate = true;
    private int ID;

    public void setID(int ID) {
        this.ID = ID;
    }

    public boolean canRotate() {
        return canRotate;
    }

    public void setCanRotate(boolean locked) {
        canRotate = locked;
    }

    public void rotate() {
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
    }

    public void flip() {
        char[][] matrixCopy = content.clone();
        for (int i = 0; i < content.length; i++) {
            matrixCopy[i] = content[content.length - 1 - i];
        }
        content = matrixCopy;
    }

    public void removeFrame() {
        char[][] matrixCopy = new char[content.length - 2][content[0].length - 2];
        for (int i = 1; i < content.length - 1; i++) {
            for (int j = 1; j < content.length - 1; j++) {
                matrixCopy[i - 1][j - 1] = content[i][j];
            }
        }
        content = matrixCopy;
    }

    public void setContent(List<String> tileContent) {
        content = new char[tileContent.size()][tileContent.get(0).length()];
        for (int i = 0; i < tileContent.size(); i++) {
            String row = tileContent.get(i);
            for (int j = 0; j < row.length(); j++) {
                content[i][j] = row.charAt(j);
            }
        }
    }

    public char[][] getContent() {
        return content;
    }

    public void printTile() {
        System.out.println("Tile " + ID);
        for (int i = 0; i < content.length; i++) {
            for (int j = 0; j < content[i].length; j++) {
                System.out.printf("" + content[i][j]);
            }
            System.out.println("");
        }
        System.out.println("\n");
    }

    public String getLeftFrame() {
        StringBuilder side = new StringBuilder();
        for (char[] matrix : content) {
            side.append(matrix[0]);
        }
        return side.toString();
    }

    public String getRightFrame() {
        StringBuilder side = new StringBuilder();
        for (char[] matrix : content) {
            side.append(matrix[content[1].length - 1]);
        }
        return side.toString();
    }

    public String getTopFrame() {
        StringBuilder row = new StringBuilder();
        for (char matrix : content[0]) {
            row.append(matrix);
        }
        return row.toString();
    }

    public String getBottomFrame() {
        StringBuilder row = new StringBuilder();
        for (char matrix : content[content.length - 1]) {
            row.append(matrix);
        }
        return row.toString();
    }

    public int getID() {
        return ID;
    }
}