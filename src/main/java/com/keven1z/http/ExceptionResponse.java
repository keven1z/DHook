package com.keven1z.http;

/**
 * @author keven1z
 * @date 2022/06/19
 */
public class ExceptionResponse {

    private String message;

    private Integer code;

    public ExceptionResponse(Integer code, String message) {

        this.message = message;

        this.code = code;

    }

    public static ExceptionResponse create(Integer code, String message) {

        return new ExceptionResponse(code, message);

    }

    public Integer getCode() {

        return code;

    }

    public String getMessage() {

        return message;

    }

}
