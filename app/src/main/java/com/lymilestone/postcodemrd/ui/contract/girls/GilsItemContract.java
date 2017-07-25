package com.lymilestone.postcodemrd.ui.contract.girls;

import com.lymilestone.httplibrary.base.mvp.BasePresenter;
import com.lymilestone.httplibrary.base.mvp.BaseView;
import com.lymilestone.postcodemrd.modle.response.gils.GirlItemData;
import com.lymilestone.postcodemrd.modle.response.joke.JokeContent;

import java.util.List;

/**
 * Created by CodeManLY on 2017/7/20 0020.
 */

public interface GilsItemContract {

    interface View extends BaseView{
        void showGilsItem(List<GirlItemData> girlItemDatas);

    }

    interface Presenter extends BasePresenter<View>{
        void fetchGilsItemData(String cid,int pageSize);


    }
}
