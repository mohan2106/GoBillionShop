package com.mohan.gobillionshop.ui.home;

public class productClass {
    int id;
    String name,description,category,image;
    double price;
    float rating;
    int nRating;

    public productClass(int id, String name, String description, String category, String image, double price, float rating, int nRating) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.image = image;
        this.price = price;
        this.rating = rating;
        this.nRating = nRating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getnRating() {
        return nRating;
    }

    public void setnRating(int nRating) {
        this.nRating = nRating;
    }
}
