package com.bagew.expensetracker2.views.models;

public class Category {
    private String catgoryName;
    private int image;

    public Category(String catgoryName) {
        this.catgoryName = catgoryName;
    }
    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getCatgoryName() {
        return catgoryName;
    }

    public void setCatgoryName(String catgoryName) {
        this.catgoryName = catgoryName;
    }
}
