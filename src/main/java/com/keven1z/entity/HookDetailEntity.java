package com.keven1z.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author keven1z
 * @date 2022/10/19
 */
@Getter
@Setter
@ToString
public class HookDetailEntity {
    private int id;
    private int hookId;
    private String param;
    private String returnObject;
    private String thisObject;
    private String stacks;
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy/MM/dd HH:mm:ss")
    private Date date;
}
