package lapr.project.utils;

import lapr.project.model.Ship;
import lapr.project.model.ShipData;


import java.util.ArrayList;

public final class Calculator {
    
    private static final double MINUTES_IN_A_DEGREE = 60;
    private static final double NAUTICAL_MILES_TO_STATUTE_MILES = 1.1515;
    private static final double STATUTE_MILES_TO_KILOMETERS = 1.609344;
    
    /**
     * Calculates distance between two latitude and longitude points and
     * convert it to a distance in kilometers. Uses Haversine method as its base.
     *
     * @param lat1 Start point.
     * @param lon1 Start point.
     * @param lat2 End point.
     * @param lon2 End point.
     * @return distance in Km's.
     */
    public static double getDistance(double lat1, double lon1, double lat2, double lon2) {
        if (lat1 == lat2 && lon1 == lon2)
            return 0;
        else {
            double theta = lon1 - lon2;
            double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * MINUTES_IN_A_DEGREE * NAUTICAL_MILES_TO_STATUTE_MILES;
            dist = dist * STATUTE_MILES_TO_KILOMETERS;
            return dist;
        }
    }

    /**
     * Nested static class for a pair of ships.
     */
    public static class ShipPair {
        private final Ship firstShip;
        private final Ship secondShip;

        public ShipPair(Ship firstShip, Ship secondShip) {
            this.firstShip = firstShip;
            this.secondShip = secondShip;
        }

        public Ship getFirstShip() {
            return firstShip;
        }

        public Ship getSecondShip() {
            return secondShip;
        }
    }

    public static class ContainerInfo {
        private String container;
        private double xxCm;
        private double yyCm;
        private double xxIn;
        private double xxFin;
        private double yyIn;
        private double yyFin;
        private double height;

        @Override
        public String toString() {
            return  container +"\n" +
                    "Center of Mass XX: " + xxCm +
                    ", Center of Mass YY: " + yyCm + "\n  Measurements \n" +
                    " Initial Position XX:" + xxIn +
                    ", Initial Position YY:" + yyIn + "\n" +
                    " Final Position XX:" + xxFin +
                    ", Final Position YY: " + yyFin + "\n" +
                    " Height=" + height + "\n" ;
        }

        public ContainerInfo(String container, double xxCm, double yyCm, double xxIn, double xxFin, double yyIn, double yyFin, double height)
        {
            this.container = container;
            this.xxCm = xxCm;
            this.yyCm = yyCm;
            this.xxIn = xxIn;
            this.xxFin = xxFin;
            this.yyIn = yyIn;
            this.yyFin = yyFin;
            this.height = height;

        }

        public double getXxCm() {
            return xxCm;
        }

        public double getYyCm() {
            return yyCm;
        }

        public double getXxIn() {
            return xxIn;
        }

        public double getXxFin() {
            return xxFin;
        }

        public double getYyIn() {
            return yyIn;
        }

        public double getYyFin() {
            return yyFin;
        }

        public double getHeight()
        {
            return height;
        }
        public String getContainer() {
            return container;
        }


    }

