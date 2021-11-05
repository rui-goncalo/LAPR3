package lapr.project.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Date;

public class CSVReader {

    private static String path = "src/data/sships.csv";
    private static String line = "";

    public static ArrayList<ArrayList<ShipData>> readCSV() {
        ArrayList<ArrayList<ShipData>> shipDataList = new ArrayList<ArrayList<ShipData>>();
        ArrayList<ShipData> shipData = new ArrayList<ShipData>();
        String mmsi = "";
        String oldMmsi = "";
        try {
            BufferedReader br = null;
            br = new BufferedReader(new FileReader(path));
            while ((line = br.readLine()) != null) {
                //System.out.println(line);
                oldMmsi = mmsi;
                String[] values = line.split(",");
                mmsi = values[0];
                String[] dateTime = values[1].split(" ");
                String[] date = dateTime[0].split("/");
                String[] time = dateTime[1].split(":");
                Date shipDate = new Date(Integer.parseInt(date[2]), Integer.parseInt(date[1]),
                        Integer.parseInt(date[0]), Integer.parseInt(time[0]), Integer.parseInt(time[1]));

                ShipData ship = new ShipData(Integer.parseInt(values[0]), shipDate, Double.parseDouble(values[2]),
                        Double.parseDouble(values[3]), Double.parseDouble(values[4]), Double.parseDouble(values[5]),
                        Double.parseDouble(values[6]), values[7], values[8], values[9], values[10],
                        Double.parseDouble(values[11]), Double.parseDouble(values[12]), Double.parseDouble(values[13]),
                        values[14], values[15].charAt(0));
                //System.out.println(values[2]);
                if (shipData.isEmpty() || mmsi.equals(oldMmsi)) {
                    shipData.add(ship);
                } else {
                    shipDataList.add(shipData);
                    shipData = new ArrayList<ShipData>();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return shipDataList;
    }

}
