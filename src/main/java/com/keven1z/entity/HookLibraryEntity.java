package com.keven1z.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author keven1z
 * @date 2022/08/08
 */
@Getter
@Setter
@ToString
public class HookLibraryEntity {
    private int id;
    /**
     * 别名
     */
    private String alias;
    /**
     * 类名
     */
    private String className;
    /**
     * 方法名
     */
    private String method;
    /**
     * 方法描述
     */
    private String desc;
    /**
     * 该hook点注释描述
     */
    private String notes;
}
