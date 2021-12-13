package lapr.project.model;

public class Border {

    private final Country country1;
    private final Country country2;

    public Border(Country country1, Country country2) {
        this.country1 = country1;
        this.country2 = country2;
    }

    public Country getCountry1() {
        return country1;
    }

    public Country getCountry2() {
        return country2;
    }
}
