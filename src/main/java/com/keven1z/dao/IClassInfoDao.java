package com.keven1z.dao;

import com.keven1z.entity.ClassInfoEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author keven1z
 * @date 2022/01/05
 */
@Mapper
public interface IClassInfoDao {
    @Insert("INSERT or replace INTO class_info(class_name,package_name,methods,fields,super_class,interfaces,flag) VALUES(#{className},#{packageName},#{methods},#{fields},#{superClass},#{interfaces},1)")
    int insert(ClassInfoEntity classInfoEntity);

    @Insert("INSERT  INTO class_info(class_name,package_name) VALUES(#{className},#{packageName})")
    int insertClass(String className,String packageName);

    @Result(column = "class_name",property = "className")
    @Result(column = "package_name",property = "packageName")
    @Result(column = "super_class",property = "superClass")
    @Select("select * from class_info where class_name=#{className} and package_name=#{packageName}")
    ClassInfoEntity findClassInfo(String className,String packageName);
    @Result(column = "class_name",property = "className")
    @Result(column = "package_name",property = "packageName")
    @Result(column = "super_class",property = "superClass")
    @Select("select * from class_info where flag=#{flag}")
    List<ClassInfoEntity> selectClassByFlag(int flag);
}
