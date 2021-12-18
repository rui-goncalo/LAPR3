package lapr.project.model;

import lapr.project.utils.CSVReaderUtils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BorderTest {

    ArrayList<Border> borderArrayList = CSVReaderUtils.readBordersCSV("data/borders.csv");

    Country country1 = borderArrayList.get(0).getCountry1();
    Country country2 = borderArrayList.get(0).getCountry2();

    @Test
    public void testGetCountry1() {
        assertEquals("Belize", country1.getName());
    }

    @Test
    public void testGetCountry2() {
        assertEquals("Mexico", country2.getName());
    }
}
