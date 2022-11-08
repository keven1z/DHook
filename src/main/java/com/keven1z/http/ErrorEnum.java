package com.keven1z.http;

/**
 * @author keven1z
 * @date 2022/06/19
 */
public enum ErrorEnum {

    /*
     * 文件解析信息
     * */
    E_10000(10000, "文件类型不正确"),
    E_10001(10001, "jar包解析出错"),

    /*文件下载error*/
    E_20001(20001, "文件写入出错"),
    E_20002(20002, "源码下载出错"),
    /*数据查找错误*/
    E_30001(30001, "未查询到对应数据"),
    E_30002(30002, "未查询到对应hook"),
    E_30003(30003, "未查询到对应类的详细信息"),

    E_40001(40001, "未获取到对应参数"),

    E_50001(50001, "获取数据超时"),








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
