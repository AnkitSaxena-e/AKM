package com.example.akmretail.Modal;

public class Product {

    private String Pid, Date, Time, Description, Price, TName, TDetail, TBrand, Keyward, Company, No1, No2, No3, No4, No5, No6, No7,
            No8, No9, No10, Adult, Child, Category, Quantity, S1, S2, S3, S4, S5, Image1, Image2, Image3, Image4, Image5, ProductName,
            No1Price, No2Price, No3Price, No4Price, No5Price, No6Price, No7Price, No8Price, No9Price, No10Price, AdultPrice, ChildPrice;

    public Product() {
    }

    public Product(String pid, String date, String time, String description, String price, String TName, String TDetail, String TBrand,
                   String keyward, String company, String no1, String no2, String no3, String no4, String no5, String no6, String no7,
                   String no8, String no9, String no10, String adult, String child, String category, String price1, String quantity,
                   String s1, String s2, String s3, String s4, String s5, String image1, String image2, String image3, String image4,
                   String image5, String productName, String no1Price, String no2Price, String no3Price, String no4Price, String no5Price,
                   String no6Price, String no7Price, String no8Price, String no9Price, String no10Price, String adultPrice, String childPrice) {
        Pid = pid;
        Date = date;
        Time = time;
        Description = description;
        Price = price;
        this.TName = TName;
        this.TDetail = TDetail;
        this.TBrand = TBrand;
        Keyward = keyward;
        Company = company;
        No1 = no1;
        No2 = no2;
        No3 = no3;
        No4 = no4;
        No5 = no5;
        No6 = no6;
        No7 = no7;
        No8 = no8;
        No9 = no9;
        No10 = no10;
        Adult = adult;
        Child = child;
        Category = category;
        Price = price1;
        Quantity = quantity;
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
        No1Price = no1Price;
        No2Price = no2Price;
        No3Price = no3Price;
        No4Price = no4Price;
        No5Price = no5Price;
        No6Price = no6Price;
        No7Price = no7Price;
        No8Price = no8Price;
        No9Price = no9Price;
        No10Price = no10Price;
        AdultPrice = adultPrice;
        ChildPrice = childPrice;
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

    public String getS1() {
        return S1;
    }

    public String getS2() {
        return S2;
    }

    public String getS3() {
        return S3;
    }

    public String getS4() {
        return S4;
    }

    public String getS5() {
        return S5;
    }

    public String getImage1() {
        return Image1;
    }

    public String getProductName() {
        return ProductName;
    }

    public String getNo1Price() {
        return No1Price;
    }

    public String getAdultPrice() {
        return AdultPrice;
    }

    public String getKeyward() {
        return Keyward;
    }

    public String getCompany() {
        return Company;
    }

    public String getChild() {
        return Child;
    }

    public void setChild(String child) {
        Child = child;
    }

    public String getCategory() {
        return Category;
    }
}