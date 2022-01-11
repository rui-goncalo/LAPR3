package lapr.project.utils;

import lapr.project.model.*;

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

    private static final String COUNTRIES_FILE = "data/countries.csv";

    /**
     * Private constructor of CSVReaderUtils.
     */
    private CSVReaderUtils() {
    }

    /**
     * Reads a CSV file and creates an ArrayList of ships.
     *
     * @param path - CSV file.
     * @return an ArrayList filled with ships and their dynamic data.
     * @throws Exception if the file path doesn't exist.
     */
    public static ArrayList<Ship> readShipCSV(String path) throws Exception {

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
     * Reads a CSV file and creates an ArrayList of Ports.
     *
     * @param path - CSV file.
     * @return an ArrayList filled with ports data.
     * @throws Exception if the file path doesn't exist.
     */
    public static ArrayList<Port> readPortCSV(String path) {
        ArrayList<Port> portArrayList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line = br.readLine();

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                Port newPort = new Port(values[0],
                        values[1],
                        Integer.parseInt(values[2]),
                        values[3],
                        Double.parseDouble(values[4]),
                        Double.parseDouble(values[5]));

                portArrayList.add(newPort);
            }
            return portArrayList;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    /**
     * Reads a CSV file and creates an ArrayList of Countries.
     * Note: Added an ID for each Country.
     *
     * @param path - CSV file.
     * @return an ArrayList filled with countries data.
     * @throws Exception if the file path doesn't exist.
     */
    public static ArrayList<Country> readCountryCSV(String path) {
        ArrayList<Country> countryArrayList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line = br.readLine();
            int counter = 0; // Country id counter
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                Country newCountry = new Country(
                        counter,
                        values[3],
                        values[1],
                        values[2],
                        values[0],
                        values[5],
                        Double.parseDouble(values[4]),
                        Double.parseDouble(values[6]),
                        Double.parseDouble(values[7]));

                countryArrayList.add(newCountry);
                counter++;
            }
            return countryArrayList;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    /**
     * Reads a CSV file and creates an ArrayList of Borders.
     *
     * @param path - CSV file.
     * @return an ArrayList filled with borders data.
     * @throws Exception if the file path doesn't exist.
     */
    public static ArrayList<Border> readBordersCSV(String path) {
        ArrayList<Border> borderArrayList = new ArrayList<>();
        ArrayList<Country> countryArrayList = readCountryCSV(COUNTRIES_FILE);

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                Country country1 = null;
                Country country2 = null;
                for (Country country : countryArrayList) {

                    if (country.getName().equals(values[0])) {
                        country1 = country;
                    }
                    if (country.getName().equals(values[1].replaceFirst(" ", ""))) {
                        country2 = country;

                    }
                }

                Border newBorder = new Border(country1, country2);
                borderArrayList.add(newBorder);
            }
            return borderArrayList;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    /**
     * Reads a CSV file and creates an ArrayList of Seadists.
     *
     * @param path - CSV file.
     * @return an ArrayList filled with sea distances data.
     * @throws Exception if the file path doesn't exist.
     */
    public static ArrayList<Seadist> readSeadistsCSV(String path) {
        ArrayList<Seadist> seadistArrayList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                Seadist newSeadist = new Seadist(
                        values[0],
                        Integer.parseInt(values[1]),
                        values[2],
                        values[3],
                        Integer.parseInt(values[4]),
                        values[5],
                        Integer.parseInt(values[6]));
                seadistArrayList.add(newSeadist);
            }
            return seadistArrayList;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    /**
     * Verify if ship exists inside an Arraylist - From mmsi.
     *
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
     *
     * @param imo is the value to be parsed.
     * @return the imo converted into an Integer or return 0.
     */
    private static int newImo(String imo) {
        String temp = imo.substring(3, imo.length());
        return Integer.parseInt(temp);
    }

    /**
     * Given a string of cargo value and converts it into an Integer.
     *
     * @param value is the value to be parsed.
     * @return the String converted into an Integer or return 0.
     */
    private static int newCargo(String value) {
        if (value.equals("NA")) {
            return 0;
        }
        return Integer.parseInt(value);
    }

    /**
     * Given an ArrayList of ships arranges ships from the oldest
     *
     * record to the most recent record.
     * @param shipArray the ArrayList that will be sort by date.
     * @return an ArrayList of Ships sorted by date.
     */
    public static ArrayList<Ship> sortByDate(ArrayList<Ship> shipArray) {

        for (int i = 0; i < shipArray.size(); i++) {
            ArrayList<ShipData> sortedArray = (ArrayList<ShipData>) shipArray.get(i).getDynamicShip().stream()
                    .sorted(Comparator.comparing(ShipData::getDateTime).reversed())
                    .collect(Collectors.toList());

            shipArray.get(i).setDynamicShip(sortedArray);
        }

        return shipArray;
    }

}