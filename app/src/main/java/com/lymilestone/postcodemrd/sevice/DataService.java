package com.lymilestone.postcodemrd.sevice;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Parcelable;

import com.lymilestone.httplibrary.rx.bus.BusFactory;
import com.lymilestone.httplibrary.utils.log.LUtils;
import com.lymilestone.postcodemrd.modle.response.joke.JokePic;
import com.lymilestone.postcodemrd.utils.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Othershe
 * Time: 2016/8/18 11:48
 */
public class DataService extends IntentService {
    public DataService() {
        super("");

    }

    public static void startService(Context context, List<JokePic> datas) {
        Intent intent = new Intent(context, DataService.class);
        intent.putParcelableArrayListExtra("data", (ArrayList<? extends Parcelable>) datas);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent == null) {
            return;
        }
        List<JokePic> datas = intent.getParcelableArrayListExtra("data");
        handleJokePic(datas);
    }

    private void handleJokePic(List<JokePic> datas) {

        if (datas.size() == 0) {
            BusFactory.getBus().post("finish");
            LUtils.e("========发出消息=========");
            return;
        }
        for (JokePic data : datas) {
            Bitmap bitmap = ImageLoader.load(this, data.getUrl());
            if (bitmap != null) {
                data.setWidth(bitmap.getWidth());
                data.setHeight(bitmap.getHeight());
            }
        }
        BusFactory.getBus().post(datas);
        LUtils.e("========发出消息=========");
    }

}
