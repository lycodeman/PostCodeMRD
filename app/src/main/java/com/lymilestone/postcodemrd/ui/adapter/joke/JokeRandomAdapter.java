package com.lymilestone.postcodemrd.ui.adapter.joke;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lymilestone.postcodemrd.R;
import com.lymilestone.postcodemrd.modle.response.joke.JokeImgs;
import com.lymilestone.postcodemrd.modle.response.joke.JokePic;

import java.util.List;

/**
 * Created by CodeManLY on 2017/7/21 0021.
 */

public class JokeRandomAdapter extends BaseQuickAdapter<JokePic,BaseViewHolder>{

    public JokeRandomAdapter(@LayoutRes int layoutResId, @Nullable List<JokePic> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, JokePic item) {


    }
}
