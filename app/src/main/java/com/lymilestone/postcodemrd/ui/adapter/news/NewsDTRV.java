package com.lymilestone.postcodemrd.ui.adapter.news;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lymilestone.httplibrary.utils.log.LUtils;
import com.lymilestone.postcodemrd.R;
import com.lymilestone.postcodemrd.modle.response.news.NewsSummary;
import com.lymilestone.postcodemrd.utils.DisplayUtil;

import java.util.List;


/**
 * Created by CodeManLY on 2017/7/24 0024.
 */

public class NewsDTRV extends BaseMultiItemQuickAdapter<NewsSummary,BaseViewHolder> {

    private Fragment fragment;
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public NewsDTRV(Fragment fragment,List<NewsSummary> data) {
        super(data);
        this.fragment=fragment;
        addItemType(NewsSummary.TYPE_ITEM, R.layout.news_detail_item);
        addItemType(NewsSummary.TYPE_PHOTO_ITEM, R.layout.news_detail_item_ad);
    }

    @Override
    protected void convert(BaseViewHolder helper, NewsSummary item) {
        switch(helper.getItemViewType()){
            case NewsSummary.TYPE_ITEM:
                ImageView imageView = helper.getView(R.id.news_summary_photo_iv);
                Glide.with(fragment)
                        .load(item.getImgsrc())
//                        .thumbnail(Glide.with(context).load(item.getImgsrc()))
                        .into(imageView);
                ((TextView)helper.getView(R.id.news_summary_digest_tv)).setText(item.getDigest());
                ((TextView)helper.getView(R.id.news_summary_title_tv)).setText(item.getLtitle());
                ((TextView)helper.getView(R.id.news_summary_ptime_tv)).setText(item.getPtime()+"");
                break;
            case NewsSummary.TYPE_PHOTO_ITEM:
                int PhotoThreeHeight = (int) DisplayUtil.dip2px(90);
                int PhotoTwoHeight = (int) DisplayUtil.dip2px(120);
                int PhotoOneHeight = (int) DisplayUtil.dip2px(150);
                List<NewsSummary.AdsBean> ads = item.getAds();
                String imgsrc = item.getImgsrc();
                List<NewsSummary.ImgextraBean> imgextra = item.getImgextra();
                LinearLayout linearLayout=helper.getView(R.id.news_summary_photo_iv_group);
                ViewGroup.LayoutParams layoutParams = linearLayout.getLayoutParams();
                helper.getView(R.id.news_summary_photo_iv_right).setVisibility(View.VISIBLE);
                helper.getView(R.id.news_summary_photo_iv_middle).setVisibility(View.VISIBLE);
                helper.getView(R.id.news_summary_photo_iv_left).setVisibility(View.VISIBLE);
                if (ads!=null&&ads.size()>0) {
                    if (ads.size() >= 3) {
                        layoutParams.height = PhotoThreeHeight;
                        linearLayout.setLayoutParams(layoutParams);
                        Glide.with(fragment).load(ads.get(0).getImgsrc()).into((ImageView) helper.getView(R.id.news_summary_photo_iv_left));
                        Glide.with(fragment).load(ads.get(1).getImgsrc()).into((ImageView) helper.getView(R.id.news_summary_photo_iv_middle));
                        Glide.with(fragment).load(ads.get(2).getImgsrc()).into((ImageView) helper.getView(R.id.news_summary_photo_iv_right));
                    } else if (ads.size() >= 2) {
                        layoutParams.height = PhotoTwoHeight;
                        linearLayout.setLayoutParams(layoutParams);
                        helper.getView(R.id.news_summary_photo_iv_right).setVisibility(View.GONE);
                        Glide.with(fragment).load(ads.get(0).getImgsrc()).into((ImageView) helper.getView(R.id.news_summary_photo_iv_left));
                        Glide.with(fragment).load(ads.get(1).getImgsrc()).into((ImageView) helper.getView(R.id.news_summary_photo_iv_middle));
                    } else if (ads.size() >= 1) {
                        layoutParams.height = PhotoOneHeight;
                        linearLayout.setLayoutParams(layoutParams);
                        helper.getView(R.id.news_summary_photo_iv_right).setVisibility(View.GONE);
                        helper.getView(R.id.news_summary_photo_iv_middle).setVisibility(View.GONE);
                        Glide.with(fragment).load(ads.get(0).getImgsrc()).into((ImageView) helper.getView(R.id.news_summary_photo_iv_left));
                    }
                }else if (imgextra!=null&&imgextra.size()>0){
                    if (imgextra.size() >= 3) {
                        layoutParams.height = PhotoThreeHeight;
                        linearLayout.setLayoutParams(layoutParams);
                        Glide.with(fragment).load(imgextra.get(0).getImgsrc()).into((ImageView) helper.getView(R.id.news_summary_photo_iv_left));
                        Glide.with(fragment).load(imgextra.get(1).getImgsrc()).into((ImageView) helper.getView(R.id.news_summary_photo_iv_middle));
                        Glide.with(fragment).load(imgextra.get(2).getImgsrc()).into((ImageView) helper.getView(R.id.news_summary_photo_iv_right));
                    } else if (imgextra.size() >= 2) {
                        layoutParams.height = PhotoTwoHeight;
                        linearLayout.setLayoutParams(layoutParams);
                        helper.getView(R.id.news_summary_photo_iv_right).setVisibility(View.GONE);
                        Glide.with(fragment).load(imgextra.get(0).getImgsrc()).into((ImageView) helper.getView(R.id.news_summary_photo_iv_left));
                        Glide.with(fragment).load(imgextra.get(1).getImgsrc()).into((ImageView) helper.getView(R.id.news_summary_photo_iv_middle));
                    } else if (imgextra.size() >= 1) {
                        layoutParams.height = PhotoOneHeight;
                        linearLayout.setLayoutParams(layoutParams);
                        helper.getView(R.id.news_summary_photo_iv_right).setVisibility(View.GONE);
                        helper.getView(R.id.news_summary_photo_iv_middle).setVisibility(View.GONE);
                        Glide.with(fragment).load(imgextra.get(0).getImgsrc()).into((ImageView) helper.getView(R.id.news_summary_photo_iv_left));
                    }
                }else if (imgsrc!=null){
                    layoutParams.height = PhotoOneHeight;
                    linearLayout.setLayoutParams(layoutParams);
                    helper.getView(R.id.news_summary_photo_iv_right).setVisibility(View.GONE);
                    helper.getView(R.id.news_summary_photo_iv_middle).setVisibility(View.GONE);
                    Glide.with(fragment).load(imgsrc).into((ImageView) helper.getView(R.id.news_summary_photo_iv_left));
                }
                ((TextView) helper.getView(R.id.news_summary_title_tv)).setText(item.getTitle());
                ((TextView)helper.getView(R.id.news_summary_ptime_tv)).setText(item.getPtime()+"");
                break;
            default:
                break;
        }
    }
}
