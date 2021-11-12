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

public final class CSVReaderUtils {

    private CSVReaderUtils() {

    }

    public static ArrayList<Ship> readCSV(String path) throws Exception {
        ArrayList<Ship> shipArray = new ArrayList<>();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                int index = shipExists(shipArray, Integer.parseInt(values[0]));

                ShipData newDynamic = new ShipData(LocalDateTime.parse(values[1], format),  //baseDateTime
                        Float.parseFloat(values[2]),  //lat
                        Float.parseFloat(values[3]),  //lon
                        Float.parseFloat(values[4]),  //sog
                        Float.parseFloat(values[5]),  //cog
                        Integer.parseInt(values[6]),  //heading
                        values[15].charAt(0));        //transceiverClass

                if (index != -1) {
                    shipArray.get(index).addDynamicShip(newDynamic);
                } else {
                    Ship newShip = new Ship(Integer.parseInt(values[0]),  // mmsi
                            new ArrayList<>(),                              // dynamic data
                            values[7],                                    // shipName
                            Integer.parseInt(values[8].substring(3)),     // imoCode
                            values[9],                                    // callSign
                            Integer.parseInt(values[10]),                 // vesselType
                            Float.parseFloat(values[11]),                 // length
                            Float.parseFloat(values[12]),                 // width
                            Float.parseFloat(values[13]),                 // draft
                            cargoParser(values[14]));                     // cargo

                    newShip.addDynamicShip(newDynamic);
                    shipArray.add(newShip);
                }
            }
            return shipArray;
        }
    }

    private static int shipExists(ArrayList<Ship> shipArray, int mmsi) {
        for (int i = 0; i < shipArray.size(); i++) {
            if (shipArray.get(i).getMmsi() == mmsi) {
                return i;
            }
        }
        return -1;
    }

    private static int cargoParser(String value) {
        if (value.equals("NA"))
            return 0;
        else
            return Integer.parseInt(value);
    }


}