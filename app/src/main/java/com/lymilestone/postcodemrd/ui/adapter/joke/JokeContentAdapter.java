package com.lymilestone.postcodemrd.ui.adapter.joke;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lymilestone.httplibrary.utils.log.LUtils;
import com.lymilestone.postcodemrd.R;
import com.lymilestone.postcodemrd.modle.response.joke.JokeContent;

import java.util.List;

/**
 * Created by CodeManLY on 2017/7/21 0021.
 */

public class JokeContentAdapter extends BaseQuickAdapter<JokeContent.DataBean,BaseViewHolder>{

    public JokeContentAdapter(@LayoutRes int layoutResId, @Nullable List<JokeContent.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, JokeContent.DataBean item) {
        ((TextView)helper.getView(R.id.joke_item_ct_ct)).setText("\u3000\u3000"+item.getContent());
        ((TextView)helper.getView(R.id.joke_item_ct_time)).setText(item.getUpdatetime());

    }
}
