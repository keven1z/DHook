package com.keven1z.utils;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

/**
 * @author keven1z
 * @date 2022/06/16
 */
public class HttpUtil {
    /**
     * http请求返回文件下载
     * @param fileName 下载文件名
     * @param resource 资源
     * @param length 文件长度
     */
    public static ResponseEntity<Object> responseSource(String fileName,InputStreamResource resource , long length){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", String.format("attachment;filename=\"%s\"", fileName));
        headers.add("Cache-Control", "no-cache,no-store,must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        ResponseEntity<Object> responseEntity = ResponseEntity.ok()
                .headers(headers)
                .contentLength(length)
                .contentType(MediaType.parseMediaType("application/java-archive"))
                .body(resource);
        return responseEntity;
    }
}
