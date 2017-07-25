package com.lymilestone.postcodemrd.base.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lymilestone.httplibrary.base.mvp.BaseView;
import com.lymilestone.httplibrary.utils.log.LUtils;
import com.lymilestone.httplibrary.utils.manager.AppUtils;
import com.lymilestone.httplibrary.utils.snackbar.SnackbarUtil;
import com.lymilestone.httplibrary.utils.toast.ToastUtil;
import com.trello.rxlifecycle2.components.support.RxFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by CodeManLY on 2017/7/20 0020.
 */

public abstract class CommonFragment extends RxFragment implements BaseView{

    /*懒加载处理*/
    private boolean isVisible = true;//是否可见
    private boolean isPrepared;//是否初始化完成
    private boolean isFirstLoad = true;//是否第一次加载
    protected View rootView;
    /*rx事件回收*/
    private CompositeDisposable compositeDisposable;
    private SparseArray<View> mViews;
    public Activity activity;
    private static final String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";
    private Unbinder bind;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity= (Activity) context;//解决getActivity（）报空指针的情况,在fragment中使用这个
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*保存fragment，由于内存重启造成的崩溃*/
        /*使用时要在activity中执行savedInstanceState的判断*/
//        if (savedInstanceState != null) {
//            boolean isSupportHidden = savedInstanceState.getBoolean(STATE_SAVE_IS_HIDDEN);
//            FragmentTransaction ft = getFragmentManager().beginTransaction();
//            if (isSupportHidden) {
//                ft.hide(this);
//            } else {
//                ft.show(this);
//            }
//            ft.commit();
//        }

        initBefore();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        isFirstLoad= true;
        rootView = inflater.inflate(getLayout(),container,false);
        bind = ButterKnife.bind(this, rootView);
        isPrepared=true;
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initListener();
        fetchData();
        onVisible();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        onUnsubscribe();
        bind.unbind();
    }

    protected void initBefore() {

    }

    public  void initView(){}

    /**
     * 初始化监听器
     */
    public void initListener(){}

    /**
     * 初始化数据
     */
    public void fetchData(){}

    /**
     * 获取布局id
     * @return
     */
    protected abstract int getLayout();

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
    protected void onUnsubscribe() {
        if (compositeDisposable != null && compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

    protected void toast(String msg){
        ToastUtil.shortShow(msg);
    }

     /*-------------------------------懒加载操作-----------------------------------*/
    /**
     * 在这里实现Fragment数据的缓加载.
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(getUserVisibleHint()) {//可见时进入
            isVisible = true;
            onVisible();
        } else {//不可见时进入
            isVisible = false;
            onInvisible();
        }
    }

    /**
     * 如果是通过FragmentTransaction的show和hide的方法来控制显示，调用的是onHiddenChanged.
     * 若是初始就show的Fragment 为了触发该事件 需要先hide再show
     * @param hidden hidden True if the fragment is now hidden, false if it is not
     *               visible.
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    protected void onVisible(){
        if (!isPrepared || !isVisible || !isFirstLoad) {//全部满足才会加载
            return;
        }
        isFirstLoad = false;
        lazyLoad();
    }

    /**
     * 要实现延迟加载Fragment内容,需要在 onCreateView
     * isPrepared = true;
     */
    protected abstract void lazyLoad() ;

    /**
     * 这里使用了viewepager暂时用不到onInvisible()
     * 不可见时的操作
     */
    protected void onInvisible(){}

    /*------------------------------事件绑定------------------------------*/
    protected <E extends View> E F(View mVew,int viewId) {
        E view = (E) mViews.get(viewId);
        if (view == null) {
            view = (E) mVew.findViewById(viewId);
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
        SnackbarUtil.show(((ViewGroup) getActivity().findViewById(android.R.id.content)).getChildAt(0), msg);
    }

    protected void closeRefresh(SwipeRefreshLayout swipeRefreshLayout){
        if (AppUtils.checkNotNull(swipeRefreshLayout).isRefreshing()){
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    protected void openRefresh(SwipeRefreshLayout swipeRefreshLayout) {
        if (!(AppUtils.checkNotNull(swipeRefreshLayout).isRefreshing())) {
            swipeRefreshLayout.setRefreshing(true);
        }
    }
}
