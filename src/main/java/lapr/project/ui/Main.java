package lapr.project.ui;
import lapr.project.model.CSVReader;
import lapr.project.model.Ship;
import lapr.project.model.ShipData;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;


class Main {
    public static void main(String[] args) throws Exception {

//        Ship ship = new Ship(210950000, "Navio 1", 123, 3, 380.0,
//                4, 2, 2.5, 4.2, 50, 20.0, 97.5, 56.4,
//                21.5, 26.6, 20.0, 15, "Transceiver");
//
//        System.out.println(ship.getLatitude());
//        System.out.println(ship.getLongitude());
//        System.out.println(ship.getSog());
//        System.out.println(ship.getCog());
//        System.out.println(ship.getHeading());
//
//        ArrayList<String> lista = new ArrayList<>();
//        File file = new File("src/data/test.csv");
//        Scanner scanner = new Scanner(file);
//        while (scanner.hasNextLine()) {
//            lista.add(scanner.nextLine());
//        }
//
//        System.out.println(lista.get(0));
//        String[] linha1 = lista.get(0).split(",");
//        System.out.println(linha1[7]);
//        ship.setLatitude(Double.parseDouble(linha1[2]));
//        ship.setLongitude(Double.parseDouble(linha1[3]));
//        ship.setSog(Double.parseDouble(linha1[4]));
//        ship.setCog(Double.parseDouble(linha1[5]));
//        ship.setHeading(Double.parseDouble(linha1[6]));
//        ship.setTransceiverClass(linha1[15]);

        ArrayList<ArrayList<ShipData>> shipDataList = CSVReader.readCSV();
        for (int i = 0; i < shipDataList.get(0).size(); i++) {
            //for (int j = 0; j < shipDataList.get(i).size(); j++) {
                System.out.println(shipDataList.get(0).get(i).getLatitude());
            //}
        }

        }
    }


