package lapr.project.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CountryTest {

    Country newCountry = new Country(1,
            "Germany",
            "DE",
            "DEU",
            "Europe",
            "Berlin",
            82.8,
            52.51666667,
            13.4);

    @Test
    public void testGetId() {
        assertEquals(1, newCountry.getId());
    }

    @Test
    public void testGetName() {
        assertEquals("Germany", newCountry.getName());
    }

    @Test
    public void testGetAlpha2() {
        assertEquals("DE", newCountry.getAlpha2());
    }

    @Test
    public void testGetAlpha3() {
        assertEquals("DEU", newCountry.getAlpha3());
    }

    @Test
    public void testGetContinent() {
        assertEquals("Europe", newCountry.getContinent());
    }

    @Test
    public void testGetPopulation() {
        assertEquals(82.8, newCountry.getPopulation());
    }

    @Test
    public void testGetLatitude() {
        assertEquals(52.51666667, newCountry.getLatitude());
    }

    @Test
    public void testGetLongitude() {
        assertEquals(13.4, newCountry.getLongitude());
    }
}
