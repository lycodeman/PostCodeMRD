package com.lymilestone.postcodemrd.ui.fragment.girls;

import android.graphics.Bitmap;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.lymilestone.httplibrary.utils.statusbar.StatusBarUtil;
import com.lymilestone.postcodemrd.R;
import com.lymilestone.postcodemrd.base.mvp.MBaseFragment;
import com.lymilestone.postcodemrd.base.mvp.MDBaseFragment;
import com.lymilestone.postcodemrd.modle.response.gils.GirlItemData;
import com.lymilestone.postcodemrd.ui.activity.MainActivity;
import com.lymilestone.postcodemrd.ui.adapter.girls.GirlsDetailAdapter;
import com.lymilestone.postcodemrd.ui.contract.girls.GilsItemContract;
import com.lymilestone.postcodemrd.ui.contract.girls.GirlsDetailCondtract;
import com.lymilestone.postcodemrd.ui.presenter.girls.GirlsDetailPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by CodeManLY on 2017/7/23 0023.
 */

public class GirlsDetailFragment extends MDBaseFragment<GirlsDetailPresenter> implements GirlsDetailCondtract.View {

    @BindView(R.id.gilrs_dt_vp)
    RecyclerView recyclerView;
    @BindView(R.id.gilrs_dt_toolbar)
    Toolbar toolbar;
    private String url;
    private String title;
    private GirlsDetailAdapter girlsDetailAdapter;


    @Override
    protected void initBefore() {
        super.initBefore();
        getFragmentComponent().inject(this);
    }

    @Override
    public void initView() {
        super.initView();
        url = getArguments().getString("url", "");
        title = getArguments().getString("title", "");
        toolbar.setTitle(title);
        toolbar.setNavigationIcon(R.mipmap.back);

        girlsDetailAdapter = new GirlsDetailAdapter(R.layout.girls_detail_fg_item, new ArrayList<Bitmap>());
        //最后一个参数是反转布局，true，显示最后一个用于聊天界面，false，显示第一个
        LinearLayoutManager layout = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layout);
        recyclerView.setAdapter(girlsDetailAdapter);
    }

    @Override
    public void initListener() {
        super.initListener();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.girls_detail_fg;
    }

    @Override
    protected void onVisible() {
        mPresenter.fetchGilsDetaileData(url);
    }

    @Override
    public void showGilsDetaile(List<Bitmap> bitmaps) {
        girlsDetailAdapter.setNewData(bitmaps);
    }
}
