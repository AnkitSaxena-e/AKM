package com.example.akm_admin.Modal;

public class SearchCategoryModal {

    private String Name, Address, Pin, State, Number, City, PID;

    public SearchCategoryModal() {
    }

    public SearchCategoryModal(String name, String address, String pin, String state, String number, String city, String PID) {
        Name = name;
        Address = address;
        Pin = pin;
        State = state;
        Number = number;
        City = city;
        this.PID = PID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPin() {
        return Pin;
    }

    public String getState() {
        return State;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getCity() {
        return City;
    }
}
