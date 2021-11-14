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

/**
 * @author Rui Gonçalves - 1191831
 */
public final class CSVReaderUtils {

    /**
     * Private constructor of CSVReaderUtils.
     */
    private CSVReaderUtils() {
    }

    /**
     * Read a CSV file and creates an ArrayList of ships.
     * @param path - CSV file.
     * @return an ArrayList filled with ships and their dynamic data.
     * @throws Exception if the file path doesn't exist.
     */
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

                } else {
                    shipArray.get(index).addDynamicShip(sd);

                }
            }
        }
        return sortByDate(shipArray);
    }

    /**
     * Verify if ship exists inside an Arraylist - From mmsi.
     * @param value is the variable to use during the search.
     * @param shipArray an ArrayList of ships to search.
     * @return the index if ship exists or -1 if doesn't exists.
     */
    public static int verifyShip(String value, ArrayList<Ship> shipArray) {

        for (int i = 0; i < shipArray.size(); i++) {
            Ship ship = shipArray.get(i);
            if (ship.getMmsi() == Integer.parseInt(value)) {
                return i;
            }
        }

        return -1;
    }

    /**
     * Given a string of imo value and converts it into an Integer.
     * @param imo is the value
     * @return
     */
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