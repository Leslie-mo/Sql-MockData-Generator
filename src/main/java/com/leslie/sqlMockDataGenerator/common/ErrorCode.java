package com.leslie.sqlMockDataGenerator.common;

/**
 * error code
 */
public enum ErrorCode {

    SUCCESS(0, "ok"),
    PARAMS_ERROR(40000, "Request parameter error"),
    NO_AUTH_ERROR(40101, "No permission"),
    NOT_FOUND_ERROR(40400, "Request data does not exist"),
    FORBIDDEN_ERROR(40300, "No Access"),
    SYSTEM_ERROR(50000, "System internal exception"),
    OPERATION_ERROR(50001, "Operation failed");

    /**
     * status code
     */
    private final int code;

    /**
     * information
     */
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
