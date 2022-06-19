package com.keven1z.handler;

import com.keven1z.exception.HttpResponseException;
import com.keven1z.http.ExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author keven1z
 * @date 2022/06/19
 */
@ControllerAdvice
@Slf4j
public class ExceptionHandler {
  @ResponseBody
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
  public ExceptionResponse handleException(Exception ex) {

    if (ex instanceof HttpResponseException) {

      log.warn(ex.getMessage(), ex);

      HttpResponseException httpResponseException = (HttpResponseException) ex;

      return ExceptionResponse.create(httpResponseException.getCode(), httpResponseException.getMessage());

    } else {

      log.error(ex.getMessage(), ex);
      return ExceptionResponse.create(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());

    }

}

}
