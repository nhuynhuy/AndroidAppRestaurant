package com.example.RestaurantApp.models;


import java.io.Serializable;

public class Food implements Serializable {
    int foodId;
    String title, disc;
    String menuName;
    float price;

    byte[] picture;

    public Food(int foodId) {
        this.foodId = foodId;
    }

    public Food(int foodId, String title, String disc, String menuName, float price, byte[] picture) {
        this.foodId = foodId;
        this.title = title;
        this.disc = disc;
        this.menuName = menuName;
        this.price = price;
        this.picture = picture;
    }

    public Food(String title, String disc, String menuName, float price, byte[] picture) {
        this.title = title;
        this.disc = disc;
        this.menuName = menuName;
        this.price = price;
        this.picture = picture;
    }

    public Food() {

    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDisc() {
        return disc;
    }

    public void setDisc(String disc) {
        this.disc = disc;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }
}


