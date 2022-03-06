package com.example.akm_admin.Modal;

public class OPro {

    private String PName, PNum, PVar, PCom, PPri, PQut, PImage, PPid;

    public OPro() {
    }

    public OPro(String PName, String PNum, String PVar, String PCom, String PPri, String pQut, String pImage, String pPid) {
        this.PName = PName;
        this.PNum = PNum;
        this.PVar = PVar;
        this.PCom = PCom;
        this.PPri = PPri;
        PQut = pQut;
        PImage = pImage;
        PPid = pPid;
    }

    public String getPName() {
        return PName;
    }

    public String getPNum() {
        return PNum;
    }

    public String getPVar() {
        return PVar;
    }

    public String getPCom() {
        return PCom;
    }

    public String getPPri() {
        return PPri;
    }

    public String getPQut() {
        return PQut;
    }

    public String getPImage() {
        return PImage;
    }

}
