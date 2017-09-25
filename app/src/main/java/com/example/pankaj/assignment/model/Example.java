package com.example.pankaj.assignment.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Pankaj on 19-06-2017.
 */

public class Example {
    @SerializedName("seo_setting")
    @Expose
    private SeoSetting seoSetting;
    @SerializedName("deals")
    @Expose
    private Deals deals;

    public SeoSetting getSeoSetting() {
        return seoSetting;
    }

    public void setSeoSetting(SeoSetting seoSetting) {
        this.seoSetting = seoSetting;
    }

    public Deals getDeals() {
        return deals;
    }

    public void setDeals(Deals deals) {
        this.deals = deals;
    }
}
