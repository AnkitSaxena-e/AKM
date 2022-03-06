package com.example.akmuser.Modal;


public class SuggModal {

    String Suggetion, Name, Number;

    public SuggModal() {
    }

    public SuggModal(String suggetion, String name, String number) {
        Suggetion = suggetion;
        Name = name;
        Number = number;
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
}
