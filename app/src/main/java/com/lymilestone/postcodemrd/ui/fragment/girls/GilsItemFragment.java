package com.lymilestone.postcodemrd.ui.fragment.girls;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lymilestone.httplibrary.utils.log.LUtils;
import com.lymilestone.httplibrary.utils.rx.RxUtils;
import com.lymilestone.postcodemrd.R;
import com.lymilestone.postcodemrd.base.mvp.MBaseFragment;
import com.lymilestone.postcodemrd.constants.Constants;
import com.lymilestone.postcodemrd.modle.response.gils.GirlItemData;
import com.lymilestone.postcodemrd.ui.adapter.girls.GirlsItemAdapter;
import com.lymilestone.postcodemrd.ui.contract.girls.GilsItemContract;
import com.lymilestone.postcodemrd.ui.presenter.girls.GilsItemPresenter;
import com.lymilestone.postcodemrd.utils.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.ObservableSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by CodeManLY on 2017/7/22 0022.
 */

public class GilsItemFragment extends MBaseFragment<GilsItemPresenter> implements GilsItemContract.View, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.gils_item_rv)
    RecyclerView recyclerView;
    @BindView(R.id.girls_swip)
    SwipeRefreshLayout swipeRefreshLayout;
    private GirlsItemAdapter girlsItemAdapter;
    private int pageSize=1;
    private String cids;
    private List<GirlItemData> girlItemDataList=new ArrayList<>();

    @Override
    protected void initBefore() {
        super.initBefore();
        getFragmentComponent().inject(this);
    }

    @Override
    public void initView() {
        super.initView();
        girlsItemAdapter = new GirlsItemAdapter(R.layout.girls_item, new ArrayList<>());
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(girlsItemAdapter);
        cids = getArguments().getString("cids");

    }

    @Override
    public void initListener() {
        super.initListener();
        girlsItemAdapter.setOnLoadMoreListener(this,recyclerView);
        girlsItemAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                LUtils.e("=======================");
                String id = girlItemDataList.get(position).getId();
                String title = girlItemDataList.get(position).getTitle();
                GirlsDetailFragment girlsDetailFragment=new GirlsDetailFragment();
                Bundle args = new Bundle();
                args.putString("url",id);
                args.putString("title",title);
                girlsDetailFragment.setArguments(args);
                girlsDetailFragment.show(getChildFragmentManager(),GirlsDetailFragment.class.getSimpleName());
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageSize=1;
                mPresenter.fetchGilsItemData(cids,pageSize);
                girlsItemAdapter.setEnableLoadMore(false);
            }
        });
    }

    @Override
    public void showGilsItem(List<GirlItemData> girlItemDatas) {
        Disposable disposable = RxUtils.createObservable(girlItemDatas)
                .flatMap(new Function<List<GirlItemData>, ObservableSource<List<GirlItemData>>>() {
                    @Override
                    public ObservableSource<List<GirlItemData>> apply(@io.reactivex.annotations.NonNull List<GirlItemData> girlItemDatas) throws Exception {
                        for (GirlItemData data : girlItemDatas) {
                            Bitmap bitmap = ImageLoader.load(activity, data.getUrl());
                            if (bitmap != null) {
                                data.setWidth(bitmap.getWidth());
                                data.setHeight(bitmap.getHeight());
                            }
                        }
                        return RxUtils.createObservable(girlItemDatas);
                    }
                })
                .compose(RxUtils.defaultTransformer())
                .compose(bindToLifecycle())
                .compose(RxUtils.dialogOTransformer(activity, "小编已经很努力了...", true))
                .subscribe(new Consumer<List<GirlItemData>>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull List<GirlItemData> girlItemDatas) throws Exception {
                        if (pageSize==1){
                            girlItemDataList.clear();
                            girlsItemAdapter.setNewData(girlItemDatas);
                        }else if (pageSize>1){
                            girlsItemAdapter.addData(girlItemDatas);
                        }
                        girlItemDataList.addAll(girlItemDatas);
                        pageSize++;
                        swipeRefreshLayout.setEnabled(true);
                        girlsItemAdapter.setEnableLoadMore(true);
                    }
                });
        addDisposable(disposable);
    }

    @Override
    protected int getLayout() {
        return R.layout.gils_item;
    }

    @Override
    protected void lazyLoad() {
        LUtils.e("=================================="+Constants.SUM++);
        mPresenter.fetchGilsItemData(cids,pageSize);
    }

    @Override
    public void onLoadMoreRequested() {
        swipeRefreshLayout.setEnabled(false);
        mPresenter.fetchGilsItemData(cids,pageSize);
    }
}
