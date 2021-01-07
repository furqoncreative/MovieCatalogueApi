package com.furqoncreative.submission3.ui.tv;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.furqoncreative.submission3.R;
import com.furqoncreative.submission3.data.model.tv.Tv;
import com.furqoncreative.submission3.data.model.tv.TvGenre;
import com.stone.vega.library.VegaLayoutManager;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowListFragment extends Fragment {


    @BindView(R.id.avi)
    AVLoadingIndicatorView avi;
    @BindView(R.id.rv_tv)
    RecyclerView rvTv;
    @BindView(R.id.activity_main)
    RelativeLayout activityMain;
    @BindView(R.id.error_layout)
    LinearLayout errorLayout;
    private TvShowListAdapter mAdapter;
    private final Observer<ArrayList<Tv>> getTvs = new Observer<ArrayList<Tv>>() {
        @Override
        public void onChanged(ArrayList<Tv> items) {
            if (items != null) {
                showLoading(false);
                mAdapter.addTvs(items);
            } else if (items == null) {
                showLoading(false);
                showError();
            }
        }
    };
    private final Observer<ArrayList<TvGenre>> getGenres = new Observer<ArrayList<TvGenre>>() {
        @Override
        public void onChanged(ArrayList<TvGenre> items) {
            if (items != null) {
                mAdapter.addGenres(items);
                showLoading(false);
            } else if (items == null) {
                showLoading(false);
                showError();
            }
        }
    };
    private TvShowViewModel tvShowViewModel;

    public TvShowListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tv, container, false);
        ButterKnife.bind(this, v);
        checkConnection();
        showLoading(true);
        mAdapter = new TvShowListAdapter(getContext(), new ArrayList<Tv>(), new ArrayList<TvGenre>(), new TvShowListAdapter.PostItemListener() {
            @Override
            public void onPostClick(int id) {
                Intent intent = new Intent(getContext(), TvShowDetailActivity.class);
                intent.putExtra(TvShowDetailActivity.ID, id);
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
        rvTv.setLayoutManager(new VegaLayoutManager());
        rvTv.setAdapter(mAdapter);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        rvTv.addItemDecoration(itemDecoration);


    }

    private void setupData() {
        String LANGUANGE = Locale.getDefault().toString();
        if (LANGUANGE.equals("in_ID")) {
            LANGUANGE = "id_ID";
        }
        tvShowViewModel.setTvs(LANGUANGE);
        tvShowViewModel.setGenre(LANGUANGE);
    }

    private void setupViewModeL() {
        tvShowViewModel = ViewModelProviders.of(this).get(TvShowViewModel.class);
        tvShowViewModel.getTvs().observe(this, getTvs);
        tvShowViewModel.getGenres().observe(this, getGenres);

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
