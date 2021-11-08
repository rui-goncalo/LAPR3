package lapr.project.model;

import lapr.project.tree.BST;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CSVReader {

    // verificar se um barco existe - através do mmsi/imo/callsign
    private static int verifyShip(int mmsi, ArrayList<Ship> shipArray) {

        for (int i = 0; i < shipArray.size(); i++) {
            if (shipArray.get(i).getMmsi() == mmsi) {
                return i;
            }
        }

        return -1;
    }

    private static int newImo(String imo) {
        String temp = imo.substring(3, imo.length());
        return Integer.parseInt(temp);
    }


    public static ArrayList<Ship> readCSV() throws Exception {

        String path = "src/data/test.csv";

        DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        ArrayList<Ship> shipArray = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {

            //BufferedReader br = new BufferedReader(new FileReader(path));

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

                int index = verifyShip(Integer.parseInt(values[0]), shipArray);
                if (index == -1) { // se o barco não existir
                    int imo = newImo(values[8]);
                    int cargo = newCargo(values[14]);

                    Ship ship = new Ship(
                            Integer.parseInt(values[0]), // mmsi
                            values[7], // name
                            imo, // imo
                            values[9], // callsign
                            Integer.parseInt(values[10]), // vessel
                            Double.parseDouble(values[11]), // length
                            Double.parseDouble(values[12]), // width
                            Double.parseDouble(values[13]),// draft)
                            cargo);// cargo
                    ship.addDynamicShip(sd);
                    shipArray.add(ship);

                } else { // se o barco existir

                    shipArray.get(index).addDynamicShip(sd);

                }
            }
        }

        return shipArray;
    }

    BST<Ship> shipBST = new BST<>();

    for (Ship ship :shipArray) {
        shipBST.insert(ship);
    }


    private static int newCargo(String value) {
        if (value.equals("NA")) {
            return 0;
        }
        return Integer.parseInt(value);

    }
}