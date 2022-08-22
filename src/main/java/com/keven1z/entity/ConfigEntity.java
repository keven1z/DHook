package com.keven1z.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author keven1z
 * @date 2022/07/28
 */
@Getter
@Setter
@ToString
public class ConfigEntity {

    /**
     * 配置名称
     */
    private String name;
    /**
     * 配置值
     */
    private String value;

}
