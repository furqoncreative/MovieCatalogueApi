package com.furqoncreative.submission3.ui.tv;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.furqoncreative.submission3.data.model.tv.Tv;
import com.furqoncreative.submission3.data.model.tv.TvGenre;
import com.furqoncreative.submission3.data.model.tv.TvGenresResponse;
import com.furqoncreative.submission3.data.model.tv.TvsResponse;
import com.furqoncreative.submission3.data.remote.ApiInterface;
import com.furqoncreative.submission3.data.remote.ApiUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.furqoncreative.submission3.data.remote.ApiUtils.API_KEY;

public class TvShowViewModel extends ViewModel {

    private final MutableLiveData<ArrayList<Tv>> lisTvs = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<TvGenre>> listTvGenres = new MutableLiveData<>();
    private final MutableLiveData<Tv> tv = new MutableLiveData<>();
    private final ApiInterface apiInterface = new ApiUtils().getApi();

    public LiveData<ArrayList<Tv>> getTvs() {
        return lisTvs;
    }

    public void setTvs(final String language) {
        apiInterface.getTvs(API_KEY, language).enqueue(new Callback<TvsResponse>() {
            @Override
            public void onResponse(Call<TvsResponse> call, Response<TvsResponse> response) {
                if (response.isSuccessful()) {
                    ArrayList<Tv> tvs = new ArrayList<>();
                    tvs.addAll(response.body().getTvs());
                    lisTvs.postValue(tvs);
                    Log.d("MainActivity", "posts loaded from API");
                } else {
                    int statusCode = response.code();
                    // handle request errors depending on status code
                }
            }

            @Override
            public void onFailure(Call<TvsResponse> call, Throwable t) {
                Log.i("MainActivity", "error", t);

            }
        });
    }

    public LiveData<ArrayList<TvGenre>> getGenres() {
        return listTvGenres;
    }

    public LiveData<Tv> getTv() {
        return tv;
    }

    public void setTv(int tv_id, String language) {
        apiInterface.getTv(tv_id, API_KEY, language).enqueue(new Callback<Tv>() {
            @Override
            public void onResponse(Call<Tv> call, Response<Tv> response) {
                if (response.isSuccessful()) {
                    tv.postValue(response.body());
                    Log.d("MainActivity", "posts loaded from API");
                } else {
                    int statusCode = response.code();
                    // handle request errors depending on status code
                }
            }

            @Override
            public void onFailure(Call<Tv> call, Throwable t) {
                Log.d("MainActivity", "error loading from API");

            }
        });
    }

    public void setGenre(String language) {
        apiInterface.getTvGenres(API_KEY, language).enqueue(new Callback<TvGenresResponse>() {
            @Override
            public void onResponse(Call<TvGenresResponse> call, Response<TvGenresResponse> response) {
                if (response.isSuccessful()) {
                    ArrayList<TvGenre> tvGenres = new ArrayList<>();
                    tvGenres.addAll(response.body().getGenres());
                    listTvGenres.postValue(tvGenres);
                    Log.d("MainActivity", "posts loaded from API");
                } else {
                    int statusCode = response.code();
                    // handle request errors depending on status code
                }
            }

            @Override
            public void onFailure(Call<TvGenresResponse> call, Throwable t) {
                Log.d("MainActivity", "error loading from API");

            }
        });
    }


}
