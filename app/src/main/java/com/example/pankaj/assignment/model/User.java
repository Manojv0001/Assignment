package com.example.pankaj.assignment.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Pankaj on 19-06-2017.
 */

public class User {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("rank")
    @Expose
    private String rank;
    @SerializedName("current_dimes")
    @Expose
    private Integer currentDimes;
    @SerializedName("karma")
    @Expose
    private Integer karma;
    @SerializedName("fpd_count")
    @Expose
    private Integer fpdCount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public Integer getCurrentDimes() {
        return currentDimes;
    }

    public void setCurrentDimes(Integer currentDimes) {
        this.currentDimes = currentDimes;
    }

    public Integer getKarma() {
        return karma;
    }

    public void setKarma(Integer karma) {
        this.karma = karma;
    }

    public Integer getFpdCount() {
        return fpdCount;
    }

    public void setFpdCount(Integer fpdCount) {
        this.fpdCount = fpdCount;
    }

}
