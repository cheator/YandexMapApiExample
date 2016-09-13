package ru.devcheat.YandexMapApiExample;
import ru.yandex.yandexmapkit.utils.GeoPoint;

import static java.lang.Math.sin;
import static java.lang.Math.asin;
import static java.lang.Math.sqrt;
import static java.lang.Math.cos;

import static java.lang.Math.toRadians;


public class MapsUtils {

    private MapsUtils(){};

    static final double EARTH_RADIUS = 6371009;
    /**
     * Returns the distance between two LatLngs, in meters.
     */
    public static double computeDistanceBetween(GeoPoint from, GeoPoint to) {
        return computeAngleBetween(from, to) * EARTH_RADIUS;
    }


    static double computeAngleBetween(GeoPoint from, GeoPoint to) {
        return distanceRadians(toRadians(from.getLat()), toRadians(from.getLon()),
                toRadians(to.getLat()), toRadians(to.getLon()));
    }
    /**
     * Returns distance on the unit sphere; the arguments are in radians.
     */
    private static double distanceRadians(double lat1, double lng1, double lat2, double lng2) {
        return arcHav(havDistance(lat1, lat2, lng1 - lng2));
    }
    /**
     * Computes inverse haversine. Has good numerical stability around 0.
     * arcHav(x) == acos(1 - 2 * x) == 2 * asin(sqrt(x)).
     * The argument must be in [0, 1], and the result is positive.
     */
    static double arcHav(double x) {
        return 2 * asin(sqrt(x));
    }


    /**
     * Returns hav() of distance from (lat1, lng1) to (lat2, lng2) on the unit sphere.
     */
    static double havDistance(double lat1, double lat2, double dLng) {
        return hav(lat1 - lat2) + hav(dLng) * cos(lat1) * cos(lat2);
    }

    /**
     * Returns haversine(angle-in-radians).
     * hav(x) == (1 - cos(x)) / 2 == sin(x / 2)^2.
     */
    static double hav(double x) {
        double sinHalf = sin(x * 0.5);
        return sinHalf * sinHalf;
    }
}
