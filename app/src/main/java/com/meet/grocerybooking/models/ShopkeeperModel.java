package com.meet.grocerybooking.models;

public class ShopkeeperModel {
    private String id;
    private String name;
    private String shopName;
    private String email;
    private String phone;
    private String imageUrl;

    public ShopkeeperModel() {
    }

    public ShopkeeperModel(String id, String name, String shopName, String email, String phone, String imageUrl) {
        this.id = id;
        this.name = name;
        this.shopName = shopName;
        this.email = email;
        this.phone = phone;
        this.imageUrl = imageUrl;
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

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
