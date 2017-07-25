package com.lymilestone.postcodemrd.ui.adapter.girls;

import android.graphics.Bitmap;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lymilestone.httplibrary.utils.manager.AppManager;
import com.lymilestone.postcodemrd.R;
//import com.lymilestone.postcodemrd.modle.glide.GlideApp;

import java.util.List;

/**
 * Created by CodeManLY on 2017/7/23 0023.
 */

public class GirlsDetailAdapter extends BaseQuickAdapter<Bitmap,BaseViewHolder> {
    public GirlsDetailAdapter(@LayoutRes int layoutResId, @Nullable List<Bitmap> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Bitmap item) {
        ImageView imageView = helper.getView(R.id.girls_dt_img);
        imageView.setImageBitmap(item);
    }
}
