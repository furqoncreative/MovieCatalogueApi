package com.furqoncreative.submission3.ui.movie;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.furqoncreative.submission3.R;
import com.furqoncreative.submission3.data.model.movie.Movie;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.furqoncreative.submission3.data.remote.ApiUtils.IMAGE_URL;

@SuppressWarnings("ALL")
public class MovieDetailsActivity extends AppCompatActivity {

    public static final String ID = "movie_id";
    @BindView(R.id.error_layout)
    LinearLayout errorLayout;
    @BindView(R.id.avi)
    AVLoadingIndicatorView avi;
    @BindView(R.id.imgBackdrop)
    ImageView imgBackdrop;
    @BindView(R.id.imgPoster)
    ImageView imgPoster;
    @BindView(R.id.txtReleaseDate)
    TextView txtReleaseDate;
    @BindView(R.id.imgRating)
    ImageView imgRating;
    @BindView(R.id.imgBack)
    ImageView imgBack;
    @BindView(R.id.txtTitlle)
    TextView txtTitlle;
    @BindView(R.id.txtRating)
    TextView txtRating;
    @BindView(R.id.labelOverview)
    TextView labelOverview;
    @BindView(R.id.txtOverview)
    TextView txtOverview;
    private final Observer<Movie> getMovie = new Observer<Movie>() {
        @Override
        public void onChanged(Movie movie) {
            if (movie != null) {
                showLoading();
                imgBack.setVisibility(View.VISIBLE);
                imgBackdrop.setVisibility(View.VISIBLE);
                Glide.with(MovieDetailsActivity.this)
                        .load(IMAGE_URL + movie.getBackdropPath())
                        .apply(RequestOptions.placeholderOf(R.color.colorPrimary))
                        .into(imgBackdrop);

                imgPoster.setVisibility(View.VISIBLE);
                Glide.with(MovieDetailsActivity.this)
                        .load(IMAGE_URL + movie.getPosterPath())
                        .apply(RequestOptions.placeholderOf(R.color.colorPrimary))
                        .into(imgPoster);
                txtTitlle.setText(movie.getOriginalTitle());
                txtReleaseDate.setVisibility(View.VISIBLE);
                txtReleaseDate.setText(movie.getReleaseDate());
                imgRating.setVisibility(View.VISIBLE);
                txtRating.setText(movie.getRating().toString());
                labelOverview.setVisibility(View.VISIBLE);
                if (movie.getOverview().length() == 0) {
                    txtOverview.setText(getResources().getString(R.string.not_found));
                } else {
                    txtOverview.setText(movie.getOverview());
                }
            }
        }
    };
    @BindView(R.id.content)
    ScrollView content;
    private MovieViewModel movieViewModel;
    private int MOVIE_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        ButterKnife.bind(this);
        checkConnection();
        setupViewModeL();
        setupData();
    }

    private void checkConnection() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
        } else {
            showLoading();
            showError();
        }
    }

    private void setupData() {
        MOVIE_ID = getIntent().getIntExtra(ID, MOVIE_ID);
        String LANGUANGE = Locale.getDefault().toString();
        if (LANGUANGE.equals("in_ID")) {
            LANGUANGE = "id_ID";
        }
        movieViewModel.setMovie(MOVIE_ID, LANGUANGE);

    }

    private void setupViewModeL() {
        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        movieViewModel.getMovie().observe(this, getMovie);

    }


    private void showLoading() {
        if (false) {
            avi.smoothToShow();
        } else {
            avi.smoothToHide();
        }
    }


    private void showError() {
        errorLayout.setVisibility(View.VISIBLE);
    }


    @OnClick(R.id.imgBack)
    public void onViewClicked() {
        finish();
    }
}
