package com.ecomerce.deepak.ecomerce.model;

import com.ecomerce.deepak.ecomerce.interfaces.IDataModel;
import com.google.gson.annotations.SerializedName;

/**
 *
 */
public class Tag implements IDataModel{
    @SerializedName("tag")
    private String tagName;
}
