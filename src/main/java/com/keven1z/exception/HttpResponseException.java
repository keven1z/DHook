package com.keven1z.exception;

import com.keven1z.http.ErrorEnum;

/**
 * @author keven1z
 * @date 2022/06/19
 */
public class HttpResponseException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private Integer code;

    /**
     * @param errorEnum 以错误的ErrorEnum做参数
     */

    public HttpResponseException(ErrorEnum errorEnum) {

        super(errorEnum.getErrorMsg());

        this.code = errorEnum.getErrorCode();

    }


    public Integer getCode() {

        return code;

    }

    public void setCode(Integer code) {

        this.code = code;

    }


}
