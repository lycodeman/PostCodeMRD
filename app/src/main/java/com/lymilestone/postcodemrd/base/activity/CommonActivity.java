package com.lymilestone.postcodemrd.base.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.lymilestone.httplibrary.base.mvp.BaseView;
import com.lymilestone.httplibrary.utils.snackbar.SnackbarUtil;
import com.lymilestone.httplibrary.utils.toast.ToastUtil;
import com.lymilestone.postcodemrd.base.application.App;
import com.lymilestone.postcodemrd.dagger.component.ActivityComponent;
import com.lymilestone.postcodemrd.dagger.component.DaggerActivityComponent;
import com.lymilestone.postcodemrd.dagger.modlue.ActivityModule;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by CodeManLY on 2017/7/19 0019.
 */

public abstract class CommonActivity extends RxAppCompatActivity implements BaseView {

    private CompositeDisposable compositeDisposable;
    private SparseArray<View> mViews;
    private Unbinder bind;


    protected ActivityComponent getActivityComponent(){
        return DaggerActivityComponent.builder()
                .activityModule(getActivityModule())
                .appComponent(App.getAppComponent())
                .build();
    }

    protected ActivityModule getActivityModule(){
        return new ActivityModule(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        initBefore();
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        //针对在styles.xml中设置不起作用时调用
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            Window window = getWindow();
//            window.setFlags(
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        }

        bind = ButterKnife.bind(this);
        mViews=new SparseArray<>();
        onViewCreate();
        initView();
        initListener();
        fetchData();
    }

    protected void onViewCreate() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
        releaseResource();
    }

    public void releaseResource(){}

    public void fetchData() {}

    public void initListener() {}

    public void initBefore() {}

    public abstract int getLayout();
    public abstract void initView();

    public void toast(String msg){
        ToastUtil.shortShow(msg);
    }

    /**
     * 所有rx订阅后，需要调用此方法，用于在detachView时取消订阅
     */
    protected void addDisposable(Disposable disposable) {
        if (compositeDisposable == null)
            compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(disposable);
    }

    /**
     * 取消本页面所有订阅
     */
    protected void onDisposable() {
        if (compositeDisposable != null && compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

    protected <E extends View> E F(int viewId) {
        E view = (E) mViews.get(viewId);
        if (view == null) {
            view = (E) findViewById(viewId);
            mViews.put(viewId, view);
        }
        return view;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void showErrorMsg(String msg) {
        SnackbarUtil.show(((ViewGroup) findViewById(android.R.id.content)).getChildAt(0), msg);
    }

    protected Toolbar setToolBar(Toolbar toolbar, String title) {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        return toolbar;
    }

    protected void hideToolBar(){
        if (getSupportActionBar().isShowing())
            getSupportActionBar().hide();
    }

    protected void showToolBar() {
        if (!getSupportActionBar().isShowing())
            getSupportActionBar().show();
    }

}
