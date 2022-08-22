package com.keven1z.http;

/**
 * @author keven1z
 * @date 2022/06/19
 */
public enum ErrorEnum {

    /*
     * 错误信息
     * */

    E_10000(10000, "文件类型不正确"),
    E_10001(10001, "jar包解析出错"),
    E_20001(20001, "文件写入出错"),

    E_30001(30001, "未查询到对应数据"),
    E_40001(40001, "未查询到对应hook"),


    ;


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
