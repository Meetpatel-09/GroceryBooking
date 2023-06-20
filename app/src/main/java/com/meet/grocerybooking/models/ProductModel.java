package com.meet.grocerybooking.models;

public class ProductModel {

    private String id;
    private String name;
    private String shopkeeperID;
    private String price;
    private String brand;
    private String category;
    private String description;
    private String image;

    public ProductModel() {
    }

    public ProductModel(String id, String name, String shopkeeperID, String price, String brand, String category, String description, String image) {
        this.id = id;
        this.name = name;
        this.shopkeeperID = shopkeeperID;
        this.price = price;
        this.brand = brand;
        this.category = category;
        this.description = description;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShopkeeperID() {
        return shopkeeperID;
    }

    public void setShopkeeperID(String shopkeeperID) {
        this.shopkeeperID = shopkeeperID;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
