package com.lymilestone.postcodemrd.modle.response.joke;

/**
 * Created by CodeManLY on 2017/7/20 0020.
 */

public class Jokes {
    @Override
    public String toString() {
        return "Jokes{" +
                "content='" + content + '\'' +
                ", hashId='" + hashId + '\'' +
                ", unixtime='" + unixtime + '\'' +
                '}';
    }

    /**
     * content : …给闺女买了一双小皮鞋，花了两张毛爷爷，小鞋很结实，闺女穿了好多天后要我给她买鞋，说不喜欢了，我就各种教育，比如要穿破之后买，要知道省钱……等等。今天再回去的时候，姑娘左手拎着残破的鞋子，右手拿着剪刀，跑过来说:爸爸，现在可以买新的鞋子了吧！来，过来，爸爸保证不打你，我的亲闺女。
     * hashId : bf56a16fd4ea0ffc55bf11c3f088855a
     * unixtime : 1431447231
     */

    private String content;
    private String hashId;
    private String unixtime;

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
}
