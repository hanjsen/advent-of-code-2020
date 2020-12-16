package com.joakimhansen;

import java.io.*;
import java.util.*;

public class Day16 {

    private final File file = new File("./input/d16.txt");
    private final Map<String, List<String>> validTicketInformation = new HashMap<>();
    private final Map<Integer, List<Integer>> nearbyTickets = new HashMap<>();
    private final HashSet<Integer> myTicket = new HashSet<>();
    private int counter = 0;

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

    }

    private void partOne() {
        for (Map.Entry<Integer, List<Integer>> entry : nearbyTickets.entrySet()) {
            entry.getValue().forEach(this::validate);
        }
        System.out.println(counter);
    }

    private void validate(Integer value) {
        int validFields = 0;
        for (Map.Entry<String, List<String>> validTicketInformation : validTicketInformation.entrySet()) {
            String field = validTicketInformation.getKey();
            List<String> ranges = validTicketInformation.getValue();
            int firstLow = Integer.parseInt(ranges.get(0).split("-")[0]);
            int firstHigh = Integer.parseInt(ranges.get(0).split("-")[1]);
            int secondLow = Integer.parseInt(ranges.get(1).split("-")[0]);
            int secondHigh = Integer.parseInt(ranges.get(1).split("-")[1]);
            if ((value > firstLow && value < firstHigh) || (value > secondLow && value < secondHigh)) {
//                System.out.println("Valid value " + value + " ranges " + ranges.get(0) + " " + ranges.get(1));
                validFields++;
            } else {
                System.out.println("Invalid value " + value + " ranges " + ranges.get(0) + " " + ranges.get(1));
            }
        }

        if (validFields == 0){
            counter += value;
        }
    }

    private void parseFile() throws IOException {
//        Reader r = new FileReader(file);
//        BufferedReader reader = new BufferedReader(r);
        FileInputStream fileInputStream = new FileInputStream(file);

        Scanner sca = new Scanner(fileInputStream);
        while (sca.hasNext()) {
            String line = sca.nextLine();
            if (line.equalsIgnoreCase("")) {
                break;
            }

            String[] holder = line.split(":");
            String ticketInformation = holder[0].trim();
            List<String> validRanges = new ArrayList<>(Arrays.asList(holder[1].replaceAll(" ", "").split("or")));
            validTicketInformation.put(ticketInformation, validRanges);
        }


        sca.nextLine();
        Arrays.stream(sca.nextLine().split(",")).forEach(line -> {
            myTicket.add(Integer.parseInt(line));
        });
        sca.nextLine();
        sca.nextLine();

        int counter = 0;
        while (sca.hasNext()) {
            String ticket = sca.nextLine();
            if (ticket.equalsIgnoreCase("")) {
                break;
            }

            String[] val = ticket.split(",");
            List<Integer> values = new ArrayList<>();
            Arrays.stream(val).forEach(line -> values.add(Integer.parseInt(line)));
            nearbyTickets.put(counter, values);
            counter++;
        }
    }
}