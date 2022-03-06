package com.example.akmuser.Modal;

public class Cart {

    private String Date, Discount, PDescription, PName, PPrice, Quantity, Time, pid;

    public Cart() {
    }

    public Cart(String date, String discount, String PDescription, String PName, String PPrice, String quantity, String time, String pid) {
        Date = date;
        Discount = discount;
        this.PDescription = PDescription;
        this.PName = PName;
        this.PPrice = PPrice;
        Quantity = quantity;
        Time = time;
        this.pid = pid;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }
}