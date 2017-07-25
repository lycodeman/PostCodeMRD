package com.lymilestone.postcodemrd.base.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.lymilestone.httplibrary.base.mvp.BaseView;
import com.lymilestone.httplibrary.utils.log.LUtils;
import com.lymilestone.httplibrary.utils.manager.AppUtils;
import com.lymilestone.httplibrary.utils.snackbar.SnackbarUtil;
import com.lymilestone.httplibrary.utils.statusbar.StatusBarUtil;
import com.lymilestone.httplibrary.utils.toast.ToastUtil;
import com.lymilestone.postcodemrd.R;
import com.trello.rxlifecycle2.components.support.RxDialogFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by CodeManLY on 2017/7/20 0020.
 */

public abstract class CommonDialogFragment extends RxDialogFragment implements BaseView{

    /*懒加载处理*/
//    private boolean isPrepared =true;
//    private boolean isVisible;
//    private boolean isFirstLoad =true;
    protected View rootView;
    /*rx事件回收*/
    private CompositeDisposable compositeDisposable;
    private SparseArray<View> mViews;
    public Activity activity;
    private static final String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";
    private Unbinder unbinder;

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
//        setStyle(RxDialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
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

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = new View(activity);

        final Dialog dialog = new Dialog(getActivity(), R.style.style_dialog);
        dialog.setContentView(view);
        dialog.show();

        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM); //可设置dialog的位置
        window.getDecorView().setPadding(0, 0, 0, 0); //消除边距
//        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        StatusBarUtil.setColor(activity, activity.getResources().getColor(R.color.colorPrimary));
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;   //设置宽度充满屏幕
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
        return dialog;

    }

    protected void initBefore() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        isFirstLoad= true;
        rootView = inflater.inflate(getLayout(),container,false);
        unbinder = ButterKnife.bind(this, rootView);
//        isPrepared=true;
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
        unbinder.unbind();
        onUnsubscribe();
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
    //dialogfragment无懒加载操作，因为其setUserVisibleHint不会回调
    protected abstract void onVisible();

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
