package com.lymilestone.postcodemrd.ui.fragment.girls;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.lymilestone.postcodemrd.R;
import com.lymilestone.postcodemrd.base.fragment.CommonFragment;
import com.lymilestone.postcodemrd.ui.adapter.girls.GirlsItemAdapter;
import com.lymilestone.postcodemrd.ui.adapter.girls.GirlsVPAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * Created by CodeManLY on 2017/7/22 0022.
 */

public class GilsFragment extends CommonFragment{

    @BindView(R.id.gils_tab)
    TabLayout tabLayout;
    @BindView(R.id.gils_vp)
    ViewPager viewPager;
    private String[] stringArray;
    private String[] cids;
    private List<Fragment> fragmentList;


    @Override
    public void initView() {
        super.initView();
    }

    @Override
    protected int getLayout() {
        return R.layout.gils_fg;
    }

    @Override
    protected void lazyLoad() {
        fragmentList=new ArrayList<>();
        stringArray = activity.getResources().getStringArray(R.array.girl);
        cids=activity.getResources().getStringArray(R.array.girl_cid);
        for (int i = 0; i < stringArray.length; i++) {
            tabLayout.addTab(tabLayout.newTab().setText(stringArray[i]));
            GilsItemFragment fragment = new GilsItemFragment();
            Bundle args = new Bundle();
            args.putString("cids",cids[i]);
            fragment.setArguments(args);
            fragmentList.add(fragment);
        }
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(
                new GirlsVPAdapter(getChildFragmentManager(),fragmentList, Arrays.asList(stringArray)));
    }


}
