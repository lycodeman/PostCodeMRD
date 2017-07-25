package com.lymilestone.postcodemrd.ui.presenter.girls;

import android.app.Activity;

import com.lymilestone.httplibrary.base.mvp.RxPresenter;
import com.lymilestone.httplibrary.utils.log.LUtils;
import com.lymilestone.httplibrary.utils.rx.RxUtils;
import com.lymilestone.postcodemrd.http.observer.CommonObserver;
import com.lymilestone.postcodemrd.http.transform.RxTransform;
import com.lymilestone.postcodemrd.modle.DataManager;
import com.lymilestone.postcodemrd.modle.response.gils.GirlItemData;
import com.lymilestone.postcodemrd.ui.contract.girls.GilsItemContract;
import com.lymilestone.postcodemrd.utils.JsoupUtil;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by CodeManLY on 2017/7/22 0022.
 */

public class GilsItemPresenter extends RxPresenter<GilsItemContract.View> implements GilsItemContract.Presenter{

    private DataManager dataManager;
    private Activity activity;

    @Inject
    public GilsItemPresenter(DataManager dataManager, Activity activity) {
        this.dataManager = dataManager;
        this.activity=activity;
    }

    @Override
    public void fetchGilsItemData(String cid, int pageSize) {
        addSubscribe(dataManager
                .getGirlItemData(cid,pageSize)
                .compose(RxUtils.defaultTransformer())
                .subscribeWith(new CommonObserver<String>(mView) {
                    @Override
                    public void onSuccess(String s) {
                        List<GirlItemData> girlItemDatas = JsoupUtil.parseGirls(s);
                        mView.showGilsItem(girlItemDatas);
                    }
                }));
    }

}
