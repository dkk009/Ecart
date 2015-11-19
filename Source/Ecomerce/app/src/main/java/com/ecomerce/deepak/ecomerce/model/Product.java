package com.ecomerce.deepak.ecomerce.model;

import com.ecomerce.deepak.ecomerce.interfaces.IDataModel;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

/**
 *
 */
public class Product implements IDataModel{

    /*
    id : "42912"
name : "Nokia Lumia 520"
brand : "Nokia"
category : "Mobiles"
category_id : 1
avg_rating : "8.00"
rating_count : 343
review_count : 125
stock_info : "In Stock"
deal_count : 9
store_count : 9
min_price : "7499.00"
min_price_str : "7,499"
     */

    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("brand")
    private String brand;
    @SerializedName("category")
    private String category;
    @SerializedName("category_id")
    private String categoryId;
    @SerializedName("avg_rating")
    private float avgRating;
    @SerializedName("rating_count")
    private int ratingCount;
    @SerializedName("stock_info")
    private  String stockInfo;
    @SerializedName("deal_count")
    private int dealCount;
    @SerializedName("store_count")
    private int storeCount;
    @SerializedName("min_price")
    private double minPrice;
    @SerializedName("min_price_str")
    private String minPriceStr;
    @SerializedName("url")
    private String url;
    @SerializedName("biq_score")
    private int bigScore;
    @SerializedName("curated_review_count")
    private int curatedReviewCount;
    @SerializedName("images")
    private Image productImages[];
    @SerializedName("images_o")
    private HashMap<String,String> images_o;

    private KeyFeatures features[];

    public HashMap<String, String> getImages_o() {
        return images_o;
    }

    public void setImages_o(HashMap<String, String> images_o) {
        this.images_o = images_o;
    }

    public KeyFeatures[] getFeatures() {
        return features;
    }

    public void setFeatures(KeyFeatures[] features) {
        this.features = features;
    }

    public int getStoreCount() {
        return storeCount;
    }

    public void setStoreCount(int storeCount) {
        this.storeCount = storeCount;
    }

    public Image[] getProductImages() {
        return productImages;
    }

    public void setProductImages(Image[] productImages) {
        this.productImages = productImages;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getBigScore() {
        return bigScore;
    }

    public void setBigScore(int bigScore) {
        this.bigScore = bigScore;
    }

    public int getCuratedReviewCount() {
        return curatedReviewCount;
    }

    public void setCuratedReviewCount(int curatedReviewCount) {
        this.curatedReviewCount = curatedReviewCount;
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

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public float getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(float avgRating) {
        this.avgRating = avgRating;
    }

    public int getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(int ratingCount) {
        this.ratingCount = ratingCount;
    }

    public String getStockInfo() {
        return stockInfo;
    }

    public void setStockInfo(String stockInfo) {
        this.stockInfo = stockInfo;
    }

    public int getDealCount() {
        return dealCount;
    }

    public void setDealCount(int dealCount) {
        this.dealCount = dealCount;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    public String getMinPriceStr() {
        return minPriceStr;
    }

    public void setMinPriceStr(String minPriceStr) {
        this.minPriceStr = minPriceStr;
    }
}
