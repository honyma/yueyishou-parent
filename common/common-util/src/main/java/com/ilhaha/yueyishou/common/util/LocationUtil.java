package com.ilhaha.yueyishou.common.util;

import com.ilhaha.yueyishou.common.constant.PublicConstant;
import org.springframework.data.geo.Point;

public class LocationUtil {

    // 地球赤道半径
    private static double EARTH_RADIUS = 6378.137;

    //等同——Math.toRadians()
    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * @描述 经纬度获取距离，单位为米
     * @参数 [lat1, lng1, lat2, lng2]
     * @返回值 double
     **/
    public static double getDistance(double lat1, double lng1, double lat2,
                                     double lng2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000d) / 10000d;
        s = s * 1000;
        return s;
    }

    /**
     * 使用 Haversine 公式计算两个经纬度之间的距离
     *
     * @param point1 第一个位置 (经纬度)
     * @param point2 第二个位置 (经纬度)
     * @return 距离，单位为公里
     */
    public static double calculateDistance(Point point1, Point point2) {

        double lat1 = Math.toRadians(point1.getY());
        double lon1 = Math.toRadians(point1.getX());
        double lat2 = Math.toRadians(point2.getY());
        double lon2 = Math.toRadians(point2.getX());

        double dlat = lat2 - lat1;
        double dlon = lon2 - lon1;

        double a = Math.sin(dlat / 2) * Math.sin(dlat / 2) +
                Math.cos(lat1) * Math.cos(lat2) *
                        Math.sin(dlon / 2) * Math.sin(dlon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return PublicConstant.EARTH_RADIUS * c;  // 返回距离，单位：公里
    }

    public static void main(String[] args) {
        double distance = getDistance(30.57404, 104.073013,
                30.509376, 104.077001);
        System.out.println("距离" + distance + "米");
    }

}
