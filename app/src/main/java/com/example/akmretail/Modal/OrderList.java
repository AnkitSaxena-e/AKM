package com.example.akmretail.Modal;

public class OrderList {

    private String Pid, Pname, Pdescription, Pprice, Pimage, SnR, ProductPID, OwnId, Color, Info, OrderNo, TTSS, Quantity, Detail, Time, Date;

    public OrderList() {
    }

    public OrderList(String pid, String pname, String pdescription, String pprice, String pimage, String snR, String productPID,
                     String ownId, String color, String info, String orderNo, String TTSS, String quantity, String detail, String time,
                     String date) {
        Pid = pid;
        Pname = pname;
        Pdescription = pdescription;
        Pprice = pprice;
        Pimage = pimage;
        SnR = snR;
        ProductPID = productPID;
        OwnId = ownId;
        Color = color;
        Info = info;
        OrderNo = orderNo;
        this.TTSS = TTSS;
        Quantity = quantity;
        Detail = detail;
        Time = time;
        Date = date;
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

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
