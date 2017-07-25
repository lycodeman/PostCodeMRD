package com.lymilestone.postcodemrd.ui.adapter.girls;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lymilestone.httplibrary.utils.manager.AppManager;
import com.lymilestone.postcodemrd.R;
import com.lymilestone.postcodemrd.modle.response.gils.GirlItemData;
import com.lymilestone.postcodemrd.widget.ScaleImageView;

import java.util.List;

/**
 * Created by CodeManLY on 2017/7/22 0022.
 */

public class GirlsItemAdapter extends BaseQuickAdapter<GirlItemData,BaseViewHolder> {

    public GirlsItemAdapter(@LayoutRes int layoutResId, @Nullable List<GirlItemData> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GirlItemData item) {
        ScaleImageView scaleImageView=helper.getView(R.id.gilrs_item_img);
        scaleImageView.setInitSize(item.getWidth(),item.getHeight());
        Glide.with(AppManager.appContext())
                .load(item.getUrl())
                .into(scaleImageView);
    }

}
