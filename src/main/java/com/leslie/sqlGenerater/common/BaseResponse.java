package com.leslie.sqlGenerater.common;

import lombok.Data;

import java.io.Serializable;

/**
 * return base
 *
 * @param <T>
 */
@Data
public class BaseResponse<T> implements Serializable {

    private int code;

    private T data;

    private String message;

    public BaseResponse(int code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public BaseResponse(int code, T data) {
        this(code, data, "");
    }

    public BaseResponse(ErrorCodeResponse errorCodeResponse) {
        this(errorCodeResponse.getCode(), null, errorCodeResponse.getMessage());
    }
}
