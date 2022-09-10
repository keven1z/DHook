package com.keven1z.service;

import com.keven1z.entity.ClassInfoEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IClassInfoService {
    int insert(ClassInfoEntity classInfoEntity);
    ClassInfoEntity findClassInfo(String ClassName,String packageName);
    int addClassName(String className, String packageName);
    public List<ClassInfoEntity> getClassInfoByFlag(int flag);

}