    public static ArrayList<ContainerInfo> calculateContainersPosition(int nContainers, double contHeight, double contLength,double contWidth, double s_length, double s_width)
    {
        int MaxContStacked = 7;
        double height = 0;
        double xxSCont = s_length /2;
        double yySCont = s_width /2;
        double cont1CmXX = contLength/2;
        double cont1CmYY = contWidth/2;
        double cont1InXX = 0;
        double cont1InYY = 0;
        double cont1FinXX = contLength;
        double cont1FinYY = contWidth;

        double cont2CmXX = s_length - contLength/2;
        double cont2CmYY = s_width - contWidth/2;
        double cont2InXX = s_length - contLength;
        double cont2InYY = s_width;
        double cont2FinXX = s_length;
        double cont2FinYY = s_width - contWidth;
        int n = 1;

        double midWidth = s_width/2;

        ArrayList<ContainerInfo> containerInfos = new ArrayList<ContainerInfo>();

        //If nContainers is odd, one container should be in the middle of the ship to be in balance
        if(nContainers % 2 ==1)
        {
            containerInfos.add(new ContainerInfo("container"+n,xxSCont,yySCont,xxSCont-contLength/2,xxSCont+contLength/2,yySCont-contWidth/2,yySCont+contWidth/2,height));
            nContainers--;
            n++;

            midWidth-=contWidth/2;
        }

        while(nContainers>0)
        {
                // Adds containers in pais, opposite sides so that the center of mass remains in the middle of the rectangle

                containerInfos.add(new ContainerInfo("container"+n,cont1CmXX,cont1CmYY,cont1InXX,cont1FinXX,cont1InYY,cont1FinYY,height));
                cont1CmXX += contLength;
                cont1InXX +=contLength;
                cont1FinXX +=contLength;
                nContainers--;
                n++;

                containerInfos.add(new ContainerInfo("container"+n,cont2CmXX,cont2CmYY,cont2InXX,cont2FinXX,cont2InYY,cont2FinYY,height));
                cont2CmXX -= contLength;
                cont2InXX -=contLength;
                cont2FinXX -=contLength;
                nContainers--;
                n++;
                //Verify if is possible to add another container in the row , if not verifies it its within the possible width
                if(cont1FinXX + contLength > s_length)
                {
                    //If it is within the possible width, increments width and changes pos of XX to initial position
                    if(cont1FinYY + contWidth < midWidth){
                        cont1CmYY += contWidth;
                        cont1InYY += contWidth;
                        cont1FinYY += contWidth;
                        cont1CmXX = contLength/2;
                        cont1InXX = 0;
                        cont1FinXX = contLength;

                        cont2CmYY -= contWidth;
                        cont2InYY -= contWidth;
                        cont2FinYY -= contWidth;
                        cont2CmXX = s_length - contLength/2;
                        cont2InXX = s_length - contLength;
                        cont2FinXX = s_length;
                    } else {
                        //If its not possible, starts to stack containers on top of each other ( MAX 8)
                        if(MaxContStacked >0)
                        {
                            height += contHeight;
                            cont1CmXX = contLength/2;
                            cont1CmYY = contWidth/2;
                            cont1InXX = 0;
                            cont1InYY = 0;
                            cont1FinXX = contLength;
                            cont1FinYY = contWidth;

                            cont2CmXX = s_length - contLength/2;
                            cont2CmYY = s_width - contWidth/2;
                            cont2InXX = s_length - contLength;
                            cont2InYY = s_width;
                            cont2FinXX = s_length;
                            cont2FinYY = s_width - contWidth;
                            MaxContStacked--;
                            //If there is still containers to add, its impossible to have that many containers in that ship, so it returns null
                        } else {
                            return null;
                        }
                    }
                }
        }


        return containerInfos;
    }



    /**
     * Given the pairs of ships, check the combination of
     * ships with arrival/departures less than 5km's within
     * an ArrayList
     *
     * @param shipArray the list to use during the search
     * @return Pairs of ships with arrival/departure nearest (both less than 5km's)
     */
    public static ArrayList<ShipPair> searchShipPairs(ArrayList<Ship> shipArray) {
        ArrayList<Ship> shipCandidates = new ArrayList<>();

        for (Ship ship : shipArray) {
            ShipData firstDynamic = ship.getFirstDynamicData();
            ShipData lastDynamic = ship.getLastDynamicData();

            double travelledDistance = getDistance(firstDynamic.getLatitude(),
                    firstDynamic.getLongitude(),
                    lastDynamic.getLatitude(),
                    lastDynamic.getLongitude());

            if (travelledDistance > 10)
                shipCandidates.add(ship);
        }

        ArrayList<ShipPair> shipPairs = new ArrayList<>();

        for (int i = 0; i < shipCandidates.size() - 1; i++) {
            for (int j = i + 1; j < shipCandidates.size(); j++) {
                Ship currentShip = shipCandidates.get(i);
                Ship nextShip = shipCandidates.get(j);

                ShipData currentFirstDynamic = currentShip.getFirstDynamicData();
                ShipData currentLastDynamic = currentShip.getLastDynamicData();

                ShipData nextFirstDynamic = nextShip.getFirstDynamicData();
                ShipData nextLastDynamic = nextShip.getLastDynamicData();

                double departureDistance = getDistance(currentFirstDynamic.getLatitude(),
                        currentFirstDynamic.getLongitude(),
                        nextFirstDynamic.getLatitude(),
                        nextFirstDynamic.getLongitude());

                double arrivalDistance = getDistance(currentLastDynamic.getLatitude(),
                        currentLastDynamic.getLongitude(),
                        nextLastDynamic.getLatitude(),
                        nextLastDynamic.getLongitude());

                if (departureDistance < 5 || arrivalDistance < 5)
                    shipPairs.add(new ShipPair(currentShip, nextShip));
            }
        }
        return shipPairs;
    }

}
