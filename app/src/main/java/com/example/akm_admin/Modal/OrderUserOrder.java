package com.example.akm_admin.Modal;

public class OrderUserOrder {

    private String Address, City, Date, Name, PhoneNumber, Status, Time, Check, Type, AdminRe, NamePrint, TotalAmount, Return, DeliveryDetail, PID, Pin, OrderNo, ImageURI, ONO;

    public OrderUserOrder() {
    }

    public OrderUserOrder(String address, String city, String date, String name, String phoneNumber, String status, String time, String check, String type, String adminRe, String namePrint, String totalAmount, String aReturn, String deliveryDetail, String PID, String pin, String orderNo, String imageURI, String ONO) {
        Address = address;
        City = city;
        Date = date;
        Name = name;
        PhoneNumber = phoneNumber;
        Status = status;
        Time = time;
        Check = check;
        Type = type;
        AdminRe = adminRe;
        NamePrint = namePrint;
        TotalAmount = totalAmount;
        Return = aReturn;
        DeliveryDetail = deliveryDetail;
        this.PID = PID;
        Pin = pin;
        OrderNo = orderNo;
        ImageURI = imageURI;
        this.ONO = ONO;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getReturn() {
        return Return;
    }

    public void setReturn(String aReturn) {
        Return = aReturn;
    }
}