package com.lymilestone.postcodemrd.ui.adapter.joke;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lymilestone.httplibrary.utils.manager.AppManager;
import com.lymilestone.postcodemrd.R;
import com.lymilestone.postcodemrd.modle.response.joke.JokeImgs;
import com.lymilestone.postcodemrd.widget.ScaleImageView;

import java.util.List;

/**
 * Created by CodeManLY on 2017/7/21 0021.
 */

public class JokeImgAdapter extends BaseQuickAdapter<JokeImgs.DataBean,BaseViewHolder>{

    public JokeImgAdapter(@LayoutRes int layoutResId, @Nullable List<JokeImgs.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, JokeImgs.DataBean item) {
        String[] split = item.getUrl().split("\\.");
        ScaleImageView imageView = (ScaleImageView) helper.getView(R.id.joke_item_img);
        imageView.setInitSize(item.getWidth(),item.getHeight());
        if (split[split.length-1].equals("gif")){
            Glide.with(AppManager.appContext())
                    .load(item.getUrl())
                    .asGif()
                    .centerCrop()
                    .into((ScaleImageView) helper.getView(R.id.joke_item_img));
        }else {
            Glide.with(AppManager.appContext())
                    .load(item.getUrl())
                    .centerCrop()
                    .into(imageView);
        }
//        ((TextView)helper.getView(R.id.joke_item_img_tv)).setText(item.getContent());
    }
}
