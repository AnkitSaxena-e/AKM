package com.example.akm_admin.Modal;

public class RemoveOrder {

    String Address, City, Date, Name, OrderNo, PID, PhoneNumber, Status, Time, TotalAmount;

    public RemoveOrder() {
    }

    public RemoveOrder(String address, String city, String date, String name, String orderNo, String PID, String phoneNumber, String status, String time, String totalAmount) {
        Address = address;
        City = city;
        Date = date;
        Name = name;
        OrderNo = orderNo;
        this.PID = PID;
        PhoneNumber = phoneNumber;
        Status = status;
        Time = time;
        TotalAmount = totalAmount;
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
}
