package com.example.pankaj.assignment.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Pankaj on 19-06-2017.
 */

public class VoteDownReason {
    @SerializedName("Self Promotion")
    @Expose
    private Integer selfPromotion;

    public Integer getSelfPromotion() {
        return selfPromotion;
    }

    public void setSelfPromotion(Integer selfPromotion) {
        this.selfPromotion = selfPromotion;
    }
}
