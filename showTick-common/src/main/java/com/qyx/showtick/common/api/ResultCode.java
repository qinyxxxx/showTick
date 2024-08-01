package com.qyx.showtick.common.api;

import lombok.Getter;

/**
 * API返回码封装类
 * Created by Yuxin Qin on 7/10/24
 */
@Getter
public enum ResultCode implements IErrorCode {
    SUCCESS(200, "Success"),
    FAILED(500, "Internal Error"),
    VALIDATE_FAILED(404, "Data Validate Failed"),
    UNAUTHORIZED(401, "Unauthorized"),
    FORBIDDEN(403, "Forbidden");
    private long code;
    private String message;

    private ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

}
