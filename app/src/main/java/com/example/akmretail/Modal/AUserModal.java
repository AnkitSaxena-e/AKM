package com.example.akmretail.Modal;

public class AUserModal {

    private String Number, Name;

    public AUserModal() {
    }

    public AUserModal(String number, String name) {
        Number = number;
        Name = name;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
