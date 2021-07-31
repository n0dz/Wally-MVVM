package com.nodz.galleryimages.Model;

public class CategoriesHome {

    String category;
    int img;

    public CategoriesHome(String category, int img) {
        this.category = category;
        this.img = img;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getCategory() {
        return category;
    }
}
