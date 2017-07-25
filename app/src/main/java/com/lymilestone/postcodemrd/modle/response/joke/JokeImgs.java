package com.lymilestone.postcodemrd.modle.response.joke;

import java.util.List;

/**
 * Created by CodeManLY on 2017/7/20 0020.
 */

public class JokeImgs {
    private List<DataBean> data;

    @Override
    public String toString() {
        return "JokeImgs{" +
                "data=" + data +
                '}';
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
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
}
