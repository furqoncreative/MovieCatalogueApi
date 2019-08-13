package com.furqoncreative.submission3.view.activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.furqoncreative.submission3.R;
import com.furqoncreative.submission3.model.tv.Tv;
import com.furqoncreative.submission3.viewModel.TvViewModel;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.furqoncreative.submission3.util.ApiUtils.IMAGE_URL;

@SuppressWarnings("ALL")
public class DetailTvActivity extends AppCompatActivity {

    public static final String ID = "tv_id";
    @BindView(R.id.avi)
    AVLoadingIndicatorView avi;
    @BindView(R.id.error_layout)
    LinearLayout errorLayout;
    @BindView(R.id.imgBackdrop)
    ImageView imgBackdrop;
    @BindView(R.id.imgPoster)
    ImageView imgPoster;
    @BindView(R.id.imgRating)
    ImageView imgRating;
    @BindView(R.id.imgBack)
    ImageView imgBack;
    @BindView(R.id.txtTitlle)
    TextView txtTitlle;
    @BindView(R.id.txtReleaseDate)
    TextView txtReleaseDate;
    @BindView(R.id.txtRating)
    TextView txtRating;
    @BindView(R.id.labelOverview)
    TextView labelOverview;
    @BindView(R.id.txtOverview)
    TextView txtOverview;
    private final Observer<Tv> getTv = new Observer<Tv>() {
        @Override
        public void onChanged(Tv tv) {
            if (tv != null) {
                showLoading();
                imgBack.setVisibility(View.VISIBLE);
                imgBackdrop.setVisibility(View.VISIBLE);
                Glide.with(DetailTvActivity.this)
                        .load(IMAGE_URL + tv.getBackdropPath())
                        .apply(RequestOptions.placeholderOf(R.color.colorPrimary))
                        .into(imgBackdrop);

                imgPoster.setVisibility(View.VISIBLE);
                Glide.with(DetailTvActivity.this)
                        .load(IMAGE_URL + tv.getPosterPath())
                        .apply(RequestOptions.placeholderOf(R.color.colorPrimary))
                        .into(imgPoster);
                txtTitlle.setText(tv.getOriginalName());
                txtReleaseDate.setVisibility(View.VISIBLE);
                txtReleaseDate.setText(tv.getFirstAirDate());
                imgRating.setVisibility(View.VISIBLE);
                txtRating.setText(tv.getRating().toString());
                labelOverview.setVisibility(View.VISIBLE);
                if (tv.getOverview().length() == 0) {
                    txtOverview.setText(getResources().getString(R.string.not_found));

                } else {
                    txtOverview.setText(tv.getOverview());

                }
            }
        }
    };
    private TvViewModel tvViewModel;
    private int TV_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tv);
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
        TV_ID = getIntent().getIntExtra(ID, TV_ID);
        String LANGUANGE = Locale.getDefault().toString();
        if (LANGUANGE.equals("in_ID")) {
            LANGUANGE = "id_ID";
        }
        tvViewModel.setTv(TV_ID, LANGUANGE);

    }

    private void setupViewModeL() {

        tvViewModel = ViewModelProviders.of(this).get(TvViewModel.class);
        tvViewModel.getTv().observe(this, getTv);

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
