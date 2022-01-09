package lapr.project.data;

import lapr.project.model.Country;
import lapr.project.model.Location;
import lapr.project.structures.Graph;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ColorMap {
    private List<Location> vertices;
    private List<Location> capitais;
    private Map<String, Integer> graphColor;
    private Map<Location, List<Location>> bordersMap;
    private List<Integer> colors;

    public ColorMap() {
        graphColor = new HashMap<>();
        colors = new ArrayList<>();
        capitais = new ArrayList<>();
    }

    public String getColoredMap(Graph<Location, Double> G) {
        vertices = G.vertices();
        getListOfCapitalVertices(vertices);
        bordersMap = getBorders(capitais, G);
        for (Location v : capitais) {
            fillListColors();
            List<Location> borderCountries = bordersMap.get(v);

            if (borderCountries == null) {
                graphColor.put(v.getCountryName(), colors.get(1));
            } else {
                for (int i = 0; i < borderCountries.size(); i++) {
                    Location l = borderCountries.get(i);
                    if (graphColor.get(l.getCountryName()) != null) {
                        colors.remove(graphColor.get(l.getCountryName()));
                    }
                }
                graphColor.put(v.getCountryName(), colors.get(1));
            }
        }
        return toStringMapColors();
    }

    public List<Location> getListOfCapitalVertices(List<Location> vertices) {
        for (Location l : vertices) {
            if (l.getClass().equals(Country.class)) {
                capitais.add(l);
            }
        }
        return capitais;
    }

    public Map<Location, List<Location>> getBorders(List<Location> capitais, Graph<Location, Double> G) {
        Map<Location, List<Location>> temp = new HashMap<Location, List<Location>>();
        for (int i = 0; i < capitais.size(); i++) {
            for (int j = i + 1; j < capitais.size(); j++) {
                if (G.edge(capitais.get(i), capitais.get(j)) != null) {
                    if (!temp.containsKey(capitais.get(i))) {
                        temp.put(capitais.get(i), new ArrayList<>());
                    }
                    if (!temp.containsKey(capitais.get(j))) {
                        temp.put(capitais.get(j), new ArrayList<>());
                    }
                    temp.get(capitais.get(i)).add(capitais.get(j));
                    temp.get(capitais.get(j)).add(capitais.get(i));
                }
            }
        }
        return temp;
    }

    public void fillListColors() {
        colors = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            colors.add(i);
        }
    }
    public String toStringMapColors() {
        String colorsMap = "";
        for (Location l : vertices)
            colorsMap += "Vértice: " + l + " \nCor: "
                    + graphColor.get(l.getCountryName()) + "\n\n";
        return colorsMap;
    }
}