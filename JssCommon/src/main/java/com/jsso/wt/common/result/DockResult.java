package com.jsso.wt.common.result;

/**
 * 自定义DTO返回数据结构
 */
public class DockResult<T> {

    private static final Integer SUCCESS = 1;
    private static final Integer WARN = 2;
    private static final Integer ERROR = 3;

    private int status;// 1:成功，2:警告，3:失败
    private String message;
    private T data;

    private DockResult() {
    }

    public static <T> DockResult success(String message, T data) {
        return new DockResult<>(SUCCESS, message, data);
    }

    public static <T> DockResult<T> success(T data) {
        return new DockResult<>(SUCCESS, "", data);
    }

    public static DockResult<String> success(String message) {
        return new DockResult<>(SUCCESS, message);
    }

    public static <T> DockResult<T> fail(String errorMsg) {
        return new DockResult<T>(ERROR, errorMsg);
    }

    public static <T> DockResult fail(String errorMsg, T data) {
        return new DockResult<>(ERROR, errorMsg, data);
    }

    public static <T> DockResult warn(String errorMsg, T data) {
        return new DockResult<>(WARN, errorMsg, data);
    }

    private DockResult(int status, String message) {
        this.status = status;
        this.message = message;
    }

    private DockResult(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    @Override
    public String toString() {
        return "DockResult{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public boolean success() {
        return status == SUCCESS;
    }

    public boolean warn() {
        return status == WARN;
    }

    public boolean error() {
        return status == ERROR;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}


