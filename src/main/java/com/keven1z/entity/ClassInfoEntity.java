package com.keven1z.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author keven1z
 * @date 2022/09/06
 */
@Getter
@Setter
@ToString
public class ClassInfoEntity {
    private String className;
    private String methods;
    private String fields;
    private String superClass;
    private String interfaces;
    private String packageName;
}
