package com.joakimhansen;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Day22 {

    private final File file = new File("./input/d22.txt");
    private final LinkedList<Integer> p1 = new LinkedList<>();
    private final LinkedList<Integer> p2 = new LinkedList<>();

    public void run() {
        parseInput();
        partOne();
        partTwo();
    }

    private void partTwo() {
    }

    private void partOne() {
        while (!p1.isEmpty() && !p2.isEmpty()) {
            int p1card = p1.poll();
            int p2card = p2.poll();
            if (p1card > p2card) {
                p1.add(p1card);
                p1.add(p2card);
            } else if (p2card > p1card) {
                p2.add(p2card);
                p2.add(p1card);
            }
        }

        Deque<Integer> winningPlayer = new LinkedList<>(p1);
        winningPlayer.addAll(p2);
        int score = 0;
        while (!winningPlayer.isEmpty()){
            score = score + winningPlayer.poll() * (winningPlayer.size()+1);
        }
        System.out.println(score);
    }

    private void parseInput() {
        try {
            Scanner scanner = new Scanner(file);
            List<Integer> temp = new ArrayList<>();
            while (scanner.hasNext()) {
                String nextLine = scanner.nextLine();
                if (!nextLine.contains("Player") && !nextLine.equalsIgnoreCase("")) {
                    temp.add(Integer.parseInt(nextLine));
                }
            }
            for (int i = 0; i < temp.size(); i++) {
                if (i < temp.size() / 2) {
                    p1.add(temp.get(i));
                } else {
                    p2.add(temp.get(i));
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}

