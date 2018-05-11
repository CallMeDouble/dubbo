package com.demo.core.server;

/**
 * Created by dragon
 *
 * 响应实体类
 */
public class Response {

    private long requestId;
    private Object response;
    private Throwable throwable;

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    public Object getResponse() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }
}
