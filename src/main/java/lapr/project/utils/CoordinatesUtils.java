package lapr.project.utils;

/**
 * @author Rui Gonçalves - 1191831
 */
public class CoordinatesUtils {

    /**
     * Private constructor of CoordinatesUtils.
     */
    private CoordinatesUtils() {
    }

    /**
     * Calculates distance between two latitude and longitude points and
     * convert it to a distance in meters. Uses Haversine method as its base.
     *
     * @param lat1 Start point.
     * @param lon1 Start point.
     * @param lat2 End point.
     * @param lon2 End point.
     * @return distance in Meters
     */
    public static double distance(double lat1, double lon1, double lat2, double lon2) {
        if ((lat1 == lat2) && (lon1 == lon2)) {
            return 0;
        } else {
            double theta = lon1 - lon2;
            double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;
            dist = dist * 1.609344;
            return (dist);
        }
    }
}
