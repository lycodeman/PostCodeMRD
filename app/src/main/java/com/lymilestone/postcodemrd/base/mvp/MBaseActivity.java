package com.lymilestone.postcodemrd.base.mvp;

import android.view.ViewGroup;

import com.lymilestone.httplibrary.base.mvp.BasePresenter;
import com.lymilestone.httplibrary.base.mvp.BaseView;
import com.lymilestone.httplibrary.utils.snackbar.SnackbarUtil;
import com.lymilestone.postcodemrd.base.activity.CommonActivity;
import javax.inject.Inject;

/**
 * Created by CodeManLY on 2017/7/20 0020.
 */

public abstract class MBaseActivity<T extends BasePresenter> extends CommonActivity {

    @Inject
    protected T mPresenter;

    @Override
    protected void onViewCreate() {
        super.onViewCreate();
        if (mPresenter!=null){
            mPresenter.attachView(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter!=null){
            mPresenter.detachView();
        }
    }

    @Override
    public void showErrorMsg(String msg) {
        SnackbarUtil.show(((ViewGroup) findViewById(android.R.id.content)).getChildAt(0), msg);
    }
}
