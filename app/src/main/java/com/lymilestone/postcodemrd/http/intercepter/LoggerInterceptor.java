package com.lymilestone.postcodemrd.http.intercepter;

import android.text.TextUtils;
import com.lymilestone.httplibrary.utils.log.LUtils;

import java.io.IOException;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * 日志打印拦截器
 * Created by zhy on 16/3/1.
 */
public class LoggerInterceptor implements Interceptor {

    public static final String TAG = "llll_";
    private String tag;
    private boolean showResponse;

    public LoggerInterceptor(String tag, boolean showResponse) {
        if (TextUtils.isEmpty(tag)) {
            tag = TAG;
        }
        this.showResponse = showResponse;
        this.tag = tag;
    }

    public LoggerInterceptor(String tag) {
        this(tag, false);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        logForRequest(request);
        Response response = chain.proceed(request);
        return logForResponse(response);
    }

    private Response logForResponse(Response response) {
        try {
            LUtils.e(response.toString());
            LUtils.e("========response'log=======");
            Response.Builder builder = response.newBuilder();
            Response clone = builder.build();
            LUtils.e("code : " + clone.code());
            if (!TextUtils.isEmpty(clone.message()))
                LUtils.e("message : " + clone.message());
            if (showResponse) {
                ResponseBody body = clone.body();
                if (body != null) {
                    MediaType mediaType = body.contentType();
                    if (mediaType != null) {
                        LUtils.e("responseBody's contentType : " + mediaType.toString());
                        if (isText(mediaType)) {
                            String resp = body.string();
                            LUtils.e("responseBody's content : " + resp);
                            body = ResponseBody.create(mediaType, resp);
                            return response.newBuilder().body(body).build();
                        } else {
                            LUtils.e("responseBody's content : maybe [file part] , too large too print , ignored!");
                        }
                    }
                }
            }
            LUtils.e("========response'log=======end");
        } catch (Exception e) {
            LUtils.e(e.getMessage());
        }finally {
            LUtils.e("========response'log=======end");
        }

        return response;
    }

    private void logForRequest(Request request) {
        try {
            String url = request.url().toString();
            Headers headers = request.headers();

            LUtils.e("________request'log________");
            LUtils.e("method : " + request.method());
            LUtils.e("url : " + url);
            if (headers != null && headers.size() > 0) {
                LUtils.e("headers : " + headers.toString());
            }
            RequestBody requestBody = request.body();
            if (requestBody != null) {
                MediaType mediaType = requestBody.contentType();
                if (mediaType != null) {
                    LUtils.e("requestBody's contentType : " + mediaType.toString());
                    if (isText(mediaType)) {
                        LUtils.e("requestBody's content : " + bodyToString(request));
                    } else {
                        LUtils.e("requestBody's content : maybe [file part] , too large too print , ignored!");
                    }
                }
            }
            LUtils.e("________request'log________end");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isText(MediaType mediaType) {
        if (mediaType.type() != null && mediaType.type().equals("text")) {
            return true;
        }
        if (mediaType.subtype() != null) {
            if (mediaType.subtype().equals("json") ||
                    mediaType.subtype().equals("xml") ||
                    mediaType.subtype().equals("html") ||
                    mediaType.subtype().equals("webviewhtml")||
                    mediaType.subtype().equals("x-www-form-urlencoded")
                    )
                return true;
        }
        return false;
    }

    private String bodyToString(final Request request) {
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "something error when show requestBody.";
        }
    }
}
