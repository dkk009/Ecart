package com.ecomerce.deepak.ecomerce.model;

import com.ecomerce.deepak.ecomerce.interfaces.IDataModel;

/**
 *
 */
public class Image implements IDataModel {

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
