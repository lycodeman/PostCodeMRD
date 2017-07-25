package com.lymilestone.postcodemrd.http.api;

import com.lymilestone.postcodemrd.modle.response.joke.BaseJoke;
import com.lymilestone.postcodemrd.modle.response.joke.JokeContent;
import com.lymilestone.postcodemrd.modle.response.joke.JokeImgs;
import com.lymilestone.postcodemrd.modle.response.joke.JokePic;
import com.lymilestone.postcodemrd.modle.response.joke.Jokes;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by codeest on 2016/8/2.
 * 知乎APIs
 */
public interface JokeApis {

    String HOST = "http://japi.juhe.cn/joke/";
    String HOST_V = "http://v.juhe.cn/joke/";
    String CONTENT_LIST = "content/list.from";
    String CONTENT_TEXT = "content/text.from";
    String IMG_LIST = "img/list.from";
    String IMG_TEXT = "img/text.from";
    String RAND_JOKE = "randJoke.php";
    String SORT_DESC = "desc";
    String SORT_ASC = "asc";
    //错误码参照
    /*  10001	错误的请求KEY	101
        10002	该KEY无请求权限	102
        10003	KEY过期	103
        10004	错误的OPENID	104
        10005	应用未审核超时，请提交认证	105
        10007	未知的请求源	107
        10008	被禁止的IP	108
        10009	被禁止的KEY	109
        10011	当前IP请求超过限制	111
        10012	请求超过次数限制	112
        10013	测试KEY超过请求限制	113
        10014	系统内部异常(调用充值类业务时，请务必联系客服或通过订单查询接口检测订单，避免造成损失)	114
        10020	接口维护	120
        10021	接口停用	121*/

    /*请求示例：http://japi.juhe.cn/joke/?
    key=您申请的KEY&page=2&pagesize=10&sort=asc&time=1418745237*/
    //按时间查询
    @GET(CONTENT_LIST)
    Observable<BaseJoke<JokeContent>> fetchJokeForTime(@Query("key") String key, @Query("page") int page
            , @Query("pagesize") int pagesize, @Query("time") String time, @Query("sort") String sort);

    //最新查询
    @GET(CONTENT_TEXT)
    Observable<BaseJoke<JokeContent>> fetchJokeForNews(@Query("key") String key, @Query("page") int page
            , @Query("pagesize") int pagesize);

    //获取趣图(按时间)
    @GET(IMG_LIST)
    Observable<BaseJoke<JokeImgs>> fetchJokeImgsTime(@Query("key") String key, @Query("page") int page
            , @Query("pagesize") int pagesize, @Query("time") String time, @Query("sort") String sort);

    //最新查询
    @GET(IMG_TEXT)
    Observable<BaseJoke<JokeImgs>> fetchJokeImgNews(@Query("key") String key, @Query("page") int page
            , @Query("pagesize") int pagesize);

    //最新查询pic
    @GET(HOST_V+RAND_JOKE)
    Observable<BaseJoke<List<JokePic>>> fetchRandJokePic(@Query("key") String key, @Query("type") String type);

    //最新查询笑话
    @GET(HOST_V+RAND_JOKE)
    Observable<BaseJoke<List<Jokes>>> fetchRandJokes(@Query("key") String key);
}
