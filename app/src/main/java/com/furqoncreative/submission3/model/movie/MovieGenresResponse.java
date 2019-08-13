package com.furqoncreative.submission3.model.movie;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class MovieGenresResponse {

    @SerializedName("genres")
    private List<MovieGenre> mGenres;

    public List<MovieGenre> getGenres() {
        return mGenres;
    }

}
