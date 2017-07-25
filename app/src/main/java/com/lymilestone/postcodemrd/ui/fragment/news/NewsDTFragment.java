package com.lymilestone.postcodemrd.ui.fragment.news;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.animation.BaseAnimation;
import com.lymilestone.httplibrary.rx.bus.BusFactory;
import com.lymilestone.httplibrary.utils.log.LUtils;
import com.lymilestone.postcodemrd.R;
import com.lymilestone.postcodemrd.base.mvp.MBaseFragment;
import com.lymilestone.postcodemrd.constants.Constants;
import com.lymilestone.postcodemrd.modle.event.ToolBarAlphaEvent;
import com.lymilestone.postcodemrd.modle.response.news.NewsChannelTable;
import com.lymilestone.postcodemrd.modle.response.news.NewsPhotoDetail;
import com.lymilestone.postcodemrd.modle.response.news.NewsSummary;
import com.lymilestone.postcodemrd.ui.activity.news.NewsDetailActivity;
import com.lymilestone.postcodemrd.ui.activity.news.NewsDetailPhotoActivity;
import com.lymilestone.postcodemrd.ui.adapter.news.NewsDTRV;
import com.lymilestone.postcodemrd.ui.contract.news.NewsCondtract;
import com.lymilestone.postcodemrd.ui.presenter.news.NewsPresenter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by CodeManLY on 2017/7/24 0024.
 */

public class NewsDTFragment extends MBaseFragment<NewsPresenter> implements NewsCondtract.View {
    @BindView(R.id.news_detail_rv)
    RecyclerView recyclerView;
    @BindView(R.id.news_swipe)
    SwipeRefreshLayout swipeRefreshLayout;
    private NewsDTRV adapter;
    private NewsChannelTable newsChannelTable;
    private int startPage;
    private List<NewsSummary> newsSummaries;

    @Override
    protected void initBefore() {
        super.initBefore();
        getFragmentComponent().inject(this);
    }

    @Override
    public void initView() {
        super.initView();
        newsSummaries=new ArrayList<>();
        newsChannelTable = (NewsChannelTable) getArguments().getSerializable("NewsChannelTable");
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        adapter = new NewsDTRV(this,new ArrayList<NewsSummary>());
        recyclerView.setAdapter(adapter);
        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        adapter.setNotDoAnimationCount(6);
    }

    @Override
    public void initListener() {
        super.initListener();
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (adapter.getItemViewType(position)==NewsSummary.TYPE_ITEM){
                    Intent intent = new Intent(activity, NewsDetailActivity.class);
                    NewsSummary summary = newsSummaries.get(position);
                    intent.putExtra("postId", summary.getPostid());
                    intent.putExtra("imgUrl", summary.getImgsrc());
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        ActivityOptions options = ActivityOptions
                                .makeSceneTransitionAnimation(activity,view, Constants.TRANSITION_ANIMATION_NEWS_PHOTOS);
                        activity.startActivity(intent, options.toBundle());
                    } else {
                        //让新的Activity从一个小的范围扩大到全屏
                        ActivityOptionsCompat options = ActivityOptionsCompat
                                .makeScaleUpAnimation(view, view.getWidth() / 2, view.getHeight() / 2, 0, 0);
                        ActivityCompat.startActivity(activity, intent, options.toBundle());
                    }
                }else if (adapter.getItemViewType(position)==NewsSummary.TYPE_PHOTO_ITEM){
                    NewsSummary newsSummary = newsSummaries.get(position);
                    NewsPhotoDetail newsPhotoDetail=new NewsPhotoDetail();
                    List<NewsPhotoDetail.Picture> pictures=new ArrayList<NewsPhotoDetail.Picture>();
                    newsPhotoDetail.setTitle(newsSummary.getTitle());
                    if (newsSummary.getAds()!=null){
                        for (NewsSummary.AdsBean adsBean:newsSummary.getAds()) {
                            NewsPhotoDetail.Picture e = new NewsPhotoDetail.Picture();
                            e.setTitle(adsBean.getTitle());
                            e.setImgSrc(adsBean.getImgsrc());
                            pictures.add(e);
                        }
                        newsPhotoDetail.setPictures(pictures);
                    }else if (newsSummary.getImgextra()!=null){
                        for (NewsSummary.ImgextraBean imgextraBean:newsSummary.getImgextra()) {
                            NewsPhotoDetail.Picture e = new NewsPhotoDetail.Picture();
                            e.setImgSrc(imgextraBean.getImgsrc());
                            pictures.add(e);
                        }
                        newsPhotoDetail.setPictures(pictures);
                    }else {
                            NewsPhotoDetail.Picture e = new NewsPhotoDetail.Picture();
                            e.setImgSrc(newsSummary.getImgsrc());
                            pictures.add(e);
                            newsPhotoDetail.setPictures(pictures);
                        }
                        Intent intent = new Intent(activity, NewsDetailPhotoActivity.class);
                        NewsSummary summary = newsSummaries.get(position);
                        intent.putExtra("newsPhotoDetail", newsPhotoDetail);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            ActivityOptions options = ActivityOptions
                                    .makeSceneTransitionAnimation(activity,view, Constants.TRANSITION_ANIMATION_NEWS_PHOTOS);
                            activity.startActivity(intent, options.toBundle());
                        } else {
                            //让新的Activity从一个小的范围扩大到全屏
                            ActivityOptionsCompat options = ActivityOptionsCompat
                                    .makeScaleUpAnimation(view, view.getWidth() / 2, view.getHeight() / 2, 0, 0);
                            ActivityCompat.startActivity(activity, intent, options.toBundle());
                        }
                    }
            }
        });
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                swipeRefreshLayout.setEnabled(false);
                mPresenter.handleNews(newsChannelTable.getNewsChannelType(),newsChannelTable.getNewsChannelId(),startPage);
                adapter.loadMoreComplete();
            }
        },recyclerView);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                startPage=0;
                openRefresh(swipeRefreshLayout);
                mPresenter.handleNews(newsChannelTable.getNewsChannelType(),newsChannelTable.getNewsChannelId(),startPage);

            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                BusFactory.getBus().post(new ToolBarAlphaEvent(dy));
            }
        });
    }

    @Override
    public void showNews(List<NewsSummary> summaryList) {
        getMultipleItemData(summaryList);
    }

    @Override
    protected int getLayout() {
        return R.layout.news_detail;
    }

    @Override
    protected void lazyLoad() {
        startPage=0;
        openRefresh(swipeRefreshLayout);
        mPresenter.handleNews(newsChannelTable.getNewsChannelType(),newsChannelTable.getNewsChannelId(),startPage);
    }

    public void getMultipleItemData(List<NewsSummary> summaryList) {
        for (int i = 0; i < summaryList.size(); i++) {
            NewsSummary newsSummary = summaryList.get(i);
            if (!TextUtils.isEmpty(newsSummary.getDigest())){
                newsSummary.setItemType(NewsSummary.TYPE_ITEM);
            }else {
                newsSummary.setItemType(NewsSummary.TYPE_PHOTO_ITEM);
            }
        }
        if (startPage==0){
            newsSummaries.clear();
            adapter.setNewData(summaryList);
        }else if (startPage>0){
            adapter.addData(summaryList);
        }
        newsSummaries.addAll(summaryList);
        closeRefresh(swipeRefreshLayout);
        swipeRefreshLayout.setEnabled(true);
        startPage+=20;
    }

    @Override
    public void showErrorMsg(String msg) {
        super.showErrorMsg(msg);
        closeRefresh(swipeRefreshLayout);
    }
}
