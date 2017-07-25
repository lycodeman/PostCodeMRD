package com.lymilestone.postcodemrd.modle.response.joke;

/**
 * Created by CodeManLY on 2017/7/20 0020.
 */

public class BaseJoke<T> {
    private int error_code;
    private String reason;
    private T result;

    @Override
    public String toString() {
        return "BaseJoke{" +
                "error_code=" + error_code +
                ", reason='" + reason + '\'' +
                ", result=" + result +
                '}';
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
