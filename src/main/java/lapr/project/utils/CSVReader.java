package lapr.project.utils;

import lapr.project.model.Ship;
import lapr.project.model.ShipData;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

public class CSVReader {

    public CSVReader() {

    }

    // verificar se um barco existe - através do mmsi/imo/callsign
    private static int verifyShip(String value, ArrayList<Ship> shipArray) {

        for (int i = 0; i < shipArray.size(); i++) {
            Ship ship = shipArray.get(i);
            if ((ship.getMmsi() == Integer.parseInt(value)
                    || ship.getImo() == Integer.parseInt(value)
                    || ship.getCallSign().equals(value))) {
                return i;
            }
        }

        return -1;
    }

    private static int newImo(String imo) {
        String temp = imo.substring(3, imo.length());
        return Integer.parseInt(temp);
    }


    private static int newCargo(String value) {
        if (value.equals("NA")) {
            return 0;
        }
        return Integer.parseInt(value);
    }


    public static ArrayList<Ship> readCSV(String path) throws Exception {

        DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        ArrayList<Ship> shipArray = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {

            String line = br.readLine();

            while ((line = br.readLine()) != null) {

                String[] values = line.split(",");

                ShipData sd = new ShipData(LocalDateTime.parse(values[1], formatDate),
                        Double.parseDouble(values[2]),
                        Double.parseDouble(values[3]),
                        Double.parseDouble(values[4]),
                        Double.parseDouble(values[5]),
                        Double.parseDouble(values[6]),
                        values[15].charAt(0));

                int index = verifyShip(values[0], shipArray);
                if (index == -1) { // if there's ship
                    int imo = newImo(values[8]);
                    int cargo = newCargo(values[14]);

                    Ship ship = new Ship(
                            Integer.parseInt(values[0]), // mmsi
                            null, // dynamic ship data
                            values[7], // name
                            imo, // imo
                            values[9], // callsign
                            Integer.parseInt(values[10]), // vessel
                            Double.parseDouble(values[11]), // length
                            Double.parseDouble(values[12]), // width
                            Double.parseDouble(values[13]),// draft
                            cargo);// cargo
                    ship.initializeDynamicData();
                    ship.addDynamicShip(sd);
                    shipArray.add(ship);
                    //System.out.println(ship.getDynamicShip().get(0).getDateTime());

                } else { // se o barco existir

                    shipArray.get(index).addDynamicShip(sd);

                }
            }
        }

        return shipArray;
    }

    public static ArrayList<Ship> sortByDate(ArrayList<Ship> shipArray) throws Exception {

        for (int i = 0; i < shipArray.size(); i++) {
            ArrayList<ShipData> sortedArray = (ArrayList<ShipData>) shipArray.get(i).getDynamicShip().stream()
                    .sorted(Comparator.comparing(ShipData::getDateTime).reversed())
                    .collect(Collectors.toList());

            shipArray.get(i).setDynamicShip(sortedArray);
        }

        return shipArray;
    }

}