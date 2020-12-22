package com.joakimhansen;

import com.joakimhansen.utils.Tile;
import com.joakimhansen.utils.TileUtil;

import java.io.*;
import java.util.*;

public class Day20 {

    private final File file = new File("./input/d20.txt");
    private final TileUtil tileUtil = new TileUtil();

    public void run() {
        parseFile();
        partOne();
        partTwo();
    }

    private void partTwo() {
        tileUtil.buildMatrix();
        tileUtil.removeAllFrames();
        tileUtil.megaMerge();
        tileUtil.findMonster();
    }

    private void partOne() {
        tileUtil.matchTiles();
        List<Tile> corners = tileUtil.getCornerTiles();
        long solution = 0;
        for (Tile t : corners) {
            if (solution == 0) {
                solution = t.getID();
            } else {
                solution *= t.getID();
            }
        }
        System.out.println("Part one solution " + solution);
    }

    private void parseFile() {
        Scanner scanner;
        try {
            scanner = new Scanner(file);
            Tile t = new Tile();
            List<String> tileContent = new ArrayList<>();
            while (scanner.hasNext()) {
                String nextLine = scanner.nextLine();
                if (nextLine.equalsIgnoreCase("")) {
                    t.setContent(tileContent);
                    t = new Tile();
                    tileContent = new ArrayList<>();
                } else if (nextLine.contains("Tile")) {
                    t.setID(Integer.parseInt(nextLine.split(" ")[1].replace(":", "").trim()));
                    tileUtil.addTile(t);
                } else {
                    tileContent.add(nextLine);
                }
            }
            t.setContent(tileContent);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}

