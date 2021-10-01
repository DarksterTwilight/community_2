package com.example.practicum3;

public class HelperClass {
    private double my_latitude,my_longitude;
    public HelperClass(){

    }

    public HelperClass(double my_latitude, double my_longitude) {
        this.my_latitude = my_latitude;
        this.my_longitude = my_longitude;
    }

    public double getMy_latitude() {
        return my_latitude;
    }

    public void setMy_latitude(double my_latitude) {
        this.my_latitude = my_latitude;
    }

    public double getMy_longitude() {
        return my_longitude;
    }

    public void setMy_longitude(double my_longitude) {
        this.my_longitude = my_longitude;
    }


}
