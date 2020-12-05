package com.eb.appdemo.entidades;

public class DataPoint {

    private int xValue;
    private float yValue;

    public DataPoint(int xValue, float yValue) {
        this.xValue = xValue;
        this.yValue = yValue;
    }

    public DataPoint() {
    }

    public int getxValue() {
        return xValue;
    }

    public void setxValue(int xValue) {
        this.xValue = xValue;
    }

    public float getyValue() {
        return yValue;
    }

    public void setyValue(float yValue) {
        this.yValue = yValue;
    }

    @Override
    public String toString() {
        return "DataPoint{" +
                "xValue=" + xValue +
                ", yValue=" + yValue +
                '}';
    }
}
