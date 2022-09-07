package com.example.myapplication.accessories;

public class AccessoriesProducts {
    private String name;
    private double price;
    private int Img;


    public AccessoriesProducts(String name, double price, int Img) {
        this.name = name;
        this.price = price;
        this.Img = Img;
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
