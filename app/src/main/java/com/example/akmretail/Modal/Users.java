package com.example.akmretail.Modal;

public class Users {

    private String Name, Number, Passward, Image, Address, Pin, Suspend, Token, UID;

    public Users() {
    }

    public Users(String name, String number, String passward, String image, String address, String pin, String suspend, String token, String UID) {
        Name = name;
        Number = number;
        Passward = passward;
        Image = image;
        Address = address;
        Pin = pin;
        Suspend = suspend;
        Token = token;
        this.UID = UID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getPassward() {
        return Passward;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
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

    public void setPin(String pin) {
        Pin = pin;
    }

    public String getSuspend() {
        return Suspend;
    }
}
