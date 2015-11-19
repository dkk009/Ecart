package com.ecomerce.deepak.ecomerce.model;

import com.ecomerce.deepak.ecomerce.interfaces.IDataModel;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 *
 */
public class Folder implements IDataModel{

    @SerializedName("uri")
    private String uri;
    @SerializedName("name")
    private String name;
    @SerializedName("facets")
    private ArrayList<Facet> facet;


    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Facet> getFacet() {
        return facet;
    }

    public void setFacet(ArrayList<Facet> facet) {
        this.facet = facet;
    }
}
