package com.furqoncreative.submission3.data.model.tv;

import com.google.gson.annotations.SerializedName;

public class TvGenre {

    @SerializedName("id")
    private Long mId;
    @SerializedName("name")
    private String mName;

    public Long getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

}
