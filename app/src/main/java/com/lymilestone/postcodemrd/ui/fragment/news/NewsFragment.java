package com.lymilestone.postcodemrd.ui.fragment.news;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import com.lymilestone.httplibrary.rx.bus.BusFactory;
import com.lymilestone.httplibrary.rx.bus.EventSubscribe;
import com.lymilestone.httplibrary.rx.bus.inner.EventThread;
import com.lymilestone.httplibrary.utils.log.LUtils;
import com.lymilestone.httplibrary.utils.manager.AppManager;
import com.lymilestone.postcodemrd.R;
import com.lymilestone.postcodemrd.base.fragment.CommonFragment;
import com.lymilestone.postcodemrd.constants.Constants;
import com.lymilestone.postcodemrd.modle.event.DrawerEvent;
import com.lymilestone.postcodemrd.modle.event.ToolBarAlphaEvent;
import com.lymilestone.postcodemrd.modle.response.news.NewsChannelTable;
import com.lymilestone.postcodemrd.ui.adapter.news.NewsFragmentVPAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * Created by CodeManLY on 2017/7/24 0024.
 */

public class NewsFragment extends CommonFragment{

    @BindView(R.id.news_toolbar)
    Toolbar toolbar;
    @BindView(R.id.news_vp)
    ViewPager viewPager;
    @BindView(R.id.news_tabs)
    TabLayout tabLayout;
    private NewsFragmentVPAdapter newsFragmentVPAdapter;
    private List<Fragment> fragmentList;
    private static ArrayList<NewsChannelTable> newsChannelTables;
    private ViewGroup.LayoutParams layoutParams;
    private float min;
    private float up;
    private float down;

    @Override
    protected void initBefore() {
        super.initBefore();
        BusFactory.getBus().register(this);
    }

    @Override
    public void initView() {
        super.initView();
        loadNewsChannelsStatic();
        for (int i = 0; i < newsChannelTables.size(); i++) {
            tabLayout.addTab(tabLayout.newTab().setText(newsChannelTables.get(i).getNewsChannelName()));
        }
        layoutParams = toolbar.getLayoutParams();
        toolbar.setTitle("新闻");
        newsFragmentVPAdapter=new NewsFragmentVPAdapter(
                getChildFragmentManager(),fragmentList,newsChannelTables);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(newsFragmentVPAdapter);
    }

    @Override
    public void initListener() {
        super.initListener();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BusFactory.getBus().post(new DrawerEvent(true));
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BusFactory.getBus().unregister(this);
    }

    @EventSubscribe(thread = EventThread.MAIN_THREAD)
    public void setToolBarrAlpha(ToolBarAlphaEvent toolBarrAlpha){


        LUtils.e(up +"======"+toolbar.getHeight());
        if (toolBarrAlpha.getAlpha()>0){
//            up = toolBarrAlpha.getAlpha()+ up;
//            if (up >toolbar.getHeight())
//                return;
//            min = 1f- Math.min(1f, up / toolbar.getHeight());
//            toolbar.setAlpha(1f);
        }else if (toolBarrAlpha.getAlpha()<0){
//            toolbar.setAlpha(0f);
//            down=Math.abs(toolBarrAlpha.getAlpha())+down;
//            if (down>toolbar.getHeight())
//                return;
//                toolbar.setAlpha(0f);
//            min=Math.min(1f,down/toolbar.getHeight());
        }

    }

    @Override
    protected int getLayout() {
        return R.layout.news_fragment;
    }

    @Override
    protected void lazyLoad() {

    }

    /**
     * 加载固定新闻类型
     * @return
     */
    public void loadNewsChannelsStatic() {
        List<String> channelName = Arrays.asList(AppManager.appContext().getResources().getStringArray(R.array.news_channel_name_static));
        List<String> channelId = Arrays.asList(AppManager.appContext().getResources().getStringArray(R.array.news_channel_id_static));
        newsChannelTables = new ArrayList<>();
        fragmentList=new ArrayList<>();
        for (int i = 0; i < channelName.size(); i++) {
            NewsChannelTable entity = new NewsChannelTable(channelName.get(i), channelId.get(i)
                    , getType(channelId.get(i)), i <= 5, i, true);
            newsChannelTables.add(entity);
            NewsDTFragment newsDTFragment = new NewsDTFragment();
            Bundle args = new Bundle();
            args.putSerializable("NewsChannelTable",entity);
            newsDTFragment.setArguments(args);
            fragmentList.add(newsDTFragment);
        }
    }

    /**
     * 新闻id获取类型
     *
     * @param id 新闻id
     * @return 新闻类型
     */
    public String getType(String id) {
        switch (id) {
            case Constants.HEADLINE_ID:
                return Constants.HEADLINE_TYPE;
            case Constants.HOUSE_ID:
                return Constants.HOUSE_TYPE;
            default:
                break;
        }
        return Constants.OTHER_TYPE;
    }
}
