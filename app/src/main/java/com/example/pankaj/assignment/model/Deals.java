package com.example.pankaj.assignment.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pankaj on 19-06-2017.
 */

public class Deals {

    @SerializedName("total_count")
    @Expose
    private Integer totalCount;
    @SerializedName("data")
    @Expose
    private List<Data> data = new ArrayList();

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

}
