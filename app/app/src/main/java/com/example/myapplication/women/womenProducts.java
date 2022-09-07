package com.example.myapplication.women;


public class womenProducts{

    String name;
    double price;
    int Img;


    public womenProducts(String name, double price, int img) {
        this.name = name;
        this.price = price;
        Img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getImg() {
        return Img;
    }

    public void setImg(int img) {
        Img = img;
    }


}
