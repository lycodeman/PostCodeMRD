package com.lymilestone.postcodemrd.ui.fragment.joke;

import android.preference.SwitchPreference;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.animation.BaseAnimation;
import com.lymilestone.httplibrary.utils.log.LUtils;
import com.lymilestone.httplibrary.utils.manager.AppUtils;
import com.lymilestone.postcodemrd.R;
import com.lymilestone.postcodemrd.base.mvp.MBaseFragment;
import com.lymilestone.postcodemrd.modle.response.joke.JokeContent;
import com.lymilestone.postcodemrd.ui.adapter.joke.JokeContentAdapter;
import com.lymilestone.postcodemrd.ui.contract.joke.JokeContentContract;
import com.lymilestone.postcodemrd.ui.presenter.joke.JokeContentPresenter;

import java.util.List;

import butterknife.BindView;

/**
 * Created by CodeManLY on 2017/7/21 0021.
 */

public class JokeContentFragment extends MBaseFragment<JokeContentPresenter> implements JokeContentContract.View, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.joke_content_rv)
    RecyclerView recyclerView;
    @BindView(R.id.joke_swipe)
    SwipeRefreshLayout swipeRefreshLayout;
    private int lastPage = 1;
    private int currentPage = 1;
    private int pageSize = 20;
    private List<JokeContent.DataBean> content;
    private JokeContentAdapter contentAdapter;
    private boolean isErr;
    private boolean refreshTag;


    @Override
    protected void initBefore() {
        super.initBefore();
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.joke_content_news;
    }

    @Override
    public void initView() {
        super.initView();
        recyclerView.setLayoutManager(new LinearLayoutManager(getFragmentComponent().getActivity()));
        contentAdapter = new JokeContentAdapter(R.layout.joke_item_content, content);
        recyclerView.setAdapter(contentAdapter);
//        contentAdapter.disableLoadMoreIfNotFullPage();
    }

    @Override
    public void initListener() {
        super.initListener();
        contentAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        contentAdapter.setNotDoAnimationCount(6);
        contentAdapter.setOnLoadMoreListener(this, recyclerView);
        contentAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currentPage = 1;
                lastPage = 1;
                refreshTag = true;
                mPresenter.getNewsJoke(1, 20);
                contentAdapter.setEnableLoadMore(false);//关闭上拉加载
            }
        });
    }

    @Override
    protected void lazyLoad() {
//        mPresenter.getNewsJoke(currentPage, pageSize);
    }

    @Override
    public void showTimesJoke(JokeContent jokeContent) {
        LUtils.e(jokeContent.toString());
    }

    @Override
    public void showNewsJoke(JokeContent jokeContent) {
        if (currentPage == 1) {
            lastPage = currentPage;
            content = jokeContent.getData();
            contentAdapter.setNewData(content);
            currentPage++;
        } else if (currentPage > 1) {
            lastPage = currentPage;
            content = jokeContent.getData();
            contentAdapter.addData(content);
            currentPage++;

        }
        swipeRefreshLayout.setEnabled(true);//加载完成时可下拉刷新
        contentAdapter.setEnableLoadMore(true);//可上拉加载

        if (refreshTag) {//只有在下拉刷新的时候才会关闭
            closeRefresh(swipeRefreshLayout);
            refreshTag = !refreshTag;
        }
    }

    @Override
    public void onLoadMoreRequested() {
        swipeRefreshLayout.setEnabled(false);//上啦加载时关闭swipeRefreshLayout
        if (content.size() == pageSize) {//相等说明还有下一页
            mPresenter.getNewsJoke(currentPage, pageSize);
            contentAdapter.loadMoreComplete();
        } else if (content.size() < pageSize) {//小于说明没有数据了
            contentAdapter.loadMoreEnd();
            swipeRefreshLayout.setEnabled(true);
        }

    }

    @Override
    public void showErrorMsg(String msg) {
        super.showErrorMsg(msg);
        closeRefresh(swipeRefreshLayout);
    }
}
