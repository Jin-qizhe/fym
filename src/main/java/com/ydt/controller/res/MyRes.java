package com.ydt.controller.res;

import lombok.Data;

@Data
public class MyRes<T> {
    private Integer code;
    private String msg;
    private T data;

    public void setSuccess(String msg) {
        this.code = 0;
        this.msg = msg;
    }

    public void setFailed(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
