package com.lymilestone.postcodemrd.base.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.lymilestone.httplibrary.base.mvp.BasePresenter;
import com.lymilestone.httplibrary.base.mvp.BaseView;
import com.lymilestone.postcodemrd.base.application.App;
import com.lymilestone.postcodemrd.base.fragment.CommonDialogFragment;
import com.lymilestone.postcodemrd.base.fragment.CommonFragment;
import com.lymilestone.postcodemrd.dagger.component.DaggerFragmentComponent;
import com.lymilestone.postcodemrd.dagger.component.FragmentComponent;
import com.lymilestone.postcodemrd.dagger.modlue.FragmentModule;

import javax.inject.Inject;

/**
 * Created by CodeManLY on 2017/7/20 0020.
 */

public abstract class MDBaseFragment<T extends BasePresenter> extends CommonDialogFragment implements BaseView{

    @Inject
    protected T mPresenter;

    protected FragmentComponent getFragmentComponent(){
        return DaggerFragmentComponent.builder()
                .appComponent(App.getAppComponent())
                .fragmentModule(getFragmentModule())
                .build();
    }

    protected FragmentModule getFragmentModule(){
        return new FragmentModule(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        if (mPresenter!=null){
            mPresenter.attachView(this);
        }
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        if (mPresenter!=null){
            mPresenter.detachView();
        }
        super.onDestroyView();
    }


}
