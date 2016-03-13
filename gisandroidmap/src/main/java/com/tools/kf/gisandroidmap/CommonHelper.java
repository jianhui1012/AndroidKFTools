package com.tools.kf.gisandroidmap;

import com.baidu.mapapi.model.LatLng;

/**
 * Created by djh on 2016/2/26.
 */
public class CommonHelper {

    private static final double EARTH_RADIUS = 6378137.0;

    // 返回单位是米
    public static double getDistance(LatLng p1, LatLng p2) {
        double Lat1 = rad(p1.latitude);
        double Lat2 = rad(p2.latitude);
        double a = Lat1 - Lat2;
        double b = rad(p1.longitude) - rad(p2.longitude);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(Lat1) * Math.cos(Lat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        return s;
    }

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }


}
