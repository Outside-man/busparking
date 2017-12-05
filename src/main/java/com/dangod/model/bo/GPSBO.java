package com.dangod.model.bo;

/**
 * Created by Yxm on 2017/11/23.
 */
public class GPSBO {
    private double X;
    private double Y;

    /**
     * 传入 xxxx,xxxxx
     * @param GPS
     */
    public GPSBO(String GPS) {
        String point[] = GPS.split(",");
        this.X = Double.parseDouble(point[0]);
        this.Y = Double.parseDouble(point[1]);
    }

    public GPSBO(double x, double y) {
        X = x;
        Y = y;
    }

    public double getX() {
        return X;
    }

    public void setX(double x) {
        X = x;
    }

    public double getY() {
        return Y;
    }

    public void setY(double y) {
        Y = y;
    }
}
