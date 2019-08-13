package com.furqoncreative.submission3.view.fragment;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.furqoncreative.submission3.R;
import com.furqoncreative.submission3.adapter.MoviesAdapter;
import com.furqoncreative.submission3.model.movie.Movie;
import com.furqoncreative.submission3.model.movie.MovieGenre;
import com.furqoncreative.submission3.view.activity.DetailMovieActivity;
import com.furqoncreative.submission3.viewModel.MovieViewModel;
import com.stone.vega.library.VegaLayoutManager;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {

    @BindView(R.id.avi)
    AVLoadingIndicatorView avi;
    @BindView(R.id.rv_movie)
    RecyclerView rvMovie;
    @BindView(R.id.error_layout)
    LinearLayout errorLayout;
    private MoviesAdapter mAdapter;
    private final Observer<ArrayList<Movie>> getMovies = new Observer<ArrayList<Movie>>() {
        @Override
        public void onChanged(ArrayList<Movie> items) {
            if (items != null) {
                mAdapter.addMovies(items);
                showLoading(false);
            }
        }
    };
    private final Observer<ArrayList<MovieGenre>> getGenres = new Observer<ArrayList<MovieGenre>>() {
        @Override
        public void onChanged(ArrayList<MovieGenre> items) {
            if (items != null) {
                mAdapter.addGenres(items);
                showLoading(false);
            }
        }
    };
    private MovieViewModel movieViewModel;

    public MovieFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_movie, container, false);
        ButterKnife.bind(this, v);
        checkConnection();
        showLoading(true);
        mAdapter = new MoviesAdapter(getContext(), new ArrayList<Movie>(), new ArrayList<MovieGenre>(), new MoviesAdapter.PostItemListener() {
            @Override
            public void onPostClick(int id) {
                Intent intent = new Intent(getContext(), DetailMovieActivity.class);
                intent.putExtra(DetailMovieActivity.ID, id);
                startActivity(intent);
            }
        });

        setupViewModeL();
        setupData();
        setupView();
        return v;
    }

    private void checkConnection() {
        ConnectivityManager connMgr = (ConnectivityManager) getContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
        } else {
            showLoading(false);
            showError();
        }
    }

    private void setupView() {
        rvMovie.setLayoutManager(new VegaLayoutManager());
        rvMovie.setAdapter(mAdapter);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        rvMovie.addItemDecoration(itemDecoration);
    }

    private void setupData() {
        String LANGUANGE = Locale.getDefault().toString();
        if (LANGUANGE.equals("in_ID")) {
            LANGUANGE = "id_ID";
        }
        movieViewModel.setMovies(LANGUANGE);
        movieViewModel.setGenre(LANGUANGE);
    }

    private void setupViewModeL() {
        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        movieViewModel.getMovies().observe(this, getMovies);
        movieViewModel.getGenres().observe(this, getGenres);

    }

    private void showLoading(Boolean state) {
        if (state) {
            avi.show();
        } else {
            avi.hide();
        }
    }


    private void showError() {
        errorLayout.setVisibility(View.VISIBLE);
    }


}
