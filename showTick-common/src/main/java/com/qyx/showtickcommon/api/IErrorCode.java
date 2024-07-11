package com.qyx.showtickcommon.api;

/**
 * API返回码接口
 * Created by Yuxin Qin on 7/10/24
 */
public interface IErrorCode {
    /**
     * 返回码
     */
    long getCode();

    /**
     * 返回信息
     */
    String getMessage();
}
