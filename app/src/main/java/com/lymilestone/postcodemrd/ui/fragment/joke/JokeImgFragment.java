package com.lymilestone.postcodemrd.ui.fragment.joke;

import android.graphics.Bitmap;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lymilestone.httplibrary.utils.log.LUtils;
import com.lymilestone.httplibrary.utils.rx.RxUtils;
import com.lymilestone.postcodemrd.R;
import com.lymilestone.postcodemrd.base.mvp.MBaseFragment;
import com.lymilestone.postcodemrd.modle.response.joke.JokeImgs;
import com.lymilestone.postcodemrd.modle.response.joke.JokePic;
import com.lymilestone.postcodemrd.ui.adapter.joke.JokeContentAdapter;
import com.lymilestone.postcodemrd.ui.adapter.joke.JokeImgAdapter;
import com.lymilestone.postcodemrd.ui.contract.joke.JokeImgContract;
import com.lymilestone.postcodemrd.ui.presenter.joke.JokeImgPresenter;
import com.lymilestone.postcodemrd.utils.ImageLoader;

import java.util.List;

import butterknife.BindView;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by CodeManLY on 2017/7/21 0021.
 */

public class JokeImgFragment extends MBaseFragment<JokeImgPresenter> implements JokeImgContract.View, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.joke_img_rv)
    RecyclerView recyclerView;
    @BindView(R.id.joke_swipe)
    SwipeRefreshLayout swipeRefreshLayout;
    private int lastPage=1;
    private int currentPage=1;
    private int pageSize=20;
    private List<JokeImgs.DataBean> content;
    private JokeImgAdapter imgAdapter;
    private boolean refreshTag;

    @Override
    protected void initBefore() {
        super.initBefore();
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.joke_img_news;
    }
    @Override
    public void initView() {
        super.initView();
        StaggeredGridLayoutManager layout = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layout);
        imgAdapter = new JokeImgAdapter(R.layout.joke_item_img, content);
        recyclerView.setAdapter(imgAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                layout.invalidateSpanAssignments();//滑动时候的闪动问题
            }
        });
    }

    @Override
    public void initListener() {
        super.initListener();
        imgAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        imgAdapter.setNotDoAnimationCount(6);
        imgAdapter.setOnLoadMoreListener(this,recyclerView);
        imgAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currentPage=1;
                lastPage=1;
                refreshTag = true;
                mPresenter.getNewsImg(1,20);
                imgAdapter.setEnableLoadMore(false);//关闭上拉加载
            }
        });
    }
    @Override
    protected void lazyLoad() {
//        mPresenter.getNewsImg(currentPage,pageSize);
    }

    @Override
    public void showTimeImg(JokeImgs jokeImgs) {
        LUtils.e(jokeImgs.toString());
    }

    @Override
    public void showNewsImg(JokeImgs jokeImgs) {

        //瀑布流图片转换
        RxUtils.createObservable(jokeImgs)
                .flatMap(new Function<JokeImgs, ObservableSource<JokeImgs>>() {
                    @Override
                    public ObservableSource<JokeImgs> apply(@io.reactivex.annotations.NonNull JokeImgs jokeImgs) throws Exception {
                        for (JokeImgs.DataBean data : jokeImgs.getData()) {
                            Bitmap bitmap = ImageLoader.load(activity, data.getUrl());
                            if (bitmap != null) {
                                data.setWidth(bitmap.getWidth());
                                data.setHeight(bitmap.getHeight());
                            }
                        }
                        return RxUtils.createObservable(jokeImgs);
                    }
                })
                .compose(RxUtils.defaultTransformer())
                .compose(bindToLifecycle())
                .subscribe(new Consumer<JokeImgs>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull JokeImgs jokeImgs) throws Exception {
                        if (currentPage==1){
                            lastPage=currentPage;
                            content = jokeImgs.getData();
                            imgAdapter.setNewData(content);
                            currentPage++;
                        }else if (currentPage>1){
                            lastPage=currentPage;
                            content = jokeImgs.getData();
                            imgAdapter.addData(content);
                            currentPage++;
                        }

                    }
                });
        if (refreshTag){//只有在下拉刷新的时候才会关闭
            closeRefresh(swipeRefreshLayout);
            refreshTag=!refreshTag;
        }
        swipeRefreshLayout.setEnabled(true);//加载完成时可下拉刷新
        imgAdapter.setEnableLoadMore(true);//可上拉加载

    }

    @Override
    public void onLoadMoreRequested() {
        swipeRefreshLayout.setEnabled(false);//上啦加载时关闭swipeRefreshLayout
        if (content.size() == pageSize) {//相等说明还有下一页
            mPresenter.getNewsImg(currentPage, pageSize);
            imgAdapter.loadMoreComplete();
        } else if (content.size() < pageSize) {//小于说明没有数据了
            imgAdapter.loadMoreEnd();
            swipeRefreshLayout.setEnabled(true);
        }
    }

    @Override
    public void showErrorMsg(String msg) {
        super.showErrorMsg(msg);
        closeRefresh(swipeRefreshLayout);
    }
}
