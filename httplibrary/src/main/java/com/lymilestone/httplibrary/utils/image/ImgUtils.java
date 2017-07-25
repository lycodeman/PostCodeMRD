//package com.lymilestone.httplibrary.utils.image;
//
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//
///**
// * Created by CodeManLY on 2017/7/22 0022.
// */
//
//public class ImgUtils {
//
//    private float getScaleType(int position) {
//        if (!indexMap.containsKey(position)) {
//            float scaleType;
//            if (hasHeader()) {
//                if (position == 1) {
//                    scaleType = SIZE_SCALE_01;
//                } else if (position == 2) {
//                    scaleType = SIZE_SCALE_02;
//                } else {
//                    scaleType = Utils.getRandomInt() % 2 == 0 ? SIZE_SCALE_01 : SIZE_SCALE_02;
//                }
//            } else {
//                if (position == 0) {
//                    scaleType = SIZE_SCALE_01;
//                } else if (position == 1) {
//                    scaleType = SIZE_SCALE_02;
//                } else {
//                    scaleType = Utils.getRandomInt() % 2 == 0 ? SIZE_SCALE_01 : SIZE_SCALE_02;
//                }
//            }
//            indexMap.put(position, scaleType);
//        }
//
//        return indexMap.get(position);
//    }
//
//    private void resizeItemView(ImageView frontCoverImage, float scaleType) {
//        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) frontCoverImage.getLayoutParams();
//        params.width = screenWidth / 2;
//        params.height = (int) (params.width / scaleType) - Utils.dp2px(context, 8);
//        frontCoverImage.setLayoutParams(params);
//    }
//}
