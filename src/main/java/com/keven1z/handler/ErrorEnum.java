package com.keven1z.handler;

/**
 * @author keven1z
 * @date 2022/06/19
 */
public enum ErrorEnum {

    /*
     * 错误信息
     * */

    E_20011(20011, "缺少必填参数"),;


    private Integer errorCode;

    private String errorMsg;

    ErrorEnum(Integer errorCode, String errorMsg) {

        this.errorCode = errorCode;

        this.errorMsg = errorMsg;

    }

    public Integer getErrorCode() {

        return errorCode;

    }

    public String getErrorMsg() {

        return errorMsg;

    }
}
