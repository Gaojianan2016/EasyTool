package com.gjn.easytool.utils;

/**
 * @author gjn
 * @time 2018/12/24 11:39
 */

public class DistanceUtils {

    public static double getDistance(double startLongitude, double startLatitude,
                                     double endLongitude, double endLatitude) {
        double lon1 = (Math.PI / 180) * startLongitude;
        double lon2 = (Math.PI / 180) * endLongitude;
        double lat1 = (Math.PI / 180) * startLatitude;
        double lat2 = (Math.PI / 180) * endLatitude;
        // 地球半径
        double R = 6371;
        // 两点间距离 m
        return Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon2 - lon1)) * R * 1000;
    }

}
