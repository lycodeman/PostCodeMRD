package com.lymilestone.postcodemrd.ui.activity.news;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lymilestone.httplibrary.utils.log.LUtils;
import com.lymilestone.postcodemrd.R;
import com.lymilestone.postcodemrd.base.activity.CommonActivity;
import com.lymilestone.postcodemrd.base.mvp.MBaseActivity;
import com.lymilestone.postcodemrd.modle.response.news.NewsPhotoDetail;

import butterknife.BindView;

/**
 * Created by CodeManLY on 2017/7/25 0025.
 */

public class NewsDetailPhotoActivity extends CommonActivity {

    @BindView(R.id.news_photo_tb)
    Toolbar toolbar;
    @BindView(R.id.news_photo_vp)
    ViewPager viewPager;
    private NewsPhotoDetail newsPhotoDetail;

    @Override
    public int getLayout() {
        return R.layout.news_photo;
    }

    @Override
    public void initView() {
        Intent intent = getIntent();
        newsPhotoDetail = intent.getParcelableExtra("newsPhotoDetail");
        toolbar.setTitle(newsPhotoDetail.getTitle());
        viewPager.setAdapter(new PagerAdapter() {

            private View inflate;

            @Override
            public int getCount() {
                LUtils.e(newsPhotoDetail.getPictures().size()+"=============");
                return newsPhotoDetail.getPictures().size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view==object;
            }

            // PagerAdapter只缓存三张要显示的图片，如果滑动的图片超出了缓存的范围，就会调用这个方法，将图片销毁
            @Override
            public void destroyItem(ViewGroup view, int position, Object object) {
                view.removeView((View) object);
            }

            // 当要显示的图片可以进行缓存的时候，会调用这个方法进行显示图片的初始化，我们将要显示的ImageView加入到ViewGroup中，然后作为返回值返回即可
            @Override
            public Object instantiateItem(ViewGroup view, int position) {
                inflate = LayoutInflater.from(NewsDetailPhotoActivity.this).inflate(R.layout.news_photo_item, view, false);
                ImageView imageView=inflate.findViewById(R.id.news_photo_item_img);
                TextView textView=inflate.findViewById(R.id.news_photo_item_tv);
                NewsPhotoDetail.Picture picture = newsPhotoDetail.getPictures().get(position);
                Glide.with(view.getContext()).load(picture.getImgSrc()).into(imageView);
                textView.setText(picture.getTitle());
                view.addView(inflate);
                return inflate;
            }

        });
    }

    @Override
    public void initListener() {
        super.initListener();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
