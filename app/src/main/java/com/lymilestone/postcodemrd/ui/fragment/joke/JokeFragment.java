package com.lymilestone.postcodemrd.ui.fragment.joke;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.lymilestone.httplibrary.utils.log.LUtils;
import com.lymilestone.postcodemrd.R;
import com.lymilestone.postcodemrd.base.fragment.CommonFragment;
import com.lymilestone.postcodemrd.ui.adapter.joke.JokeVpAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * Created by CodeManLY on 2017/7/21 0021.
 */

public class JokeFragment extends CommonFragment {


    @BindView(R.id.joke_tab)
    TabLayout jokeTab;
    @BindView(R.id.joke_vp)
    ViewPager jokeVp;
    private List<Fragment> fragments;
    private List<String> titleList= Arrays.asList("最新笑话","最新趣图","随机");

    @Override
    protected int getLayout() {
        return R.layout.joke;
    }

    @Override
    public void initView() {
        super.initView();
        initTab(titleList);
        fragments = new ArrayList<>();
        fragments.add(new JokeContentFragment());
        fragments.add(new JokeImgFragment());
        fragments.add(new JokeRandomFragment());
    }

    private void initTab(List<String> titles) {
        for (int i = 0; i < titles.size(); i++) {
            jokeTab.addTab(jokeTab.newTab().setText(titleList.get(i)));
        }
    }

    @Override
    protected void lazyLoad() {
        jokeVp.setAdapter(new JokeVpAdapter(getChildFragmentManager(),fragments,titleList));
        jokeTab.setupWithViewPager(jokeVp);
    }

    @Override
    public void fetchData() {
        super.fetchData();

    }

}
