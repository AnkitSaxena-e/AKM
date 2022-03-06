package com.example.akm_admin.Modal;

public class OrderDetailLayout {

    private String Date, Discount, Pdescription, Pname, Pimage, Pprice, Quantity, Time, Pid, Detail, SnR,  Info, Color, OrderNo;

    public OrderDetailLayout() {
    }

    public OrderDetailLayout(String date, String discount, String pdescription, String pname, String pimage, String pprice, String quantity, String time, String pid, String detail, String snR, String info, String color, String orderNo) {
        Date = date;
        Discount = discount;
        Pdescription = pdescription;
        Pname = pname;
        Pimage = pimage;
        Pprice = pprice;
        Quantity = quantity;
        Time = time;
        Pid = pid;
        Detail = detail;
        SnR = snR;
        Info = info;
        Color = color;
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
