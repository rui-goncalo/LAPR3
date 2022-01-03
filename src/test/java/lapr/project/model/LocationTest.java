package lapr.project.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class LocationTest {
    private final Location l1;
    private final Location l2;

    public LocationTest(){
        l1 = new Location("111","111",23.4, 74.5);
        l2 = new Location("222", "222");
    }

    @Test
    void getLongitude() {
        double criada = 74.5;
        double esperada = l1.getLongitude();
        assertEquals(criada, esperada);
    }
    @Test
    void getLatitude() {
        double criada = 23.4;
        double esperada = l1.getLatitude();
        assertEquals(criada, esperada);
    }
    @Test
    void getName() {
        String criada = "111";
        String esperada = l1.getName();
        assertEquals(criada, esperada);
    }

    @Test
    void getCountryName() {
        String criada = "111";
        String esperada = l1.getCountryName();
        assertEquals(criada, esperada);
    }
    @Test
    void testToString() {
        String expRes ="Location{" +
                "name='" + l1.getName() + '\'' +
                ", countryName='" + l1.getCountryName() + '\'' +
                '}';
        assertEquals(expRes, l1.toString());
    }

    @Test
    void testEquals() {
        assertTrue(l1.equals(l1));

    }

    @Test
    void testHashCode() {
        assertEquals(1650424225,l1.hashCode());
    }
}