package com.lymilestone.postcodemrd.ui.contract.news;

import android.graphics.Bitmap;

import com.lymilestone.httplibrary.base.mvp.BasePresenter;
import com.lymilestone.httplibrary.base.mvp.BaseView;
import com.lymilestone.postcodemrd.modle.response.news.NewsSummary;

import java.util.List;

/**
 * Created by CodeManLY on 2017/7/23 0023.
 */

public interface NewsCondtract {

    interface View extends BaseView{
        void showNews(List<NewsSummary> summaryList);
    }

    interface Presenter extends BasePresenter<View>{
        void handleNews(String type, String id, int startPage);
    }
}
