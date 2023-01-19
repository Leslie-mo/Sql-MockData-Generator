package com.leslie.sqlGenerator.common;

/**
 * return tool
 *
 */
public class ResultUtils {

    /**
     * success
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(0, data, "ok");
    }

    /**
     * fail
     *
     * @param errorCodeResponse
     * @return
     */
    public static BaseResponse error(ErrorCodeResponse errorCodeResponse) {
        return new BaseResponse<>(errorCodeResponse);
    }

    /**
     * fail
     *
     * @param code
     * @param message
     * @return
     */
    public static BaseResponse error(int code, String message) {
        return new BaseResponse(code, null, message);
    }

    /**
     * fail
     *
     * @param errorCodeResponse
     * @return
     */
    public static BaseResponse error(ErrorCodeResponse errorCodeResponse, String message) {
        return new BaseResponse(errorCodeResponse.getCode(), null, message);
    }
}
