package com.lymilestone.postcodemrd.modle.response.joke;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by CodeManLY on 2017/7/20 0020.
 */

public class JokePic implements Parcelable {
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
                ", url='" + url + '\'' +
                ", width=" + width +
                ", height=" + height +
                '}';
    }

    /**
     * content : 圣诞节还是不过了
     * hashId : 70FE1194DD6AD18F2A1676DA3D368F02
     * unixtime : 1419380597
     * url : http://juheimg.oss-cn-hangzhou.aliyuncs.com/joke/201412/24/70FE1194DD6AD18F2A1676DA3D368F02.png
     */

    private String content;
    private String hashId;
    private String unixtime;
    private String url;

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

    public String getUnixtime() {
        return unixtime;
    }

    public void setUnixtime(String unixtime) {
        this.unixtime = unixtime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.width);
        dest.writeInt(this.height);
        dest.writeString(this.content);
        dest.writeString(this.hashId);
        dest.writeString(this.unixtime);
        dest.writeString(this.url);
    }

    public JokePic() {
    }

    protected JokePic(Parcel in) {
        this.width = in.readInt();
        this.height = in.readInt();
        this.content = in.readString();
        this.hashId = in.readString();
        this.unixtime = in.readString();
        this.url = in.readString();
    }

    public static final Parcelable.Creator<JokePic> CREATOR = new Parcelable.Creator<JokePic>() {
        @Override
        public JokePic createFromParcel(Parcel source) {
            return new JokePic(source);
        }

        @Override
        public JokePic[] newArray(int size) {
            return new JokePic[size];
        }
    };
}
