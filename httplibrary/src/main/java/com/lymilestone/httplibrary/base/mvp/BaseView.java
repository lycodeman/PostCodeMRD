package com.lymilestone.httplibrary.base.mvp;

/**
 * Created by codeest on 2016/8/2.
 * View基类
 */
public interface BaseView {

    void showErrorMsg(String msg);

    void showLoading();

    void dismissLoading();

}
