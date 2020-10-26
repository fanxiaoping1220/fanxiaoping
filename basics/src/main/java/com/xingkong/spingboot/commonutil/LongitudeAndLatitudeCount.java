package com.xingkong.spingboot.commonutil;

import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GeodeticCurve;
import org.gavaghan.geodesy.GlobalCoordinates;

/**
 * * @className: LongitudeAndLatitudeCount
 * * @description: 计算2个经纬度之间的距离
 * * @author: fanxiaoping
 * * @date: 2020-06-15  10:33
 **/
public class LongitudeAndLatitudeCount {

    public static void main(String[] args){
        GlobalCoordinates source = new GlobalCoordinates( 24.46452179999999998472048901021480560302734375,118.0667710999999968635165714658796787261962890625);
        GlobalCoordinates target = new GlobalCoordinates(24.4626940,118.0708532);

//        double meter1 = getDistanceMeter(source, target, Ellipsoid.Sphere);
        double meter2 = getDistanceMeter(source, target, Ellipsoid.WGS84);
//        System.out.println("Sphere坐标系计算结果："+meter1 + "米");
        System.out.println("WGS84坐标系计算结果："+meter2 + "米");
    }

    public static double getDistanceMeter(GlobalCoordinates gpsFrom, GlobalCoordinates gpsTo, Ellipsoid ellipsoid){
        //创建GeodeticCalculator，调用计算方法，传入坐标系、经纬度用于计算距离
        GeodeticCurve geoCurve = new GeodeticCalculator().calculateGeodeticCurve(ellipsoid, gpsFrom, gpsTo);
        return geoCurve.getEllipsoidalDistance();
    }
}