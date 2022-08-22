package com.keven1z.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author keven1z
 * @date 2022/01/05
 */
@Getter
@Setter
@ToString
public class ClassMapEntity {
    private int id;
    /**
     * 类名
     */
    private String className;
    /**
     * 包名
     */
    private String packageName;
    private String agentId;

}
