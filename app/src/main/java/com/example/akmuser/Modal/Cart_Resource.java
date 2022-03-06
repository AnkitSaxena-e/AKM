package com.example.akmuser.Modal;

public class Cart_Resource {

    private String Date, Discount, Pdescription, Pname, Pimage, Pprice, Rating, Quantity, Time, Pid, Detail, Color, SnR, Info, OrderNo;

    public Cart_Resource() {
    }

    public Cart_Resource(String date, String discount, String pdescription, String pname, String pimage, String pprice,
                         String rating, String quantity, String time, String pid, String detail, String color, String snR, String info, String orderNo) {
        Date = date;
        Discount = discount;
        Pdescription = pdescription;
        Pname = pname;
        Pimage = pimage;
        Pprice = pprice;
        Rating = rating;
        Quantity = quantity;
        Time = time;
        Pid = pid;
        Detail = detail;
        Color = color;
        SnR = snR;
        Info = info;
        OrderNo = orderNo;
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
        return Pid;
    }

    public void setPid(String pid) {
        Pid = pid;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }
}
