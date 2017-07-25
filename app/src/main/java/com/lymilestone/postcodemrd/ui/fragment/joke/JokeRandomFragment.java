package com.lymilestone.postcodemrd.ui.fragment.joke;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.MenuItem;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lymilestone.httplibrary.rx.bus.BusFactory;
import com.lymilestone.httplibrary.rx.bus.EventSubscribe;
import com.lymilestone.httplibrary.rx.bus.inner.EventThread;
import com.lymilestone.httplibrary.utils.log.LUtils;
import com.lymilestone.httplibrary.utils.rx.RxUtils;
import com.lymilestone.postcodemrd.R;
import com.lymilestone.postcodemrd.base.mvp.MBaseFragment;
import com.lymilestone.postcodemrd.constants.Constants;
import com.lymilestone.postcodemrd.modle.response.joke.JokePic;
import com.lymilestone.postcodemrd.modle.response.joke.Jokes;
import com.lymilestone.postcodemrd.ui.adapter.joke.JokeRandomPicAdapter;
import com.lymilestone.postcodemrd.ui.adapter.joke.JokesAdapter;
import com.lymilestone.postcodemrd.ui.contract.joke.JokeRandomContract;
import com.lymilestone.postcodemrd.ui.presenter.joke.JokeRandomPresenter;
import com.lymilestone.postcodemrd.utils.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.ObservableSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by CodeManLY on 2017/7/21 0021.
 */

public class JokeRandomFragment extends MBaseFragment<JokeRandomPresenter> implements JokeRandomContract.View {

    @BindView(R.id.joke_random_rv)
    RecyclerView recyclerView;
    @BindView(R.id.bottom_navigation_view)
    BottomNavigationView bottomNavigationView;
    private JokeRandomPicAdapter randomPicAdapter;

    @Override
    protected void initBefore() {
        super.initBefore();
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.joke_random;
    }

    @Override
    public void initView() {
        super.initView();
        BusFactory.getBus().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BusFactory.getBus().unregister(this);
    }

    @Override
    public void initListener() {
        super.initListener();


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.random_pic:
                        mPresenter.getRandomJokePic(Constants.JOKE_RANDOM_PIC);
                        break;
                    case R.id.random_df:
                        mPresenter.getRandomJokes();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

    }
    
    @Override
    protected void lazyLoad() {
        bottomNavigationView.setSelectedItemId(R.id.random_pic);
    }

    @Override
    public void showRandomJokePic(List<JokePic> jokePics) {

        //第一种方式：开启服务去加载图片，处理闪烁问题，可行
//        DataService.startService(activity, jokePics);
        StaggeredGridLayoutManager layout = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        layout.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);//不让它左右滑动，即闪动
        recyclerView.setLayoutManager(layout);
        //地三种方式，监听回调
//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                layout.invalidateSpanAssignments();//滑动时候的闪动问题，压根没用，滑动顶部会留白
//            }
//
//        });
        randomPicAdapter = new JokeRandomPicAdapter(R.layout.joke_item_img, new ArrayList<>());
        randomPicAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        randomPicAdapter.setNotDoAnimationCount(6);
        //设置间隔
//        SpacesItemDecoration decoration = new SpacesItemDecoration(8);
//        recyclerView.addItemDecoration(decoration);
        recyclerView.setAdapter(randomPicAdapter);
        //第二种方式：RxJava去加载图片
        Disposable disposable = RxUtils.createObservable(jokePics)
                .flatMap(new Function<List<JokePic>, ObservableSource<List<JokePic>>>() {
                    @Override
                    public ObservableSource<List<JokePic>> apply(@io.reactivex.annotations.NonNull List<JokePic> jokePics) throws Exception {
                        for (JokePic data : jokePics) {
                            Bitmap bitmap = ImageLoader.load(activity, data.getUrl());
                            if (bitmap != null) {
                                data.setWidth(bitmap.getWidth());
                                data.setHeight(bitmap.getHeight());
                            }
                        }
                        return RxUtils.createObservable(jokePics);
                    }
                })
                .compose(RxUtils.defaultTransformer())
//                .compose(bindToLifecycle())
                .compose(RxUtils.dialogOTransformer(activity, "小编已经很努力了...", true))
                .subscribe(new Consumer<List<JokePic>>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull List<JokePic> jokePics) throws Exception {
                        randomPicAdapter.setNewData(jokePics);
                    }
                });
        addDisposable(disposable);

    }

    @Override
    public void showRandomJokes(List<Jokes> jokes) {
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        JokesAdapter jokesAdapter = new JokesAdapter(R.layout.joke_item_content, jokes);
        jokesAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        jokesAdapter.setNotDoAnimationCount(6);
        recyclerView.setAdapter(jokesAdapter);
    }

    @EventSubscribe(thread = EventThread.MAIN_THREAD)
    public void dataEvent(List<JokePic> data) {
        randomPicAdapter.setNewData(data);
        LUtils.e("=========收到消息=========");
    }

}
