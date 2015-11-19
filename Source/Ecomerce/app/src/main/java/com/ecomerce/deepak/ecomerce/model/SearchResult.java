package com.ecomerce.deepak.ecomerce.model;

import com.ecomerce.deepak.ecomerce.interfaces.IDataModel;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 *
 */
public class SearchResult implements IDataModel {

    /*
    took : 40.81
total : 5504
selected_tags
selected_facets
folders
products
prev : ""
next : "page=2&tags=mobiles"
     */
    @SerializedName("took")
    private double tookTime;
    @SerializedName("total")
    private int total;



    @SerializedName("prev")
    private String prevUrl;
    @SerializedName("next")
    private String nextUrl;
    @SerializedName("folders")
    private ArrayList<Folder> folders;
    @SerializedName("products")
    private ArrayList<Product> products;

    public double getTookTime() {
        return tookTime;
    }

    public void setTookTime(double tookTime) {
        this.tookTime = tookTime;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getPrevUrl() {
        return prevUrl;
    }

    public void setPrevUrl(String prevUrl) {
        this.prevUrl = prevUrl;
    }

    public String getNextUrl() {
        return nextUrl;
    }

    public void setNextUrl(String nextUrl) {
        this.nextUrl = nextUrl;
    }

    public ArrayList<Folder> getFolders() {
        return folders;
    }

    public void setFolders(ArrayList<Folder> folders) {
        this.folders = folders;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }
}
