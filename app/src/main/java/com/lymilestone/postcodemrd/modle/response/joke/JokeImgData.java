package com.lymilestone.postcodemrd.modle.response.joke;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Othershe
 * Time: 2016/8/17 10:12
 */
public class JokeImgData implements Parcelable {
    private List<JokeImgData.DataBean> data;

    @Override
    public String toString() {
        return "JokeImgs{" +
                "data=" + data +
                '}';
    }

    public List<JokeImgData.DataBean> getData() {
        return data;
    }

    public void setData(List<JokeImgData.DataBean> data) {
        this.data = data;
    }

    public static class DataBean {

        /**
         * content : 二汪
         * hashId : 13AF55EB201FADD4DB8AD3C0FC053E72
         * unixtime : 1418954054
         * updatetime : 2014-12-19 09:54:14
         * url : http://img.juhe.cn/joke/201412/19/13AF55EB201FADD4DB8AD3C0FC053E72.gif
         */

        private String content;
        private String hashId;
        private int unixtime;
        private String updatetime;
        private String url;
        private int width;
        private int height;

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "content='" + content + '\'' +
                    ", hashId='" + hashId + '\'' +
                    ", unixtime=" + unixtime +
                    ", updatetime='" + updatetime + '\'' +
                    ", url='" + url + '\'' +
                    ", width=" + width +
                    ", height=" + height +
                    '}';
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getHashId() {
            return hashId;
        }

        public void setHashId(String hashId) {
            this.hashId = hashId;
        }

        public int getUnixtime() {
            return unixtime;
        }

        public void setUnixtime(int unixtime) {
            this.unixtime = unixtime;
        }

        public String getUpdatetime() {
            return updatetime;
        }

        public void setUpdatetime(String updatetime) {
            this.updatetime = updatetime;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.data);
    }

    public JokeImgData() {
    }

    protected JokeImgData(Parcel in) {
        this.data = new ArrayList<DataBean>();
        in.readList(this.data, DataBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<JokeImgData> CREATOR = new Parcelable.Creator<JokeImgData>() {
        @Override
        public JokeImgData createFromParcel(Parcel source) {
            return new JokeImgData(source);
        }

        @Override
        public JokeImgData[] newArray(int size) {
            return new JokeImgData[size];
        }
    };
}
