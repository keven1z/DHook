package com.keven1z.service.impl;

import com.keven1z.dao.IClassInfoDao;
import com.keven1z.entity.ClassInfoEntity;
import com.keven1z.service.IClassInfoService;
import org.springframework.stereotype.Service;
import org.sqlite.SQLiteException;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author keven1z
 * @date 2022/09/06
 */
@Service
public class ClassInfoServiceImpl implements IClassInfoService {
    @Resource
    private IClassInfoDao classInfoDao;

    @Override
    public int insert(ClassInfoEntity classInfoEntity) {
        return classInfoDao.insert(classInfoEntity);
    }
    @Override
    public int addClassName(String className, String packageName){
       try{
           return classInfoDao.insertClass(className,packageName);
       }
       catch (Exception e){
            return 0;
       }
    }

    @Override
    public List<ClassInfoEntity> getClassInfoByFlag(int flag) {
        return classInfoDao.selectClassByFlag(flag);
    }

    @Override
    public ClassInfoEntity findClassInfo(String className, String packageName) {
        return classInfoDao.findClassInfo(className, packageName);
    }
}
