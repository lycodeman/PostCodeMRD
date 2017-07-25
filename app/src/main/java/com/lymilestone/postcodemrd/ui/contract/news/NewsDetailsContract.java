package com.lymilestone.postcodemrd.ui.contract.news;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.lymilestone.httplibrary.base.mvp.BasePresenter;
import com.lymilestone.httplibrary.base.mvp.BaseView;
import com.lymilestone.postcodemrd.modle.response.news.NewsDetail;

/**
 * Created by CodeManLY on 2017/7/25 0025.
 */

public interface NewsDetailsContract {
    interface View extends BaseView{
        void showNewsDetail(NewsDetail newsDetail);
        void showImg(Bitmap bitmap);
    }
    interface Presenter extends BasePresenter<View>{
        void handleData(String postId);
        void fetchImg(ImageView imageView,String url);
    }
}
