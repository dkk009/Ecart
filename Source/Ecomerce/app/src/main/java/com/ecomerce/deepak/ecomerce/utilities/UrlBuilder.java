package com.ecomerce.deepak.ecomerce.utilities;

import android.text.TextUtils;

import java.util.ArrayList;

/**
 *
 */
public class UrlBuilder {
    private static ArrayList<String> sTagList = new ArrayList<>();
    private static String sPageNumber = "";
    private static String sFacetName = "";
    private static final String BASE_URL = "http://api.buyingiq.com/v1/search/?";
    //tags=mobiles&facet=1&page=1
    public static String mUrl = "";
    private static String defaultTagString   = "tags=mobiles&";
    private static String tagString = "";


    public static void addTag(String tag) {
        sTagList.add(tag);
    }
    public static  void clearTags() {
        sTagList.clear();
    }

    public static void clearUrls() {
        sPageNumber = "";
        clearTags();
        sFacetName = "";
    }

    public static String getTagString() {
        return tagString;
    }

    public static void setTagString(String tagString) {
        String tags[] = tagString.split(" ");
        UrlBuilder.tagString = "";
        for(String str : tags) {
            UrlBuilder.tagString =  UrlBuilder.tagString + "tags=" + str + "&";
        }
    }

    public static void setsPageNumber(String page) {
        sPageNumber = page;
    }
    public static void setFacet(String facet) {
        sFacetName = facet;
    }


    public static String getUrl() {
        if(TextUtils.isEmpty(tagString))
            tagString = defaultTagString;
        if(TextUtils.isEmpty(sPageNumber)) {
            sPageNumber = "page=1";
        }
        if(TextUtils.isEmpty(sFacetName)) {
            sFacetName = "facet=1";
        }

        for(String tag : sTagList) {
            tagString = tagString + "tags="+tag+"&";
        }
        mUrl = BASE_URL + tagString+ sFacetName +"&"+ sPageNumber;
        return mUrl;
    }

    public static void removeTag(String tag) {
        sTagList.remove(tag);
    }

}
