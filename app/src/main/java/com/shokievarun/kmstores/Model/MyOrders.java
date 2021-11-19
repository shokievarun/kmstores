package com.shokievarun.kmstores.Model;

public class MyOrders {

    private String dateTime;

    public MyOrders() {
    }

    public MyOrders(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
