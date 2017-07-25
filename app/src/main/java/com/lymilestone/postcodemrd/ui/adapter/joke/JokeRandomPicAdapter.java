package com.lymilestone.postcodemrd.ui.adapter.joke;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lymilestone.httplibrary.utils.manager.AppManager;
import com.lymilestone.postcodemrd.R;
import com.lymilestone.postcodemrd.modle.response.joke.JokePic;
import com.lymilestone.postcodemrd.widget.ScaleImageView;

import java.util.List;

/**
 * Created by CodeManLY on 2017/7/21 0021.
 */

public class JokeRandomPicAdapter extends BaseQuickAdapter<JokePic,BaseViewHolder>{

    public JokeRandomPicAdapter(@LayoutRes int layoutResId, @Nullable List<JokePic> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, JokePic item) {
        ScaleImageView imageView =  helper.getView(R.id.joke_item_img);
        imageView.setInitSize(item.getWidth(),item.getHeight());
        String[] split = item.getUrl().split("\\.");
        if (split[split.length-1].equals("gif")){
            Glide.with(AppManager.appContext())

                    .load(item.getUrl())
                    .asGif()
//                    .override(width,height)
                    .centerCrop()
                    .into(imageView);
        }else {
            Glide.with(AppManager.appContext())

                    .load(item.getUrl())
                    .asBitmap()
//                    .override(width,height)
                    .centerCrop()
                    .into(imageView);
        }
//        ((TextView)helper.getView(R.id.joke_item_img_tv)).setText(item.getContent());

    }
}
