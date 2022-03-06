package com.example.akm_admin.Modal;

public class ProductPhone {

    private String Pid, Date, Time, Description, Price, TName, TDetail, TBrand, Keyward, Color1, Color2, Color3, Color4, Color5, Color6, Color7,
    Color8, Color9, Color10, SearchP, SnR1, SnR2, SnR3, SnR4, SnR5, SnR6, SnR7, SnR8, SnR9, SnR10, NOCo, NOSnR, S1, S2, S3, S4, S5, Image1, Image2, Image3,
    Image4, Image5, ProductName;

    public ProductPhone() {
    }

    public ProductPhone(String pid, String date, String time, String description, String price, String TName, String TDetail, String TBrand,
                        String keyward, String color1, String color2, String color3, String color4, String color5, String color6, String color7,
                        String color8, String color9, String color10, String searchP, String snR1, String snR2, String snR3, String snR4,
                        String snR5, String snR6, String snR7, String snR8, String snR9, String snR10, String NOCo, String NOSnR, String s1,
                        String s2, String s3, String s4, String s5, String image1, String image2, String image3, String image4, String image5,
                        String productName) {
        Pid = pid;
        Date = date;
        Time = time;
        Description = description;
        Price = price;
        this.TName = TName;
        this.TDetail = TDetail;
        this.TBrand = TBrand;
        Keyward = keyward;
        Color1 = color1;
        Color2 = color2;
        Color3 = color3;
        Color4 = color4;
        Color5 = color5;
        Color6 = color6;
        Color7 = color7;
        Color8 = color8;
        Color9 = color9;
        Color10 = color10;
        SearchP = searchP;
        SnR1 = snR1;
        SnR2 = snR2;
        SnR3 = snR3;
        SnR4 = snR4;
        SnR5 = snR5;
        SnR6 = snR6;
        SnR7 = snR7;
        SnR8 = snR8;
        SnR9 = snR9;
        SnR10 = snR10;
        this.NOCo = NOCo;
        this.NOSnR = NOSnR;
        S1 = s1;
        S2 = s2;
        S3 = s3;
        S4 = s4;
        S5 = s5;
        Image1 = image1;
        Image2 = image2;
        Image3 = image3;
        Image4 = image4;
        Image5 = image5;
        ProductName = productName;
    }

    public String getPid() {
        return Pid;
    }

    public void setPid(String pid) {
        Pid = pid;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getImage1() {
        return Image1;
    }

    public void setImage1(String image1) {
        Image1 = image1;
    }

    public String getImage2() {
        return Image2;
    }

    public void setImage2(String image2) {
        Image2 = image2;
    }
}
