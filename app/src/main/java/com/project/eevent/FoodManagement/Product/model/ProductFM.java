package com.project.eevent.FoodManagement.Product.model;

import com.google.gson.annotations.SerializedName;

public class ProductFM {

    @SerializedName("product_id")
    private String product_id;

    @SerializedName("name")
    private String name;
    @SerializedName("price")
    private String price;
    @SerializedName("value")
    private String value;
    @SerializedName("image")
    private String image;

    @SerializedName("category")
    private String category;

    @SerializedName("quantity")
    private String quantity;


    @SerializedName("fm_cell")
    private String fm_cell;

    @SerializedName("description")
    private String description;



    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getImage() {
        return image;
    }

    public String getCategory() {
        return category;
    }


    public String getDescription() {
        return description;
    }

    public String getFMCell() {
        return fm_cell;
    }

    public String getProductId() {
        return product_id;
    }


}