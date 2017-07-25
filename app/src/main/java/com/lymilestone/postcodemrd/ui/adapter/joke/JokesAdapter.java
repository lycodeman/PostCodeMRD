package com.lymilestone.postcodemrd.ui.adapter.joke;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lymilestone.httplibrary.utils.date.DateUtil;
import com.lymilestone.postcodemrd.R;
import com.lymilestone.postcodemrd.modle.response.joke.JokeContent;
import com.lymilestone.postcodemrd.modle.response.joke.Jokes;

import java.util.List;

/**
 * Created by CodeManLY on 2017/7/21 0021.
 */

public class JokesAdapter extends BaseQuickAdapter<Jokes,BaseViewHolder>{

    public JokesAdapter(@LayoutRes int layoutResId, @Nullable List<Jokes> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Jokes item) {
        ((TextView)helper.getView(R.id.joke_item_ct_ct)).setText("\u3000\u3000"+item.getContent());
        ((TextView)helper.getView(R.id.joke_item_ct_time)).setText(DateUtil.getYmdhms(Long.parseLong(item.getUnixtime())*1000l));

    }
}
