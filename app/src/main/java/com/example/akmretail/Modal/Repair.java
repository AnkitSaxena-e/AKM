package com.example.akmretail.Modal;

public class Repair {

    private  String Name, Number, UEN, Modal, Problem, PID, Status, Address;

    public Repair() {
    }

    public Repair(String name, String number, String UEN, String modal, String problem, String PID, String status, String address) {
        Name = name;
        Number = number;
        this.UEN = UEN;
        Modal = modal;
        Problem = problem;
        this.PID = PID;
        Status = status;
        Address = address;
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

    public String getModal() {
        return Modal;
    }

    public void setModal(String modal) {
        Modal = modal;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
