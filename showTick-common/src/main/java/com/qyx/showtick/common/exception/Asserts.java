package com.qyx.showtick.common.exception;


import com.qyx.showtick.common.api.IErrorCode;

/**
 * 断言处理类，用于抛出各种API异常
 * Created by Yuxin Qin
 */
public class Asserts {
    public static void fail(String message) {
        throw new ApiException(message);
    }

    public static void fail(IErrorCode errorCode) {
        throw new ApiException(errorCode);
    }
}
