package com.lymilestone.postcodemrd.http.observer;

import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.lymilestone.httplibrary.base.mvp.BaseView;
import com.lymilestone.httplibrary.rx.exception.HttpResponseException;
import com.lymilestone.httplibrary.utils.log.LUtils;
import com.lymilestone.httplibrary.utils.manager.AppManager;
import com.lymilestone.httplibrary.utils.toast.ToastUtil;
import com.lymilestone.postcodemrd.http.exception.ApiException;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

/**
 * <p>Description:
 * <p>
 * <p>Created by Devin Sun on 2017/3/29.
 */

public abstract class CommonObserver<T> extends DisposableObserver<T> {

    BaseView baseView;

    public CommonObserver(BaseView baseView) {
        this.baseView = baseView;
    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        LUtils.e(e.getMessage());
        Log.e("","----------------数据亲求异常解析-------------------");
        if (e instanceof ApiException){
//            HttpResponseException responseException=new HttpResponseException("自定义异常出错",007);
//            if (e.getMessage().equals("密码错误")){
//                responseException = new HttpResponseException("密码错误", ((HttpResponseException) e).getStatus());
//            }else if (e.getMessage().equals("token过期")){
//                responseException = new HttpResponseException("认证失败", ((HttpResponseException) e).getStatus());
//                //此处添加跳转到登陆界面
////            SPUtils.put("firstLogin", false);
////            AppManager.jumpAndFinish(LoginActivity.class);//跳转到登陆界面
//            }else if (e.getMessage().contains("4")){
//                responseException = new HttpResponseException("参数错误", ((HttpResponseException) e).getStatus());
//            }else if (e.getMessage().contains("5")){
//                responseException = new HttpResponseException("主机错误", ((HttpResponseException) e).getStatus());
//            }
            onFailed((ApiException) e);
        } else if (e instanceof ConnectException || e instanceof UnknownHostException) {
            baseView.showErrorMsg("无法连接服务器");
        } else if (e instanceof TimeoutException || e instanceof SocketTimeoutException) {
            baseView.showErrorMsg("连接超时，请稍后再试！");
        } else if (e instanceof JsonSyntaxException || e instanceof JsonIOException ||
                e instanceof JsonParseException || e instanceof JSONException) {
            baseView.showErrorMsg("解析失败");
        } else if (e.getMessage().contains("40")) {
            baseView.showErrorMsg("参数错误");
        } else if (e instanceof NullPointerException) {
            baseView.showErrorMsg("数据为空");
        } else {
            baseView.showErrorMsg("未知错误");
        }
    }

    @Override
    public void onComplete() {
    }

    public abstract void onSuccess(T t);

    protected void onFailed(ApiException apiException) {
        baseView.showErrorMsg(apiException.getMessage() + "(" + apiException.getCode() + ")");
    }
}
