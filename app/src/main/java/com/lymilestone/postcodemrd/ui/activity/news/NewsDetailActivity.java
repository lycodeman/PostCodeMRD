package com.lymilestone.postcodemrd.ui.activity.news;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lymilestone.httplibrary.utils.log.LUtils;
import com.lymilestone.postcodemrd.R;
import com.lymilestone.postcodemrd.base.mvp.MBaseActivity;
import com.lymilestone.postcodemrd.modle.response.news.NewsDetail;
import com.lymilestone.postcodemrd.ui.contract.news.NewsDetailsContract;
import com.lymilestone.postcodemrd.ui.presenter.news.NewsDetailPresenter;

import butterknife.BindView;

/**
 * Created by CodeManLY on 2017/7/25 0025.
 */

public class NewsDetailActivity extends MBaseActivity<NewsDetailPresenter> implements NewsDetailsContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.news_detail_body_tv)
    TextView bodyTv;
    @BindView(R.id.news_detail_from_tv)
    TextView fromTv;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.news_detail_photo_iv)
    ImageView photoImg;
    @BindView(R.id.mask_view)
    View view;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    private Intent intent;
    private String postId;
    private String imgUrl;

    @Override
    public void showNewsDetail(NewsDetail newsDetail) {
        bodyTv.setText(Html.fromHtml(newsDetail.getBody()));
        String newsSource = newsDetail.getSource();
        String newsTime = newsDetail.getPtime();
        fromTv.setText(getString(R.string.news_from, newsSource, newsTime));
        toolbar.setTitle(newsDetail.getTitle());
        progressBar.setVisibility(View.INVISIBLE);
        fab.setVisibility(View.VISIBLE);
    }

    @Override
    public void showImg(Bitmap bitmap) {
        photoImg.setImageBitmap(bitmap);
        view.setVisibility(View.INVISIBLE);
    }

    @Override
    public void initBefore() {
        super.initBefore();
        getActivityComponent().inject(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_news_details;
    }

    @Override
    public void initView() {
        intent = getIntent();
        postId = intent.getStringExtra("postId");
        imgUrl = intent.getStringExtra("imgUrl");
        progressBar.setVisibility(View.VISIBLE);
        mPresenter.handleData(postId);
        mPresenter.fetchImg(photoImg,imgUrl);
    }

    @Override
    public void initListener() {
        super.initListener();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finish();
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toast("开发中...");
            }
        });
    }
}
