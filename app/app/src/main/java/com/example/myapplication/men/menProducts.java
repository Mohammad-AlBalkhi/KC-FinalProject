package com.example.myapplication.men;

public class menProducts {
    String name;
    double price;
    int Img;

    public menProducts(String name, double price, int img) {
        this.name = name;
        this.price = price;
        Img = img;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setImg(int img) {
        Img = img;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getImg() {
        return Img;
    }
}
