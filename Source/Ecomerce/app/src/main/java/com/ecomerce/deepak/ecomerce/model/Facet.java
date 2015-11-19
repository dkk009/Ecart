package com.ecomerce.deepak.ecomerce.model;

import com.ecomerce.deepak.ecomerce.interfaces.IDataModel;
import com.google.gson.annotations.SerializedName;

/**
 *
 */
public class Facet implements IDataModel{
    /*
     "label": "Adapters",
                    "tag": "adapters",
                    "count": 117,
                    "is_selected": false
     */
    @SerializedName("label")
    private String label;
    @SerializedName("tag")
    private String tag;
    @SerializedName("count")
    private int count;
    @SerializedName("is_selected")
    private boolean isSelected;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
}
