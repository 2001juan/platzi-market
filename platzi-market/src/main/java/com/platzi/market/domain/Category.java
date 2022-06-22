package com.platzi.market.domain;

public class Category {
    private int categoriId;
    private String category;
    private boolean active;

    public int getCategoriId() {
        return categoriId;
    }

    public void setCategoriId(int categoriId) {
        this.categoriId = categoriId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
