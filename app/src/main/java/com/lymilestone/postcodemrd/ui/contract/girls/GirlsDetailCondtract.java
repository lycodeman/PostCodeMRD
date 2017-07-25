package com.lymilestone.postcodemrd.ui.contract.girls;

import android.graphics.Bitmap;

import com.lymilestone.httplibrary.base.mvp.BasePresenter;
import com.lymilestone.httplibrary.base.mvp.BaseView;

import java.util.List;

/**
 * Created by CodeManLY on 2017/7/23 0023.
 */

public interface GirlsDetailCondtract {

    interface View extends BaseView{
        void showGilsDetaile(List<Bitmap> bitmaps);
    }

    interface Presenter extends BasePresenter<View>{
        void fetchGilsDetaileData(String id);
    }
}
