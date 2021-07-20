package com.xianlv.business.bean;

public class CategoryItem {
    public String name;
    public boolean isCheck;

    public CategoryItem(String name, boolean isCheck) {
        this.name = name;
        this.isCheck = isCheck;
    }

    public CategoryItem(String name) {
        this.name = name;
    }
}
